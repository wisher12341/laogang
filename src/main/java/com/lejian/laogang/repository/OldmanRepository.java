package com.lejian.laogang.repository;


import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.OldmanBo;
import com.lejian.laogang.repository.dao.OldmanDao;
import com.lejian.laogang.repository.entity.OldmanEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Query;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lejian.laogang.common.ComponentRespCode.REPOSITORY_ERROR;


@Repository
public class OldmanRepository extends AbstractSpecificationRepository<OldmanBo,OldmanEntity> {

    @Autowired
    private OldmanDao oldmanDao;


    @Override
    protected JpaRepository getDao() {
        return oldmanDao;
    }

    @Override
    protected OldmanBo convert(OldmanEntity oldmanEntity) {
        return OldmanBo.convert(oldmanEntity);
    }


    @Override
    protected String getTableName() {
        return "oldman";
    }

    public Map<String,Long> getZdFinishGroupCount(String group, JpaSpecBo jpaSpecBo) {
        try {
            Map<String,Long> map= Maps.newHashMap();
            String whereSql = jpaSpecBo.getSql("o.");
            String sql = "select o."+group+",count(o."+group+") from oldman o left join oldman_attr oa on o.id=oa.oldman_id " +
                            "where oa.type=13 and o.is_zd=1 and " + whereSql +
                            " group by o."+group;
            Query query =entityManager.createNativeQuery(sql);
            query.getResultList().forEach(object->{
                Object[] cells = (Object[]) object;
                map.put(String.valueOf(cells[0]),Long.valueOf(String.valueOf(cells[1])));
            });
            return map;
        }catch (Exception e){
            REPOSITORY_ERROR.doThrowException("getZdFinishGroupCount",e);
        }
        return Maps.newHashMap();
    }

    public List<OldmanBo> getByIdCards(List<String> idCardList) {
        return oldmanDao.findByIdCardIn(idCardList).stream().map(OldmanBo::convert).collect(Collectors.toList());
    }

    public Map<String,Long> getOldmanBaseGroupByAttr(String field, List<Integer> typeList,JpaSpecBo jpaSpecBo) {
        try {
            String where = "";
            if (jpaSpecBo!=null){
                where = " and "+jpaSpecBo.getSql("o.");
            }
            Map<String,Long> map= Maps.newHashMap();
            String a= Joiner.on(",").join(typeList); ;
            String sql = "select "+field+",count(1) from ("+
                    "select distinct o.id_card,o."+field+" from oldman o left join oldman_attr oa on o.id=oa.oldman_id"+
                    " where oa.type in ("+a+") "+where+
            ") a group by "+field;
            Query query =entityManager.createNativeQuery(sql);
            query.getResultList().forEach(object->{
                Object[] cells = (Object[]) object;
                map.put(String.valueOf(cells[0]),Long.valueOf(String.valueOf(cells[1])));
            });
            return map;
        }catch (Exception e){
            REPOSITORY_ERROR.doThrowException("getOldmanBaseGroupByAttr",e);
        }
        return Maps.newHashMap();
    }

    public Map<String, Long> getOldmanGroupCount(String field, JpaSpecBo jpaSpecBo) {
        try {

            Map<String,Long> map= Maps.newHashMap();
            StringBuilder whereCase=new StringBuilder("where 1=1 ");
            String whereSql = jpaSpecBo.getSql();
            if(StringUtils.isNotEmpty(whereSql)){
                whereCase.append("and ").append(whereSql);
            }
            //todo
            String sql = String.format("select o.male,count(1) from oldman o left join(                             \n" +
                            "select a.id,GROUP_CONCAT(b.type SEPARATOR ',') AS types \n" +
                            "from oldman a left join oldman_attr b on a.id=b.oldman_id\n" +
                            "group by a.id) oa on o.id=oa.id where oa.types like \"%11%\" group by o.male",
                    field,field,whereCase,field);
            Query query =entityManager.createNativeQuery(sql);
            query.getResultList().forEach(object->{
                Object[] cells = (Object[]) object;
                String key = String.valueOf(cells[0]);
                if (StringUtils.isNotBlank(key)) {
                    map.put(key, Long.valueOf(String.valueOf(cells[1])));
                }
            });
            return map;
        }catch (Exception e){
            REPOSITORY_ERROR.doThrowException("getGroupCount",e);
        }
        return Maps.newHashMap();
    }
}
