package com.lejian.laogang.repository;


import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.OldmanBo;
import com.lejian.laogang.repository.dao.OldmanDao;
import com.lejian.laogang.repository.entity.OldmanEntity;
import com.lejian.laogang.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Query;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lejian.laogang.common.ComponentRespCode.REPOSITORY_ERROR;
import static com.lejian.laogang.util.DateUtils.YYMMDDHHMMSS;


@Repository
public class OldmanRepository extends AbstractSpecificationRepository<OldmanBo, OldmanEntity> {

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

    public Map<String, Long> getZdFinishGroupCount(String group, JpaSpecBo jpaSpecBo) {
        try {
            Map<String, Long> map = Maps.newHashMap();
            String whereSql = jpaSpecBo.getSql("o.");
            String sql = "select o." + group + ",count(o." + group + ") from oldman o left join oldman_attr oa on o.id=oa.oldman_id " +
                    "where oa.type=13 and o.is_zd=1 " + (StringUtils.isNotBlank(whereSql) ? " and " + whereSql : "") +
                    " group by o." + group;
            Query query = entityManager.createNativeQuery(sql);
            query.getResultList().forEach(object -> {
                Object[] cells = (Object[]) object;
                map.put(String.valueOf(cells[0]), Long.valueOf(String.valueOf(cells[1])));
            });
            return map;
        } catch (Exception e) {
            REPOSITORY_ERROR.doThrowException("getZdFinishGroupCount", e);
        }
        return Maps.newHashMap();
    }

    public List<OldmanBo> getByIdCards(List<String> idCardList) {
        return oldmanDao.findByIdCardIn(idCardList).stream().map(OldmanBo::convert).collect(Collectors.toList());
    }

    public Map<String, Long> getOldmanBaseGroupByAttr(String field, List<Integer> typeList, JpaSpecBo jpaSpecBo) {
        try {
            String where = "";
            if (jpaSpecBo != null) {
                String s = jpaSpecBo.getSql("o.");
                if (StringUtils.isNotBlank(s)) {
                    where = " and " + s;
                }
            }
            Map<String, Long> map = Maps.newHashMap();
            String a = Joiner.on(",").join(typeList);
            ;
            String sql = "select " + field + ",count(1) from (" +
                    "select distinct o.id_card,o." + field + " from oldman o left join oldman_attr oa on o.id=oa.oldman_id" +
                    " where oa.type in (" + a + ") " + where +
                    ") a group by " + field;
            Query query = entityManager.createNativeQuery(sql);
            query.getResultList().forEach(object -> {
                Object[] cells = (Object[]) object;
                map.put(String.valueOf(cells[0]), Long.valueOf(String.valueOf(cells[1])));
            });
            return map;
        } catch (Exception e) {
            REPOSITORY_ERROR.doThrowException("getOldmanBaseGroupByAttr", e);
        }
        return Maps.newHashMap();
    }

    public Map<String, Long> getOldmanGroupCount(String field, JpaSpecBo jpaSpecBo) {
        try {
            Map<String, Long> map = Maps.newHashMap();
            StringBuilder whereCase = new StringBuilder("where 1=1 ");
            String whereSql = jpaSpecBo.getSql();
            if (StringUtils.isNotEmpty(whereSql)) {
                whereCase.append("and ").append(whereSql);
            }
            String sql = String.format("select %s,count(1) from oldman o left join(                             \n" +
                            "select a.id,GROUP_CONCAT(b.type SEPARATOR ',') AS types " +
                            "from oldman a left join oldman_attr b on a.id=b.oldman_id " +
                            "group by a.id) oa on o.id=oa.id " +
                            " %s group by %s",
                    field, whereCase, field);
            Query query = entityManager.createNativeQuery(sql);
            query.getResultList().forEach(object -> {
                Object[] cells = (Object[]) object;
                String key = String.valueOf(cells[0]);
                if (StringUtils.isNotBlank(key)) {
                    map.put(key, Long.valueOf(String.valueOf(cells[1])));
                }
            });
            return map;
        } catch (Exception e) {
            REPOSITORY_ERROR.doThrowException("getGroupCount", e);
        }
        return Maps.newHashMap();
    }

