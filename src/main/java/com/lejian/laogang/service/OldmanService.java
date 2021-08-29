package com.lejian.laogang.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lejian.laogang.check.bo.CheckResultBo;
import com.lejian.laogang.controller.contract.request.OldmanParam;
import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.enums.*;
import com.lejian.laogang.enums.label.LabelBaseEnum;
import com.lejian.laogang.pojo.bo.*;
import com.lejian.laogang.pojo.vo.LocationVo;
import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.repository.*;
import com.lejian.laogang.repository.entity.OldmanEntity;
import com.lejian.laogang.util.DateUtils;
import com.lejian.laogang.util.LjReflectionUtils;
import com.lejian.laogang.util.TrendUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.lejian.laogang.common.ComponentRespCode.REFLECTION_ERROR;
import static com.lejian.laogang.common.Constant.IMPORT_RESET;
import static com.lejian.laogang.util.DateUtils.YYMMDD;

@Service
public class OldmanService {

    private static final int PART_NUM = 100;

    @Autowired
    private OldmanRepository oldmanRepository;
    @Autowired
    private OldmanAttrRepository oldmanAttrRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private OldmanViewRepository oldmanViewRepository;


    public Map<String, Object> getGroupCount(List<String> fieldNameList, List<String> labelIdList) {
        JpaSpecBo jpaSpecBo = LabelBaseEnum.generateJpaSpecBo(labelIdList);
        Map<String, Object> map = Maps.newHashMap();
        fieldNameList.forEach(fieldName -> {
            Map<String, Long> result;
            if (OldmanEntity.haveField(fieldName)) {
                result = oldmanViewRepository.getGroupCount(fieldName,jpaSpecBo);
            } else {
                //不支持可视化标签筛选 labelIdList
                Map<String, String> attrWhere = OldmanAttrEnum.generateAttrWhere(fieldName);
                jpaSpecBo.getEqualMap().putAll(attrWhere);
                //不用对老人去重
                result = oldmanAttrRepository.getGroupCount("value", jpaSpecBo);
            }
            Map<String, Long> voResult = Maps.newHashMap();
            result.forEach((k, v) -> {
                if (NumberUtils.isNumber(k)) {
                    BusinessEnum businessEnum = BusinessEnum.find(Integer.valueOf(k), fieldName);
                    if (businessEnum != null) {
                        voResult.put(businessEnum.getDesc(), v);
                    } else {
                        voResult.put(k, v);
                    }
                } else {
                    voResult.put(k, v);
                }
            });
            map.put(fieldName, voResult);
        });
        return map;
    }

    public List<OldmanVo> getByPage(OldmanParam oldmanParam, PageParam pageParam) {
        List<OldmanBo> oldmanBoList;
        if (oldmanParam.getIsView()) {
            JpaSpecBo jpaSpecBo = oldmanParam.convert();
            oldmanBoList = oldmanViewRepository.findByPageWithSpec(pageParam.getPageNo(), pageParam.getPageSize(),jpaSpecBo );
        }else{
            oldmanBoList = oldmanRepository.findByPageWithSpec(pageParam.getPageNo(), pageParam.getPageSize(), oldmanParam.convert());

        }
        return oldmanBoList.stream().map(OldmanBo::convertVo).collect(Collectors.toList());
    }

