package com.lejian.laogang.log;


import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.lejian.laogang.pojo.bo.*;
import com.lejian.laogang.repository.*;
import com.lejian.laogang.repository.entity.HomeBedEntity;
import com.lejian.laogang.repository.entity.HomeDoctorEntity;
import com.lejian.laogang.repository.entity.IntelligentDeviceEntity;
import com.lejian.laogang.repository.entity.OldmanAttrEntity;
import com.lejian.laogang.util.LaogangContext;
import com.lejian.laogang.util.LjReflectionUtils;
import com.lejian.laogang.util.StringUtils;
import com.lejian.laogang.util.UserUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Aspect
@Component
public class LogRecordAop {

    @Autowired
    private LogRepository repository;

    private Gson gson = new Gson();

    @Pointcut("execution(* com.lejian.laogang.repository.AbstractRepository.save(..)) || execution(* com.lejian.laogang.repository.AbstractRepository.batchInsertEntity(..)) || execution(* com.lejian.laogang.repository.AbstractRepository.batchInsert(..)) || execution(* com.lejian.laogang.repository.AbstractRepository.saveAndReturn(..))")
    public void insert() {
    }

    @Pointcut("execution(* com.lejian.laogang.repository.AbstractRepository.dynamicUpdate(..)) || execution(* com.lejian.laogang.repository.AbstractRepository.dynamicUpdateByPkId(..)) || execution(* com.lejian.laogang.repository.AbstractRepository.batchUpdate(..)) || execution(* com.lejian.laogang.repository.AbstractRepository.dynamicBatchUpdate(..))")
    public void update() {
    }


    @Pointcut("execution(* com.lejian.laogang.repository.AbstractRepository.deleteById(..))")
    public void delete() {
    }

    @AfterReturning("insert()")
    public void afterReturnInsert(JoinPoint joinPoint) {
        Object obj = joinPoint.getArgs()[0];


        if (obj instanceof Collection) {
            List list = (List) obj;
            Object first = list.get(0);
            if (first instanceof BaseBo) {
                first = ((BaseBo) first).convert();
            }
            if (first.getClass().getAnnotation(LogRecord.class) == null) {
                return;
            }
            if (Lists.newArrayList("OldmanAttrEntity", "OldmanAttrBo").contains(first.getClass().getSimpleName())) {
                //oldman_attr 合成一条（数据量太多了）
                List<String> l = (List<String>) list.stream().map(i -> {
                    if (i instanceof BaseBo) {
                        i = ((BaseBo) i).convert();
                    }
                    return gson.toJson(i);
                }).collect(Collectors.toList());
                LogBo logBo = new LogBo();
                logBo.setUserId(UserUtils.getUser().getId());
                logBo.setTraceId(LaogangContext.get("traceId"));
                logBo.setPk(String.valueOf(LjReflectionUtils.getField(first, first.getClass().getAnnotation(LogRecord.class).value())));
                logBo.setPkType("oid");
                logBo.setData(StringUtils.join(l, "#"));
                logBo.setOperation(1);
                logBo.setTable("oldman_attr");
                repository.save(logBo);

            } else {
                for (Object o : list) {
                    if (o instanceof BaseBo) {
                        o = ((BaseBo) o).convert();
                    }
                    log(1, o);
                }
            }
        } else {
            if (obj instanceof BaseBo) {
                obj = ((BaseBo) obj).convert();
            }
            if (obj.getClass().getAnnotation(LogRecord.class) == null) {
                return;
            }
            log(1, obj);
        }
    }


    @AfterReturning("update()")
    public void afterReturnUpdate(JoinPoint joinPoint) {
        Object obj = joinPoint.getArgs()[0];


        if (obj instanceof Collection) {
            List list = (List) obj;
            Object first = list.get(0);
            if (first instanceof BaseBo) {
                first = ((BaseBo) first).convert();
            }
            if (first.getClass().getAnnotation(LogRecord.class) == null) {
                return;
            }
            for (Object o : list) {
                if (o instanceof BaseBo) {
                    o = ((BaseBo) o).convert();
                }
                log(0, o);
            }
        } else {
            if (obj instanceof BaseBo) {
                obj = ((BaseBo) obj).convert();
            }
            if (obj.getClass().getAnnotation(LogRecord.class) == null) {
                return;
            }
            log(0, obj);
        }

    }

