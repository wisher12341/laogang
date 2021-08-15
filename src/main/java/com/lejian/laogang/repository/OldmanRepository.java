package com.lejian.laogang.repository;


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
}
