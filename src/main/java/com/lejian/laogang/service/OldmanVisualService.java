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
import com.lejian.laogang.util.StringUtils;
import com.lejian.laogang.util.TrendUtils;
import org.apache.commons.collections4.CollectionUtils;
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
import static com.lejian.laogang.enums.BusinessEnum.DefaultValue.NULL;
import static com.lejian.laogang.util.DateUtils.YYMMDD;

@Service
public class OldmanVisualService {


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


    public Map<String, Object> getGroupCount(List<String> fieldNameList, OldmanParam oldmanParam) {
        Map<String, Object> map = Maps.newHashMap();
        fieldNameList.forEach(fieldName -> {
            JpaSpecBo jpaSpecBo;
            if (oldmanParam==null){
                jpaSpecBo = new JpaSpecBo();
            }else{
                jpaSpecBo = oldmanParam.convert();
            }
            Map<String, Long> result;
            if (OldmanEntity.haveField(fieldName)) {
                result = oldmanViewRepository.getGroupCount(fieldName, jpaSpecBo);
            } else {
                String oldmanWhere = jpaSpecBo.getSql("o.");
                Map<String, String> attrWhere = OldmanAttrEnum.generateAttrWhere(fieldName);
                if (StringUtils.isBlank(oldmanWhere)) {
                    //不支持可视化标签筛选 labelIdList
                    jpaSpecBo.getEqualMap().putAll(attrWhere);
                    //不用对老人去重
                    result = oldmanAttrRepository.getGroupCount("value", jpaSpecBo);
                }else{
                    //有oldman表筛选属性
                    result = oldmanAttrRepository.getGroupCountWithOldman(oldmanWhere,attrWhere.get("type"));
                }
            }
            Map<String, Long> voResult = Maps.newHashMap();
            result.forEach((k, v) -> {
                if (NumberUtils.isNumber(k)) {
                    BusinessEnum businessEnum = BusinessEnum.find(Integer.valueOf(k), fieldName);
                    if (businessEnum != null) {
                        if (businessEnum != NULL) {
                            voResult.put(businessEnum.getDesc(), v);
                        }
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
            oldmanBoList = oldmanViewRepository.findByPageWithSpec(pageParam.getPageNo(), pageParam.getPageSize(), jpaSpecBo);
        } else {
            oldmanBoList = oldmanRepository.findByPageWithSpec(pageParam.getPageNo(), pageParam.getPageSize(), oldmanParam.convert());

        }
        //数字6和4代表前后几位数字不被替换
        String regex = "(\\w{6})(\\w+)(\\w{4})";
        List<OldmanVo> voList = oldmanBoList.stream().map(OldmanBo::convertVo).collect(Collectors.toList());
        if (oldmanParam.getIsView()) {
            voList.forEach(vo -> {
                vo.setName(StringUtils.nameMask(vo.getName()));
                vo.setIdCard(vo.getIdCard().replaceAll(regex, "$1****$3"));
            });
        }
        return voList;
    }

    public Long getOldmanCount(OldmanParam oldmanParam) {
        return oldmanViewRepository.countWithSpec(oldmanParam.convert());
    }

    public Map<String, Object> getAgeGroupCount(OldmanParam oldmanParam) {
        Map<String, Object> map = Maps.newHashMap();
        JpaSpecBo jpaSpecBo = oldmanParam.convert();
        if (oldmanParam.getLabelIdList().contains("1")
                ||(!oldmanParam.getLabelIdList().contains("5") && !oldmanParam.getLabelIdList().contains("19"))) {
            jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(60).toLocalDate());
            jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(69).toLocalDate());

            Map<String, Long> result = oldmanViewRepository.getGroupCount("male", jpaSpecBo);

            Map<String, Long> voResult = Maps.newHashMap();
            result.forEach((k, v) -> {
                BusinessEnum businessEnum = BusinessEnum.find(Integer.valueOf(k), OldmanEnum.Male.class);
                if (BusinessEnum.DefaultValue.NULL != businessEnum) {
                    voResult.put(businessEnum.getDesc(), v);
                }
            });
            map.put("60-69", voResult);
        }

        if (oldmanParam.getLabelIdList().contains("5")
                ||(!oldmanParam.getLabelIdList().contains("1") && !oldmanParam.getLabelIdList().contains("19"))) {
            jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(70).toLocalDate());
            jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(79).toLocalDate());


            Map<String, Long> result2 = oldmanViewRepository.getGroupCount("male", jpaSpecBo);
            Map<String, Long> voResult2 = Maps.newHashMap();
            result2.forEach((k, v) -> {
                BusinessEnum businessEnum = BusinessEnum.find(Integer.valueOf(k), OldmanEnum.Male.class);
                if (BusinessEnum.DefaultValue.NULL != businessEnum) {
                    voResult2.put(businessEnum.getDesc(), v);
                }
            });
            map.put("70-79", voResult2);
        }
        if (oldmanParam.getLabelIdList().contains("19")
                ||(!oldmanParam.getLabelIdList().contains("5") && !oldmanParam.getLabelIdList().contains("1"))) {
            jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(80).toLocalDate());
            jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(89).toLocalDate());
            Map<String, Long> result3 = oldmanViewRepository.getGroupCount("male", jpaSpecBo);
            Map<String, Long> voResult3 = Maps.newHashMap();
            result3.forEach((k, v) -> {
                BusinessEnum businessEnum = BusinessEnum.find(Integer.valueOf(k), OldmanEnum.Male.class);
                if (BusinessEnum.DefaultValue.NULL != businessEnum) {
                    voResult3.put(businessEnum.getDesc(), v);
                }
            });
            map.put("80-89", voResult3);
        }
        return map;
    }

    public List<LocationVo> getLocation(OldmanParam oldmanParam) {
        if (oldmanParam.getIsView()) {
            List<LocationVo> locationVoList = Lists.newArrayList();
            Map<String, Long> locationMap = oldmanViewRepository.getGroupCount("location", oldmanParam.convert());
            locationMap.forEach((k, v) -> {
                String[] arr = k.split("_");
                if (arr.length == 2) {
                    LocationVo vo = new LocationVo();
                    vo.setCount(v + "");
                    vo.setLng(arr[0]);
                    vo.setLat(arr[1]);
                    locationVoList.add(vo);
                }
            });
            return locationVoList;
        } else {
            Map<String, Long> locationMap = oldmanRepository.getGroupCount("location_id", oldmanParam.convert());
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


    public Map<String, Object> getTypeCount(List<Integer> typeList,OldmanParam oldmanParam) {
        Map<String, Object> map = Maps.newHashMap();
        typeList.forEach(type -> {
            JpaSpecBo jpaSpecBo = new JpaSpecBo();
            jpaSpecBo.getEqualMap().put("type", type);
            map.put(BusinessEnum.find(type, OldmanAttrEnum.OldmanAttrType.class).getDesc(), oldmanAttrRepository.typeCount(jpaSpecBo,oldmanParam));
        });
        return map;
    }

    public Map<String, Object> getExtGroup(Integer type, Integer value, OldmanParam oldmanParam) {
        String where = oldmanParam.convert().getSql("o.");
        Map<String, Object> map = Maps.newHashMap();
        map.putAll(oldmanAttrRepository.getExtGroup(type, value, where));
        return map;
    }

    public Map<String, Object> getOldmanBaseGroupByAttr(List<String> fieldNameList, List<Integer> typeList,OldmanParam oldmanParam) {
        Map<String, Object> map = Maps.newHashMap();
        fieldNameList.forEach(item -> {
            if (item.equals("age")) {
                Map<String, Object> res = Maps.newHashMap();
                JpaSpecBo jpaSpecBo = oldmanParam.convert();
                jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(60).toLocalDate());
                jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(69).toLocalDate());
                Map<String,Long> result1 = oldmanRepository.getOldmanBaseGroupByAttr("male", typeList, jpaSpecBo);
                Map<String, Long> voResult1 = Maps.newHashMap();
                result1.forEach((k, v) -> {
                    BusinessEnum businessEnum = BusinessEnum.find(Integer.valueOf(k), OldmanEnum.Male.class);
                    if (BusinessEnum.DefaultValue.NULL!=businessEnum){
                        voResult1.put(businessEnum.getDesc(),v);
                    }
                });
                res.put("60-69", voResult1);

                jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(70).toLocalDate());
                jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(79).toLocalDate());
                Map<String,Long> result2 = oldmanRepository.getOldmanBaseGroupByAttr("male", typeList, jpaSpecBo);
                Map<String, Long> voResult2 = Maps.newHashMap();
                result2.forEach((k, v) -> {
                    BusinessEnum businessEnum = BusinessEnum.find(Integer.valueOf(k), OldmanEnum.Male.class);
                    if (BusinessEnum.DefaultValue.NULL!=businessEnum){
                        voResult2.put(businessEnum.getDesc(),v);
                    }
                });
                res.put("70-79", voResult2);
                jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(80).toLocalDate());
                jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(89).toLocalDate());
                Map<String,Long> result3 = oldmanRepository.getOldmanBaseGroupByAttr("male", typeList, jpaSpecBo);
                Map<String, Long> voResult3 = Maps.newHashMap();
                result3.forEach((k, v) -> {
                    BusinessEnum businessEnum = BusinessEnum.find(Integer.valueOf(k), OldmanEnum.Male.class);
                    if (BusinessEnum.DefaultValue.NULL!=businessEnum){
                        voResult3.put(businessEnum.getDesc(),v);
                    }
                });
                res.put("80-89", voResult3);
                map.put("age", res);
            } else {
                JpaSpecBo jpaSpecBo = oldmanParam.convert();
                Map<String, Long> a = oldmanRepository.getOldmanBaseGroupByAttr(item, typeList, jpaSpecBo);
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

    public Map<String, Object> getTrend(List<Integer> types) {
        Map<String, Object> result = Maps.newHashMap();
        types.forEach(type -> {
            Map<String, Object> map = Maps.newHashMap();
            JpaSpecBo jpaSpecBo = new JpaSpecBo();
            jpaSpecBo.getEqualMap().put("type", type);
            jpaSpecBo.getGreatEMap().put("time", LocalDate.now().minusMonths(2).format(DateTimeFormatter.ofPattern("yyyy-MM")));
            jpaSpecBo.getLessEMap().put("time", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")));

            Map<Integer, List<HistoryBo>> boMap = historyRepository.findWithSpec(jpaSpecBo).stream().collect(Collectors.groupingBy(HistoryBo::getValue));

            boMap.forEach((k, v) -> {
                List<Long> list = v.stream().sorted(Comparator.comparing(HistoryBo::getTime)).map(HistoryBo::getCount).collect(Collectors.toList());
                list.add(TrendUtils.getTrendData(list.get(list.size() - 1)));
                list.add(TrendUtils.getTrendData(list.get(list.size() - 1)));
                map.put(BusinessEnum.find(Integer.valueOf(k), String.valueOf(type)).getDesc(), list);
            });
            List<String> xdataList = Lists.newArrayList(LocalDate.now().minusMonths(2).format(DateTimeFormatter.ofPattern("yyyy-MM")),
                    LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM")),
                    LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                    LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM")),
                    LocalDate.now().plusMonths(2).format(DateTimeFormatter.ofPattern("yyyy-MM")));
            map.put("xdata", xdataList);
            result.put(type + "", map);
        });
        return result;
    }

}