    @Before("delete()")
    public void beforeDelete(JoinPoint joinPoint) {
        Object id = joinPoint.getArgs()[0];
        LogBo logBo = new LogBo();
        logBo.setUserId(UserUtils.getUser().getId());
        logBo.setTraceId(LaogangContext.get("traceId"));
        logBo.setOperation(2);
        logBo.setPkType(joinPoint.getTarget().getClass().getAnnotation(LogRecord.class).valueType());
        logBo.setTable(joinPoint.getTarget().getClass().getAnnotation(LogRecord.class).table());
        AbstractRepository abstractRepository = (AbstractRepository) joinPoint.getTarget();
        Object o = abstractRepository.getByPkId((Integer) id);
        if (abstractRepository instanceof IntelligentDeviceRepository) {
            IntelligentDeviceBo bo = (IntelligentDeviceBo) o;
            logBo.setPk(bo.getOldmanId()+"");
        } else if(abstractRepository instanceof HomeBedRepository){
            HomBedBo bo = (HomBedBo) o;
            logBo.setPk(bo.getOldmanId()+"");
        } else if(abstractRepository instanceof HomeDoctorRepository){
            HomeDoctorBo bo = (HomeDoctorBo) o;
            logBo.setPk(bo.getOldmanId()+"");
        }else{
            logBo.setPk(String.valueOf(id));
        }
        logBo.setData(gson.toJson(o));
        repository.save(logBo);
    }

    private void log(Integer type, Object entity) {
        LogBo logBo = new LogBo();
        logBo.setUserId(UserUtils.getUser().getId());
        logBo.setTraceId(LaogangContext.get("traceId"));
        logBo.setPk(String.valueOf(LjReflectionUtils.getField(entity, entity.getClass().getAnnotation(LogRecord.class).value())));
        logBo.setPkType(entity.getClass().getAnnotation(LogRecord.class).valueType());
        logBo.setData(gson.toJson(entity));
        logBo.setOperation(type);
        logBo.setTable(entity.getClass().getAnnotation(Table.class).name());
        repository.save(logBo);
    }


    @AfterReturning("execution(* com.lejian.laogang.repository.OldmanAttrRepository.deleteByOldmanId(..))")
    public void afterReturn1(JoinPoint joinPoint) {
        List obj = (List) joinPoint.getArgs()[0];
        obj.forEach(oldmanId -> {
            LogBo bo = new LogBo();
            bo.setOperation(2);
            bo.setPkType("oid");
            bo.setPk(String.valueOf(oldmanId));
            bo.setTable("oldman_arr");
            bo.setTraceId(LaogangContext.get("traceId"));
            bo.setUserId(UserUtils.getUser().getId());
            repository.save(bo);
        });

    }

    /**
     * 删除oldman_attr
     * 自定义data
     *
     * @param joinPoint
     */
    @AfterReturning("execution(* com.lejian.laogang.repository.OldmanAttrRepository.deleteByType(..))")
    public void afterReturn2(JoinPoint joinPoint) {
        Integer oldmanId = (Integer) joinPoint.getArgs()[0];
        List typeList = (List) joinPoint.getArgs()[1];

        LogBo bo = new LogBo();
        bo.setOperation(2);
        bo.setPkType("oid");
        bo.setPk(String.valueOf(oldmanId));
        bo.setTable("oldman_attr");
        bo.setTraceId(LaogangContext.get("traceId"));
        bo.setUserId(UserUtils.getUser().getId());
        bo.setData(StringUtils.join(typeList, ","));
        repository.save(bo);
    }

    @Before("execution(* com.lejian.laogang.controller.*Controller.*(..))")
    public void setTraceId() {
        LaogangContext.set("traceId", UUID.randomUUID().toString());
    }
}
