package com.lejian.laogang.enums;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lejian.laogang.controller.contract.response.OperationLogResponse;
import com.lejian.laogang.log.LogFieldRecord;
import com.lejian.laogang.pojo.bo.LogBo;
import com.lejian.laogang.pojo.bo.OldmanBo;
import com.lejian.laogang.pojo.bo.OldmanLogBo;
import com.lejian.laogang.repository.entity.*;
import com.lejian.laogang.util.LjCollectionUtils;
import com.lejian.laogang.util.LjReflectionUtils;
import com.lejian.laogang.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.lejian.laogang.util.StringUtils.fromGson;

@Getter
@AllArgsConstructor
public enum OperationLogEnum {

    A1(Lists.newArrayList("oldman")) {
        @Override
        protected List<OperationLogResponse.LogData> createLogData(List<LogBo> logBoList, String table) {
            logBoList = logBoList.stream().sorted(Comparator.comparing(LogBo::getCreateTime)).collect(Collectors.toList());
            Map<String, Field> fieldMap = LjReflectionUtils.getFieldToMap(OldmanLogBo.class);
            Map<String, Field> entityfieldMap = LjReflectionUtils.getFieldToMap(OldmanEntity.class);
            List<OperationLogResponse.LogData> logDataList = Lists.newArrayList();
            for (int i = 0; i < logBoList.size(); i++) {
                LogBo newBo = logBoList.get(i);
                OldmanEntity newEntity = fromGson(newBo.getData(), OldmanEntity.class);
                OldmanLogBo newOldmanVo = OldmanBo.convert(newEntity).convertLogBo();
                OldmanEntity oldEntiy = i > 0 ? fromGson(logBoList.get(i - 1).getData(), OldmanEntity.class) : null;
                OldmanLogBo oldOldmanVo = oldEntiy != null ? OldmanBo.convert(oldEntiy).convertLogBo() : null;
                fieldMap.forEach((k, v) -> {
                    try {
                        if (!entityfieldMap.containsKey(k)) {
                            return;
                        }
                        v.setAccessible(true);
                        Object newData = v.get(newOldmanVo);
                        if (newData != null) {
                            Object oldData = oldOldmanVo != null ? v.get(oldOldmanVo) : null;
                            if ((oldData == null)
                                    || !String.valueOf(newData).equals(String.valueOf(oldData))) {
                                LogFieldRecord logFieldRecord = entityfieldMap.get(k).getAnnotation(LogFieldRecord.class);
                                if (logFieldRecord == null) {
                                    return;
                                }
                                if (oldData == null && logFieldRecord.defaultValue().equals(String.valueOf(newData))) {
                                    //db 默认值，不计入更新
                                    return;
                                }

                                OperationLogResponse.LogData logData = new OperationLogResponse.LogData();
                                logData.setNewData(String.valueOf(newData));
                                logData.setOperator(String.valueOf(newBo.getUserId()));
                                logData.setOldData(oldData != null ? String.valueOf(oldData) : null);
                                logData.setField(logFieldRecord.value());
                                logData.setType(operation(newBo.getOperation()));
                                logData.setTime(newBo.getCreateTime());
                                logData.setTraceId(newBo.getTraceId());
                                logDataList.add(logData);
                            }
                        }
                    } catch (Exception e) {
                    }
                });
            }
            return logDataList;
        }
    },

