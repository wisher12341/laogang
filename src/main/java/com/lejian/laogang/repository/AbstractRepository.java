package com.lejian.laogang.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lejian.laogang.pojo.bo.BaseBo;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.lejian.laogang.common.ComponentRespCode.REPOSITORY_ERROR;


public abstract class AbstractRepository<Bo extends BaseBo,Entity> {



    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * 批量插入
     *
     * @param boList 实体类集合
     */
    public void batchInsert(List<Bo> boList) {
        List<Entity> entityList = boList.stream().map(item->(Entity)item.convert()).collect(Collectors.toList());
        for (int i = 0; i < entityList.size(); i++) {
            entityManager.persist(entityList.get(i));
            if (i % 50 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.flush();
        entityManager.clear();
    }


    public List<Bo> getAll() {
        List<Entity> entityList = getDao().findAll();
        return entityList.stream().map(this::convert).collect(Collectors.toList());
    }

    protected abstract Bo convert(Entity entity);

    protected abstract JpaRepository getDao();


    public Bo getByPkId(Integer pkId) {
        Optional<Entity> optional =getDao().findById(pkId);
        REPOSITORY_ERROR.check(optional.isPresent(),
                "getByPkId no data found,id:"+pkId+","+this.getClass().getSimpleName());
        return convert(optional.get());
    }


    public void save(Bo bo) {
        Entity entity = bo.convert();
        getDao().save(entity);
    }

    public Bo saveAndReturn(Bo bo) {
        Entity entity = (Entity) bo.convert();
        return convert((Entity) getDao().save(entity));
    }

    @Transactional
    public void dynamicUpdateByPkId(Bo bo){
        dynamicUpdate(bo,"id");
    }

    /**
     * 动态根据某个值更新 过滤非null字段
     * jpa 拼接 update 语句
     * 要加事务  不然会报错
     * @param bo
     */
    @Transactional
    public void dynamicUpdate(Bo bo,String fieldName){
        try {
            String sqlFormat = "update %s set %s where %s='%s'";

            StringBuilder updateStr=new StringBuilder();
            Entity entity = (Entity) bo.convert();
            Object searchValue =null;
            Field[] fields = entity.getClass().getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                Object fieldValue = field.get(entity);
                if(fieldValue!=null){
                    if(field.getName().equals(fieldName)){
                        searchValue = fieldValue;
                    }else {
                        String name = StringUtils.isNotBlank(field.getAnnotation(Column.class).name())? field.getAnnotation(Column.class).name():field.getName();
                        updateStr.append(name + "='" + fieldValue + "'");
                        updateStr.append(",");
                    }
                }
            }
                updateStr.deleteCharAt(updateStr.length()-1);


            REPOSITORY_ERROR.checkNotNull(searchValue,
                    "dynamicUpdate, no searchValue id found,"+this.getClass().getSimpleName());



            String sql = String.format(sqlFormat,
                    entity.getClass().getAnnotation(Table.class).name(),
                    updateStr.toString(),
                    fieldName,searchValue);

            Query query =entityManager.createNativeQuery(sql);
            query.executeUpdate();

        }catch (Exception e){
            REPOSITORY_ERROR.doThrowException("dynamicUpdate,"+this.getClass().getSimpleName(),e);
        }
    }

    public List<Bo> getByPkIds(List<Integer> pkIds) {
        List<Entity> entityList =getDao().findAllById(pkIds);
        return entityList.stream().map(this::convert).collect(Collectors.toList());
    }

    public Long count() {
        return getDao().count();
    }

    /**
     * 批量插入
     * @param boList
     */
    public void batchAdd(List<Bo> boList) {
        List<Entity> entityList = boList.stream().map(item->(Entity)item.convert()).collect(Collectors.toList());
        //saveAll  也可以用于更新， 但是更新是 null值的属性  相应字段会更新成null
        getDao().saveAll(entityList);
    }

    /**
     * 批量更新
     */
    public void batchUpdate(List<Bo> boList){
        dynamicBatchUpdateByPkId(boList);
    }

    private void dynamicBatchUpdateByPkId(List<Bo> boList) {
        dynamicBatchUpdate(boList,"id");
    }

    @Transactional
    public void dynamicBatchUpdate(List<Bo> boList, String fieldName) {
        List<List<Bo>> list =Lists.partition(boList,50);
        list.forEach(item->{
            try {
                StringBuilder sql = new StringBuilder("update ");
                List<Entity> entityList = boList.stream().map(x->(Entity)x.convert()).collect(Collectors.toList());
                Class<Entity> entityClass = (Class<Entity>) entityList.get(0).getClass();
                sql.append(entityClass.getAnnotation(Table.class).name());
                sql.append(" set ");


                Field[] fields = entityClass.getDeclaredFields();

                String sqlCase = "%s= case "+fieldName;
                String updateCase = " when %s then '%s'";
                Field idField =  entityClass.getDeclaredField(fieldName);

                Set<String> idSet = Sets.newHashSet();
                idField.setAccessible(true);
                for(Field field:fields){
                    field.setAccessible(true);
                    String s1;
                    if (!field.getName().equals(fieldName)){
                        String name = StringUtils.isNotBlank(field.getAnnotation(Column.class).name()) ? field.getAnnotation(Column.class).name() : field.getName();
                        s1 =String.format(sqlCase,name);
                    }else{
                        continue;
                    }
                    StringBuilder s2 = new StringBuilder();
                    for (Entity entity : entityList) {
                        Object fieldValue = field.get(entity);
                        if (fieldValue != null) {
                            Object id = idField.get(entity);
                            idSet.add(String.valueOf(id));
                            s2.append(String.format(updateCase,String.valueOf(id),String.valueOf(fieldValue)));
                        }
                    }
                    if (StringUtils.isNotBlank(s2.toString())) {
                        sql.append(s1);
                        sql.append(s2);
                        sql.append(" end,");
                    }
                }

                String execSql = sql.toString().substring(0,sql.length()-1);

                execSql+=String.format(" where %s in ('%s')",fieldName,idSet.stream().collect(Collectors.joining("','")));

                Query query =entityManager.createNativeQuery(execSql);
                query.executeUpdate();

            }catch (Exception e){
                REPOSITORY_ERROR.doThrowException("dynamicBatchUpdate,"+this.getClass().getSimpleName(),e);
            }
        });

    }

    public void deleteById(Integer id) {
        getDao().deleteById(id);
    }

    public void delete(Bo bo) {
        Entity entity=bo.convert();
        getDao().delete(entity);
    }

    /**
     * 逻辑删除
     * @param id
     */
    @Transactional
    public void logicDeleteById(Integer id,String tableName){
        try {
            String sqlFormat = "update %s set is_delete=1 where id='%s'";

            String sql = String.format(sqlFormat,
                    tableName,id);

            Query query =entityManager.createNativeQuery(sql);
            query.executeUpdate();

        }catch (Exception e){
            REPOSITORY_ERROR.doThrowException("logicDeleteById,"+this.getClass().getSimpleName(),e);
        }
    }

    public Map<String,Long> getGroupCount(String field, JpaSpecBo jpaSpecBo) {
        try {
            String table = getTableName();
            Map<String,Long> map= Maps.newHashMap();
            StringBuilder whereCase=new StringBuilder("where 1=1 ");
            String whereSql = jpaSpecBo.getSql();
            if(StringUtils.isNotEmpty(whereSql)){
                whereCase.append("and ").append(whereSql);
            }
            String sql = String.format("select %s,count(%s) from %s %s group by %s",
                    field,field,table,whereCase,field);
            Query query =entityManager.createNativeQuery(sql);
            query.getResultList().forEach(object->{
                Object[] cells = (Object[]) object;
                map.put(String.valueOf(cells[0]),Long.valueOf(String.valueOf(cells[1])));
            });
            return map;
        }catch (Exception e){
            REPOSITORY_ERROR.doThrowException("getGroupCount",e);
        }
        return Maps.newHashMap();
    }

    protected abstract String getTableName();
}
