package com.lejian.laogang.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lejian.laogang.controller.contract.response.OperationLogResponse;
import com.lejian.laogang.enums.OperationLogEnum;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.LogBo;
import com.lejian.laogang.repository.AbstractRepository;
import com.lejian.laogang.repository.LogRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class OperationLogService {
    @Autowired
    private LogRepository logRepository;

    public OperationLogResponse oldman(Integer oldmanId) {
        OperationLogResponse response = new OperationLogResponse();
        List<OperationLogResponse.LogGroup> logGroupList = Lists.newArrayList();
        response.setLogGroupList(logGroupList);

        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getEqualMap().put("pk",String.valueOf(oldmanId));
        jpaSpecBo.getEqualMap().put("pkType","oid");
        List<LogBo> logBoList = logRepository.findWithSpec(jpaSpecBo);
        if (CollectionUtils.isEmpty(logBoList)){
            return response;
        }

        Map<String,List<LogBo>> tableMap = logBoList.stream().collect(Collectors.groupingBy(LogBo::getTable));
        List<OperationLogResponse.LogData> list = Lists.newArrayList();
        tableMap.forEach((k,v)-> list.addAll(OperationLogEnum.logData(k,v)));

        Map<String,List<OperationLogResponse.LogData>> traceMap = list.stream().collect(Collectors.groupingBy(OperationLogResponse.LogData::getTraceId));

        traceMap.forEach((k,v)->{
            OperationLogResponse.LogGroup logGroup = new OperationLogResponse.LogGroup();
            logGroup.setTraceId(k);
            logGroup.setTime(v.stream().min(Comparator.comparing(OperationLogResponse.LogData::getTime)).get().getTime());
            logGroup.setLogDataList(v);
            response.getLogGroupList().add(logGroup);
        });

        response.setLogGroupList(response.getLogGroupList().stream().sorted(Comparator.comparing(OperationLogResponse.LogGroup::getTime).reversed()).collect(Collectors.toList()));
        return response;
    }
}