    A2(Lists.newArrayList("oldman_attr")) {
        @Override
        protected List<OperationLogResponse.LogData> createLogData(List<LogBo> logBoList, String table) {
            List<OperationLogResponse.LogData> list = Lists.newArrayList();
            logBoList = logBoList.stream().sorted(Comparator.comparing(LogBo::getCreateTime)).collect(Collectors.toList());

            Map<String, List<LogBo>> groupMap = Maps.newHashMap();

            logBoList.forEach(bo -> groupMap.computeIfAbsent(bo.getTraceId(), i -> Lists.newArrayList()).add(bo));

            List<List<LogBo>> group = groupMap.values().stream().sorted(Comparator.comparing(i -> i.get(0).getCreateTime())).collect(Collectors.toList());

            for (int i = 0; i < group.size(); i++) {
                if (i == 0) {
                    group.get(0).forEach(bo -> {
                        if (bo.getOperation() == 1) {
                            OperationLogResponse.LogData logData = new OperationLogResponse.LogData();
                            OldmanAttrEntity entity = fromGson(bo.getData(), OldmanAttrEntity.class);
                            logData.setNewData(BusinessEnum.find(entity.getValue(), OldmanAttrEnum.OldmanAttrType.findEnumClass(entity.getType())).getDesc());
                            if (StringUtils.isNotBlank(entity.getExt())) {
                                logData.setNewData(logData.getNewData() + "(" + entity.getExt() + ")");
                            }
                            logData.setField(OldmanAttrEnum.OldmanAttrType.find(entity.getType()).getDesc());
                            logData.setOperator(String.valueOf(bo.getUserId()));
                            logData.setType("新增");
                            logData.setTime(bo.getCreateTime());
                            logData.setTraceId(bo.getTraceId());
                            list.add(logData);
                        }
                    });
                } else {
                    //key:type
                    Map<Integer, List<OldmanAttrEntity>> n = group.get(i).stream().filter(it -> it.getOperation() == 1).map(it -> fromGson(it.getData(), OldmanAttrEntity.class)).collect(Collectors.groupingBy(OldmanAttrEntity::getType));
                    Map<Integer, List<OldmanAttrEntity>> o = group.get(i - 1).stream().filter(it -> it.getOperation() == 1).map(it -> fromGson(it.getData(), OldmanAttrEntity.class)).collect(Collectors.groupingBy(OldmanAttrEntity::getType));
                    LogBo logBo = group.get(i).get(0);
                    //更新判断
                    n.forEach((k, v) -> {
                        if (o.containsKey(k)) {
                            List<String> oldValueList = o.get(k).stream().map(x -> x.getValue() + "_" + (StringUtils.isNotBlank(x.getExt()) ? x.getExt() : "")).collect(Collectors.toList());
                            List<String> newValueList = v.stream().map(x -> x.getValue() + "_" + (StringUtils.isNotBlank(x.getExt()) ? x.getExt() : "")).collect(Collectors.toList());
                            if (LjCollectionUtils.existDiff(oldValueList, newValueList)) {
                                OperationLogResponse.LogData logData = new OperationLogResponse.LogData();
                                List<String> oldStr = oldValueList.stream().map(vv -> {
                                    String type = BusinessEnum.find(Integer.valueOf(vv.split("_")[0]), OldmanAttrEnum.OldmanAttrType.findEnumClass(k)).getDesc();
                                    if (vv.split("_").length > 1) {
                                        type += "(" + vv.split("_")[1] + ")";
                                    }
                                    return type;
                                }).collect(Collectors.toList());
                                logData.setOldData(StringUtils.join(oldStr, ","));
                                List<String> newStr = newValueList.stream().map(vv -> {
                                    String type = BusinessEnum.find(Integer.valueOf(vv.split("_")[0]), OldmanAttrEnum.OldmanAttrType.findEnumClass(k)).getDesc();
                                    if (vv.split("_").length > 1) {
                                        type += "(" + vv.split("_")[1] + ")";
                                    }
                                    return type;
                                }).collect(Collectors.toList());
                                logData.setNewData(StringUtils.join(newStr, ","));

                                logData.setField(OldmanAttrEnum.OldmanAttrType.find(k).getDesc());
                                logData.setOperator(String.valueOf(logBo.getUserId()));
                                logData.setType("更新");
                                logData.setTime(logBo.getCreateTime());
                                logData.setTraceId(logBo.getTraceId());
                                list.add(logData);
                            }
                        }
                    });
                    //新增判断
                    n.forEach((k, v) -> {
                        if (!o.containsKey(k)) {
                            v.forEach(vv -> {
                                OperationLogResponse.LogData logData = new OperationLogResponse.LogData();
                                logData.setNewData(BusinessEnum.find(vv.getValue(), OldmanAttrEnum.OldmanAttrType.findEnumClass(k)).getDesc());
                                if (StringUtils.isNotBlank(vv.getExt())) {
                                    logData.setNewData(logData.getNewData() + "(" + vv.getExt() + ")");
                                }
                                logData.setField(OldmanAttrEnum.OldmanAttrType.find(k).getDesc());
                                logData.setOperator(String.valueOf(logBo.getUserId()));
                                logData.setType("新增");
                                logData.setTime(logBo.getCreateTime());
                                logData.setTraceId(logBo.getTraceId());
                                list.add(logData);
                            });
                        }
                    });
                    //删除判断
                    List<String> delType = Lists.newArrayList();
                    LogBo ll = group.get(i).stream().filter(xx -> xx.getOperation() == 2).findFirst().orElse(null);
                    if (ll != null) {
                        delType.addAll(Lists.newArrayList(ll.getData().split(",")).stream().filter(xx -> !n.containsKey(Integer.valueOf(xx))).collect(Collectors.toList()));
                    }
                    o.forEach((k, v) -> {
                        if (delType.contains(String.valueOf(k))) {
                            OperationLogResponse.LogData logData = new OperationLogResponse.LogData();
                            List<String> oldStr = v.stream().map(xxx -> xxx.getValue() + "_" + (StringUtils.isNotBlank(xxx.getExt()) ? xxx.getExt() : "")).map(vv -> {
                                String type = BusinessEnum.find(Integer.valueOf(vv.split("_")[0]), OldmanAttrEnum.OldmanAttrType.findEnumClass(k)).getDesc();
                                if (vv.split("_").length > 1) {
                                    type += "(" + vv.split("_")[1] + ")";
                                }
                                return type;
                            }).collect(Collectors.toList());
                            logData.setOldData(StringUtils.join(oldStr, ","));
                            logData.setField(OldmanAttrEnum.OldmanAttrType.find(k).getDesc());
                            logData.setOperator(String.valueOf(logBo.getUserId()));
                            logData.setType("删除");
                            logData.setTime(logBo.getCreateTime());
                            logData.setTraceId(logBo.getTraceId());
                            list.add(logData);
                        }
                    });
                }
            }

            List<OperationLogResponse.LogData> result = Lists.newArrayList();
            //同trace 同type合并
            Map<String, List<OperationLogResponse.LogData>> map = list.stream().collect(Collectors.groupingBy(i -> i.getTraceId() + i.getField()));
            map.forEach((k, v) -> {
                if (v.size() == 1) {
                    result.addAll(v);
                } else {
                    List<String> newData = v.stream().map(i -> i.getNewData()).collect(Collectors.toList());
                    OperationLogResponse.LogData l = v.get(0);
                    l.setNewData(StringUtils.join(newData, ","));
                    result.add(l);
                }
            });
            return result;
        }
    },

