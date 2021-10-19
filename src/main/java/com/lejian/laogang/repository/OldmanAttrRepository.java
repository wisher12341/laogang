package com.lejian.laogang.repository;


import com.google.common.collect.Maps;
import com.lejian.laogang.controller.contract.request.OldmanParam;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.OldmanAttrBo;
import com.lejian.laogang.pojo.bo.OldmanBo;
import com.lejian.laogang.repository.dao.OldmanAttrDao;
import com.lejian.laogang.repository.dao.OldmanDao;
import com.lejian.laogang.repository.entity.OldmanAttrEntity;
import com.lejian.laogang.repository.entity.OldmanEntity;
import com.lejian.laogang.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lejian.laogang.common.ComponentRespCode.REPOSITORY_ERROR;


@Repository
public class OldmanAttrRepository extends AbstractSpecificationRepository<OldmanAttrBo,OldmanAttrEntity> {

    @Autowired
    private OldmanAttrDao dao;


    @Override
    protected JpaRepository getDao() {
        return dao;
    }

    @Override
    protected OldmanAttrBo convert(OldmanAttrEntity entity) {
        return OldmanAttrBo.convert(entity);
    }

    @Override
    protected String getTableName() {
        return "oldman_attr";
    }

    public Long typeCount(JpaSpecBo jpaSpecBo, OldmanParam oldmanParam) {
        try {
            String where = jpaSpecBo.getSql("a.");
            String sql;
            String oldmanWhere = oldmanParam.convert().getSql("o.");
            if (StringUtils.isBlank(oldmanWhere)){
                sql = "select count(distinct a.oldman_id) from oldman_attr a where "+where;
            }else{
                sql = "select count(distinct a.oldman_id) from oldman_attr a" +
                        " left join oldman o on o.id=a.oldman_id  where "+where +" and "+oldmanWhere;
            }

            Query query =entityManager.createNativeQuery(sql);
            return Long.valueOf(String.valueOf(query.getResultList().get(0)));

        }catch (Exception e){
            REPOSITORY_ERROR.doThrowException("typeCount",e);
        }
        return 0L;
    }

    public Map<String, Long> getExtGroup(Integer type, Integer value,String where) {
        Map<String, Long> map = Maps.newHashMap();
        try {
            String sql = String.format("select a.ext,count(1) from oldman_attr a " +
                    " left join oldman o on o.id=a.oldman_id where a.type='%s' and a.value='%s' %s group by ext",type,value, StringUtils.isNotBlank(where)?" and "+where:"");

            Query query =entityManager.createNativeQuery(sql);
            query.getResultList().forEach(object->{
                Object[] cells = (Object[]) object;
                map.put(String.valueOf(cells[0]),Long.valueOf(String.valueOf(cells[1])));
            });

        }catch (Exception e){
            REPOSITORY_ERROR.doThrowException("getExtGroup",e);
        }
        return map;
    }

    public Map<String, Long> getGroupCountWithOldman(String oldmanWhere, String type) {
        try {
            Map<String,Long> map= Maps.newHashMap();
            if (StringUtils.isEmpty(oldmanWhere)){
                oldmanWhere= "1=1";
            }
            String sql = String.format("select a.value,count(1) from oldman_attr a " +
                            " left join oldman o on o.id= a.oldman_id " +
                    "where %s and a.type=%s group by a.value",oldmanWhere,type);
            Query query =entityManager.createNativeQuery(sql);
            query.getResultList().forEach(object->{
                Object[] cells = (Object[]) object;
                String key = String.valueOf(cells[0]);
                if (org.apache.commons.lang.StringUtils.isNotBlank(key)) {
                    map.put(key, Long.valueOf(String.valueOf(cells[1])));
                }
            });
            return map;
        }catch (Exception e){
            REPOSITORY_ERROR.doThrowException("getGroupCount",e);
        }
        return Maps.newHashMap();

    }

    public void deleteByOldmanId(List<Integer> oldmanIdList) {
        try {
            String where = StringUtils.join(oldmanIdList.stream().map(item->"'"+item+"'").collect(Collectors.toList()).toArray(),",");
            String sql = String.format("delete from oldman_attr where oldman_id in (%s)",where);
            Query query =entityManager.createNativeQuery(sql);
            query.executeUpdate();
        }catch (Exception e){
            REPOSITORY_ERROR.doThrowException("deleteByOldmanId",e);
        }
    }

    public void deleteByType(Integer id, List<Integer> typeList) {
        try {
            if (CollectionUtils.isEmpty(typeList) || id ==null){
                return;
            }
            String where = StringUtils.join(typeList.stream().map(item->"'"+item+"'").collect(Collectors.toList()).toArray(),",");
            String sql = String.format("delete from oldman_attr where oldman_id ='%s' and type in (%s)",id,where);
            Query query =entityManager.createNativeQuery(sql);
            query.executeUpdate();
        }catch (Exception e){
            REPOSITORY_ERROR.doThrowException("deleteByOldmanId",e);
        }
    }
}