    public List<OldmanBo> findByPage(Integer pageNo, Integer pageSize, Pair<String, String> whereSql) {
        List<OldmanBo> oldmanBoList = Lists.newArrayList();
        try {
            String where = "";
            if (StringUtils.isNotBlank(whereSql.getFirst()) && StringUtils.isNotBlank(whereSql.getSecond())) {
                where = whereSql.getFirst() + " and " + whereSql.getSecond();
            } else {
                where = whereSql.getFirst() + whereSql.getSecond();
            }
            if (StringUtils.isBlank(where)) {
                where = "1=1";
            }
            String sql;
            if (StringUtils.isBlank(whereSql.getSecond())) {
                sql = String.format("select o.id,o.name,o.male,o.birthday,o.area_village,o.id_card,o.phone from oldman o where %s limit %s,%s",where,pageNo,pageSize);
            } else {
                sql = String.format("select o.id,o.name,o.male,o.birthday,o.area_village,o.id_card,o.phone,oa.type from oldman o left join(\n" +
                                " select a.id,GROUP_CONCAT(CONCAT(\"@\",b.type,\"_\",b.value,\"_\",b.ext) SEPARATOR ',') AS type\n" +
                                " from oldman a left join oldman_attr b on a.id=b.oldman_id\n" +
                                " group by a.id) oa on o.id=oa.id\n" +
                                " where %s limit %s,%s",
                        where, pageNo, pageSize);
            }
            Query query = entityManager.createNativeQuery(sql);
            query.getResultList().forEach(object -> {
                Object[] cells = (Object[]) object;
                OldmanBo oldmanBo = new OldmanBo();
                oldmanBo.setId((Integer) cells[0]);
                oldmanBo.setName((String) cells[1]);
                oldmanBo.setMale(BusinessEnum.find((Integer) cells[2], OldmanEnum.Male.class));
                oldmanBo.setBirthday(((Date) cells[3]).toLocalDate());
                oldmanBo.setAreaVillage((String) cells[4]);
                oldmanBo.setIdCard((String) cells[5]);
                oldmanBo.setPhone((String) cells[6]);
                oldmanBoList.add(oldmanBo);
            });
            return oldmanBoList;
        } catch (Exception e) {
            REPOSITORY_ERROR.doThrowException("findByPage", e);
        }
        return oldmanBoList;
    }

    public Long count(Pair<String, String> whereSql) {
        try {
            String where = "";
            if (StringUtils.isNotBlank(whereSql.getFirst()) && StringUtils.isNotBlank(whereSql.getSecond())) {
                where = whereSql.getFirst() + " and " + whereSql.getSecond();
            } else {
                where = whereSql.getFirst() + whereSql.getSecond();
            }
            if (StringUtils.isBlank(where)) {
                where = "1=1";
            }
            String sql;
            if (StringUtils.isBlank(whereSql.getSecond())) {
                sql = String.format("select count(1) from oldman o where %s ",where);
            } else {
                sql = String.format("select count(1) from oldman o left join(\n" +
                                " select a.id,GROUP_CONCAT(CONCAT(\"@\",b.type,\"_\",b.value,\"_\",b.ext) SEPARATOR ',') AS type\n" +
                                " from oldman a left join oldman_attr b on a.id=b.oldman_id\n" +
                                " group by a.id) oa on o.id=oa.id\n" +
                                " where %s",
                        where);
            }
            Query query = entityManager.createNativeQuery(sql);
            return ((BigInteger) query.getResultList().get(0)).longValue();
        } catch (Exception e) {
            REPOSITORY_ERROR.doThrowException("count", e);
        }
        return 0L;
    }
}