    A3(Lists.newArrayList("intelligent_device","home_bed","home_doctor")) {
        @Override
        protected List<OperationLogResponse.LogData> createLogData(List<LogBo> logBoList, String table) {
            Class clazz = null;
            switch (table){
                case "intelligent_device":
                    clazz = IntelligentDeviceEntity.class;
                    break;
                case "home_bed":
                    clazz = HomeBedEntity.class;
                    break;
                case "home_doctor":
                    clazz = HomeDoctorEntity.class;
                    break;
            }

            logBoList = logBoList.stream().sorted(Comparator.comparing(LogBo::getCreateTime)).collect(Collectors.toList());
            Map<String, Field> entityfieldMap = LjReflectionUtils.getFieldToMap(clazz);
            List<OperationLogResponse.LogData> logDataList = Lists.newArrayList();
            for (int i = 0; i < logBoList.size(); i++) {
                LogBo newBo = logBoList.get(i);
                Object newEntity = fromGson(newBo.getData(), clazz);
                Object oldEntiy = i > 0 && newBo.getOperation() != 2 ? fromGson(logBoList.get(i - 1).getData(), clazz) : null;
                if (newBo.getOperation() == 2){
                    //删除
                    Object old = fromGson(logBoList.get(i - 1).getData(), clazz);
                    if (old == null){
                        continue;
                    }
                    entityfieldMap.forEach((k, v) -> {
                        try {
                            v.setAccessible(true);
                            LogFieldRecord logFieldRecord = v.getAnnotation(LogFieldRecord.class);
                            if (logFieldRecord == null) {
                                return;
                            }
                            Object oldData = v.get(old);
                            OperationLogResponse.LogData logData = new OperationLogResponse.LogData();
                            logData.setOperator(String.valueOf(newBo.getUserId()));
                            logData.setOldData(String.valueOf(oldData));
                            logData.setField(logFieldRecord.value());
                            logData.setType(operation(newBo.getOperation()));
                            logData.setTime(newBo.getCreateTime());
                            logData.setTraceId(newBo.getTraceId());
                            logDataList.add(logData);
                        }catch (Exception e){}
                    });
                }else {
                    entityfieldMap.forEach((k, v) -> {
                        try {
                            v.setAccessible(true);
                            Object newData = v.get(newEntity);
                            if (newData != null) {
                                Object oldData = oldEntiy != null ? v.get(oldEntiy) : null;
                                if ((oldData == null)
                                        || !String.valueOf(newData).equals(String.valueOf(oldData))) {
                                    LogFieldRecord logFieldRecord = v.getAnnotation(LogFieldRecord.class);
                                    if (logFieldRecord == null) {
                                        return;
                                    }
                                    if (oldData == null && logFieldRecord.defaultValue().equals(String.valueOf(newData))) {
                                        //db 默认值，不计入更新
                                        return;
                                    }

                                    OperationLogResponse.LogData logData = new OperationLogResponse.LogData();
                                    logData.setNewData(String.valueOf(newData));
                                    logData.setOperator(String.valueOf(newBo.getUserId()));
                                    logData.setOldData(oldData != null ? String.valueOf(oldData) : null);
                                    logData.setField(logFieldRecord.value());
                                    logData.setType(operation(newBo.getOperation()));
                                    logData.setTime(newBo.getCreateTime());
                                    logData.setTraceId(newBo.getTraceId());
                                    logDataList.add(logData);
                                }
                            }
                        } catch (Exception e) {
                        }
                    });
                }
            }
            return logDataList;
        }
    },
    ;


    private List<String> table;

    protected abstract List<OperationLogResponse.LogData> createLogData(List<LogBo> logBoList, String table);

    public static List<OperationLogResponse.LogData> logData(String table, List<LogBo> logBoList) {
        for (OperationLogEnum operationLogEnum : OperationLogEnum.values()) {
            if (operationLogEnum.getTable().contains(table)) {
                if (table.equals("oldman_attr")) {
                    //特殊处理，一个bo分成多个
                    logBoList = logBoList.stream()
                            .map(item -> Arrays.stream(item.getData().split("#")).map(i -> {
                                LogBo b = new LogBo();
                                BeanUtils.copyProperties(item, b);
                                b.setData(i);
                                return b;
                            }).collect(Collectors.toList()))
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList());
                }
                return operationLogEnum.createLogData(logBoList,table);
            }
        }
        return Lists.newArrayList();
    }

    public String operation(Integer operation) {
        switch (operation) {
            case 0:
                return "更新";
            case 1:
                return "新增";
            case 2:
                return "删除";
        }
        return null;
    }
}