    public Map<String, Object> getAgeGroupCount(List<String> labelIdList) {
        Map<String, Object> map = Maps.newHashMap();
        JpaSpecBo jpaSpecBo = LabelBaseEnum.generateJpaSpecBo(labelIdList);

        jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(60).toLocalDate());
        jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(69).toLocalDate());
        map.put("60-69", oldmanViewRepository.getGroupCount("male", jpaSpecBo));

        jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(70).toLocalDate());
        jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(79).toLocalDate());
        map.put("70-79", oldmanViewRepository.getGroupCount("male", jpaSpecBo));

        jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(80).toLocalDate());
        jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(89).toLocalDate());
        map.put("80-89", oldmanViewRepository.getGroupCount("male", jpaSpecBo));
        return map;
    }

    public List<LocationVo> getLocation(OldmanParam oldmanParam) {
        if (oldmanParam.getIsView()){
            List<LocationVo> locationVoList = Lists.newArrayList();
            Map<String, Long> locationMap = oldmanViewRepository.getGroupCount("location", oldmanParam.convert());
            locationMap.forEach((k,v)->{
                String[] arr = k.split("_");
                if (arr.length==2) {
                    LocationVo vo = new LocationVo();
                    vo.setCount(v + "");
                    vo.setLng(arr[0]);
                    vo.setLat(arr[1]);
                    locationVoList.add(vo);
                }
            });
            return locationVoList;
        }else {
            Map<String, Long> locationMap =oldmanRepository.getGroupCount("location_id", oldmanParam.convert());
            List<LocationBo> locationBoList = locationRepository.getByPkIds(locationMap.keySet().stream().filter(item -> !item.equals("null")).map(Integer::valueOf).collect(Collectors.toList()));
            return locationBoList.stream().map(item -> item.convertVo(locationMap)).collect(Collectors.toList());
        }
    }

    public Map<String, Object> getCount(List<OldmanParam> request) {
        Map<String, Object> map = Maps.newHashMap();
        for (int i = 1; i <= request.size(); i++) {
            map.put(i + "", oldmanRepository.countWithSpec(request.get(i - 1).convert()));
        }
        return map;
    }

    public Map<String, Object> getZdFinish(String group, OldmanParam oldmanParam) {
        Map<String, Long> zdTotal = oldmanRepository.getGroupCount(group, oldmanParam.convert());
        Map<String, Long> zdFinish = oldmanRepository.getZdFinishGroupCount(group, oldmanParam.convert());

        Map<String, Object> result = Maps.newHashMap();
        zdTotal.forEach((k, v) -> {
            if (zdFinish.containsKey(k)) {
                result.put(k, Double.valueOf(zdFinish.get(k)) / Double.valueOf(v));
            } else {
                result.put(k, 0);
            }
        });
        return result;
    }

    @Transactional
    public List<CheckResultBo> addOldmanByExcel(Pair<List<String>, List<List<String>>> excelData) {
        List<String> titleList = excelData.getFirst();
        List<List<String>> valueList = excelData.getSecond();

//        List<CheckResultBo> checkResultBoList=checkOldmanImport(excelData);
//        if(CollectionUtils.isNotEmpty(checkResultBoList)){
//            return checkResultBoList;
//        }


        //todo 临时去重 身份证
        List<List<String>> newList = Lists.newArrayList();
        List<String> idCardList = Lists.newArrayList();
        for (int i = 0; i < valueList.size(); i++) {
            if (idCardList.contains(valueList.get(i).get(7).toLowerCase())) {
                continue;
            } else {
                newList.add(valueList.get(i));
                idCardList.add(valueList.get(i).get(7).toLowerCase());
            }
        }


        List<List<List<String>>> partList = Lists.partition(newList, 500);
        partList.forEach(list -> {
            addOldmanBaseInfo(titleList, list);
            addOldmanAttr(titleList, list);
        });

        return Lists.newArrayList();
    }

    private List<CheckResultBo> addOldmanAttr(List<String> titleList, List<List<String>> valueList) {
        //todo
//        clearOldmanAttr(oldmanIdList);
        List<OldmanAttrBo> oldmanAttrBoList = Lists.newArrayList();

        try {
            for (int i = 0; i < valueList.size(); i++) {
                String idCard = "";
                for (int j = 0; j < titleList.size(); j++) {
                    ExcelEnum oldmanExcelEnum = ExcelEnum.findFieldName(titleList.get(j), OldmanAttrExcelEnum.class);
                    if (oldmanExcelEnum == OldmanAttrExcelEnum.ID_CARD) {
                        idCard = valueList.get(i).get(j);
                        break;
                    }
                }
                for (int j = 0; j < titleList.size(); j++) {
                    ExcelEnum oldmanExcelEnum = ExcelEnum.findFieldName(titleList.get(j), OldmanAttrExcelEnum.class);
                    if (oldmanExcelEnum == null || oldmanExcelEnum == OldmanAttrExcelEnum.ID_CARD) {
                        continue;
                    }
                    Object value = valueList.get(i).get(j);
                    if (IMPORT_RESET.contains(String.valueOf(value))) {
                        continue;
                    }
                    if (oldmanExcelEnum.getEnumType() == null) {
                        OldmanAttrBo attrBo = new OldmanAttrBo();
                        attrBo.setIdCard(idCard);
                        oldmanExcelEnum.handle(attrBo, value);
                        oldmanAttrBoList.add(attrBo);
                    } else {
                        String enumValue = value.toString().split("〖")[0];
                        String ext = value.toString().split("〖").length == 2 ? value.toString().split("〖")[1].replaceAll("〗", "") : Strings.EMPTY;
                        String[] arr = enumValue.split("┋");
                        for (String v : arr) {
                            BusinessEnum val = BusinessEnum.find(v, oldmanExcelEnum.getEnumType());
                            if (val != BusinessEnum.DefaultValue.NULL) {
                                OldmanAttrBo attrBo = new OldmanAttrBo();
                                attrBo.setIdCard(idCard);
                                attrBo.setType(BusinessEnum.find(Integer.valueOf(oldmanExcelEnum.getFieldName()), OldmanAttrEnum.OldmanAttrType.class));
                                attrBo.setValue(val);
                                attrBo.setExt(ext);
                                oldmanAttrBoList.add(attrBo);
                            }
                        }
                    }
                }


            }
        } catch (Exception e) {
            REFLECTION_ERROR.doThrowException("fail to addOldmanAttr", e);
        }

        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getInMap().put("idCard", oldmanAttrBoList.stream().map(OldmanAttrBo::getIdCard).distinct().collect(Collectors.toList()));
        Map<String, Integer> idMap = oldmanRepository.findWithSpec(jpaSpecBo).stream().collect(Collectors.toMap(OldmanBo::getIdCard, OldmanBo::getId));
        oldmanAttrBoList.forEach(bo -> bo.setOldmanId(idMap.get(bo.getIdCard())));
        oldmanAttrRepository.batchInsert(oldmanAttrBoList);
        return Lists.newArrayList();
    }

    private List<CheckResultBo> addOldmanBaseInfo(List<String> titleList, List<List<String>> valueList) {
        List<OldmanBo> oldmanBoList = Lists.newArrayList();

        IntStream.range(0, valueList.size()).forEach(item -> {
            OldmanBo oldmanBo = new OldmanBo();
            oldmanBoList.add(oldmanBo);
        });

        Map<String, Field> fieldMap = LjReflectionUtils.getFieldToMap(OldmanBo.class);

        try {
            for (int i = 0; i < titleList.size(); i++) {
                ExcelEnum oldmanExcelEnum = ExcelEnum.findFieldName(titleList.get(i), OldmanExcelEnum.class);
                if (oldmanExcelEnum == null) {
                    continue;
                }
                Field field = fieldMap.get(oldmanExcelEnum.getFieldName());

                if (field == null) {
                    for (int j = 0; j < valueList.size(); j++) {
                        Object value = valueList.get(j).get(i);
                        oldmanExcelEnum.handle(oldmanBoList.get(j), value);
                    }
                } else {
                    field.setAccessible(true);
                    //纵向 遍历每个对象，一个属性一个属性 纵向赋值
                    for (int j = 0; j < valueList.size(); j++) {
                        Object value = valueList.get(j).get(i);
                        if (StringUtils.isNotBlank(String.valueOf(value))) {
                            if (IMPORT_RESET.contains(String.valueOf(value))) {
                                if (field.getType() == Integer.class) {
                                    field.set(oldmanBoList.get(j), 0);
                                } else if (field.getType() == String.class) {
                                    field.set(oldmanBoList.get(j), StringUtils.EMPTY);
                                }
                            } else {
                                //转换成枚举值
                                Class<? extends BusinessEnum> enumClass = oldmanExcelEnum.getEnumType();
                                if (enumClass != null) {
                                    boolean find = false;
                                    //需要 枚举转换
                                    for (BusinessEnum businessEnum : enumClass.getEnumConstants()) {
                                        if (businessEnum.getDesc().equals(value)) {
                                            find = true;
                                            value = businessEnum;
                                            break;
                                        }
                                    }
                                    if (find) {
                                        field.set(oldmanBoList.get(j), value);
                                    }
                                } else {
                                    field.set(oldmanBoList.get(j), value);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            REFLECTION_ERROR.doThrowException("fail to addOldmanByExcel", e);
        }

        supplement(oldmanBoList);

        // left 添加 right更新
        Pair<List<OldmanBo>, List<OldmanBo>> pair = classifyDbType(oldmanBoList);
        //todo 并非真正的 batch
        oldmanRepository.batchInsert(pair.getFirst());
        oldmanRepository.batchUpdate(pair.getSecond());
        return Lists.newArrayList();
    }


    /**
     * 补全数据
     */
    private void supplement(List<OldmanBo> oldmanBoList) {
        /**
         * key 坐标 lng+lat
         */
        List<LocationBo> locationBoList = oldmanBoList.stream()
                .filter(bo -> StringUtils.isNotBlank(bo.getGpsDesc()))
                .map(bo -> {
                    LocationBo locationBo = new LocationBo();
                    locationBo.setLng(bo.getLng());
                    locationBo.setLat(bo.getLat());
                    locationBo.setDesc(bo.getGpsDesc());
                    return locationBo;
                }).collect(Collectors.toList());

        Map<String, Integer> locationMap = locationRepository.getBatchByDescOrCreate(locationBoList);

        oldmanBoList.forEach(oldmanBo -> {
            oldmanBo.setBirthday(DateUtils.stringToLocalDate(oldmanBo.getIdCard().substring(6, 14), YYMMDD));
            oldmanBo.setLocationId(locationMap.get(oldmanBo.getLng() + "_" + oldmanBo.getLat()));
            oldmanBo.setIdCard(oldmanBo.getIdCard().toUpperCase());
        });
    }

    /**
     * 区分 哪些老人 添加 哪些老人更新
     *
     * @param oldmanBoList
     * @return
     */
    private Pair<List<OldmanBo>, List<OldmanBo>> classifyDbType(List<OldmanBo> oldmanBoList) {
        List<OldmanBo> addList = Lists.newArrayList();
        List<OldmanBo> updateList = Lists.newArrayList();

        List<List<OldmanBo>> parts = Lists.partition(oldmanBoList, PART_NUM);
        parts.forEach(item -> {
            List<String> idCardList = item.stream().map(OldmanBo::getIdCard).collect(Collectors.toList());
            Map<String, OldmanBo> existOldmanMap = oldmanRepository.getByIdCards(idCardList).stream().collect(Collectors.toMap(a->a.getIdCard().toLowerCase(), Function.identity()));
            item.forEach(oldman -> {
                if (existOldmanMap.containsKey(oldman.getIdCard().toLowerCase())) {
                    oldman.setId(existOldmanMap.get(oldman.getIdCard().toLowerCase()).getId());
                    oldman.setStatus(0);
                    updateList.add(oldman);
                } else {
                    addList.add(oldman);
                }
            });
        });
        return Pair.of(addList, updateList);
    }

    public Map<String, Object> getTypeCount(List<Integer> typeList) {
        Map<String, Object> map = Maps.newHashMap();
        typeList.forEach(type -> {
            JpaSpecBo jpaSpecBo = new JpaSpecBo();
            jpaSpecBo.getEqualMap().put("type", type);
            map.put(BusinessEnum.find(type, OldmanAttrEnum.OldmanAttrType.class).getDesc(), oldmanAttrRepository.typeCount(jpaSpecBo));
        });
        return map;
    }

    public Map<String, Object> getExtGroup(Integer type, Integer value) {
        Map<String, Object> map = Maps.newHashMap();
        map.putAll(oldmanAttrRepository.getExtGroup(type, value));
        return map;
    }

    public Map<String, Object> getOldmanBaseGroupByAttr(List<String> fieldNameList, List<Integer> typeList) {
        Map<String, Object> map = Maps.newHashMap();
        fieldNameList.forEach(item -> {
            if (item.equals("age")){
                Map<String,Object> res = Maps.newHashMap();
                JpaSpecBo jpaSpecBo = new JpaSpecBo();

                jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(60).toLocalDate());
                jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(69).toLocalDate());
                res.put("60-69", oldmanRepository.getOldmanBaseGroupByAttr("male", typeList,jpaSpecBo));

                jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(70).toLocalDate());
                jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(79).toLocalDate());
                res.put("70-79", oldmanRepository.getOldmanBaseGroupByAttr("male", typeList,jpaSpecBo));

                jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(80).toLocalDate());
                jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(89).toLocalDate());
                res.put("80-89", oldmanRepository.getOldmanBaseGroupByAttr("male", typeList,jpaSpecBo));
                map.put("age",res);
            }else {
                Map<String, Long> a = oldmanRepository.getOldmanBaseGroupByAttr(item, typeList,null);
                Map<String, Long> res = Maps.newHashMap();
                a.forEach((k, v) -> {
                    BusinessEnum businessEnum = BusinessEnum.find(Integer.valueOf(k), item);
                    if (businessEnum != null) {
                        res.put(businessEnum.getDesc(), v);
                    } else {
                        res.put(k, v);
                    }
                });
                map.put(item, res);
            }
        });
        return map;
    }

    public Map<String, Object> getTrend(Integer type) {
        Map<String, Object> map  = Maps.newHashMap();
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getEqualMap().put("type",type);
        jpaSpecBo.getGreatEMap().put("time",LocalDate.now().minusMonths(2).format(DateTimeFormatter.ofPattern("yyyy-MM")));
        jpaSpecBo.getLessEMap().put("time",LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")));

        Map<Integer, List<HistoryBo>> boMap = historyRepository.findWithSpec(jpaSpecBo).stream().collect(Collectors.groupingBy(HistoryBo::getValue));

        boMap.forEach((k,v)->{
            List<Long> list = v.stream().sorted(Comparator.comparing(HistoryBo::getTime)).map(HistoryBo::getCount).collect(Collectors.toList());
            list.add(TrendUtils.getTrendData(list.get(list.size()-1)));
            list.add(TrendUtils.getTrendData(list.get(list.size()-1)));
            map.put(BusinessEnum.find(Integer.valueOf(k), String.valueOf(type)).getDesc(),list);
        });
        List<String> xdataList = Lists.newArrayList(LocalDate.now().minusMonths(2).format(DateTimeFormatter.ofPattern("yyyy-MM")),
                LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM")),
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM")),
                LocalDate.now().plusMonths(2).format(DateTimeFormatter.ofPattern("yyyy-MM")));
        map.put("xdata",xdataList);
        return map;
    }
}
