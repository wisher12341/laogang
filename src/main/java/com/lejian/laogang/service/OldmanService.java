package com.lejian.laogang.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lejian.laogang.check.bo.CheckResultBo;
import com.lejian.laogang.controller.contract.request.EditOldmanParam;
import com.lejian.laogang.controller.contract.request.OldmanParam;
import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.enums.*;
import com.lejian.laogang.pojo.bo.*;
import com.lejian.laogang.pojo.vo.IntelligentDeviceVo;
import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.repository.*;
import com.lejian.laogang.repository.entity.*;
import com.lejian.laogang.util.DateUtils;
import com.lejian.laogang.util.LjReflectionUtils;
import com.lejian.laogang.util.StringUtils;
import com.lejian.laogang.util.UserUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.lejian.laogang.common.ComponentRespCode.REFLECTION_ERROR;
import static com.lejian.laogang.common.Constant.IMPORT_RESET;
import static com.lejian.laogang.enums.BusinessEnum.DefaultValue.NULL;
import static com.lejian.laogang.util.DateUtils.YYMMDD;
import static com.lejian.laogang.util.DateUtils.YY_MM_DD;

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
    private IntelligentDeviceRepository intelligentDeviceRepository;
    @Autowired
    private HomeBedRepository homeBedRepository;
    @Autowired
    private HomeDoctorRepository homeDoctorRepository;

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
                            if (val != NULL) {
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
        clearOldmanAttr(oldmanAttrBoList.stream().map(OldmanAttrBo::getOldmanId).filter(Objects::nonNull).distinct().collect(Collectors.toList()));
        oldmanAttrRepository.batchInsert(oldmanAttrBoList);
        return Lists.newArrayList();
    }

    private void clearOldmanAttr(List<Integer> oldmanIdList) {
        List<List<Integer>> group = Lists.partition(oldmanIdList, 150);
        group.forEach(list -> oldmanAttrRepository.deleteByOldmanId(list));
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
            Map<String, OldmanBo> existOldmanMap = oldmanRepository.getByIdCards(idCardList).stream().collect(Collectors.toMap(a -> a.getIdCard().toLowerCase(), Function.identity()));
            item.forEach(oldman -> {
                if (existOldmanMap.containsKey(oldman.getIdCard().toLowerCase())) {
                    oldman.setId(existOldmanMap.get(oldman.getIdCard().toLowerCase()).getId());
                    oldman.setStatus(0);
                    oldman.setDatachangeTime(new Timestamp(System.currentTimeMillis()));
                    updateList.add(oldman);
                } else {
                    addList.add(oldman);
                }
            });
        });
        return Pair.of(addList, updateList);
    }


    public List<OldmanVo> getByPage(OldmanParam oldmanParam, PageParam pageParam) {
        List<OldmanBo> oldmanBoList = oldmanRepository.findByPage(pageParam.getPageNo(), pageParam.getPageSize(), oldmanParam.getSql());
        List<OldmanVo> voList = oldmanBoList.stream().map(OldmanBo::convertVo).collect(Collectors.toList());
        return voList;
    }

    public Long getOldmanCount(OldmanParam oldmanParam) {
        return oldmanRepository.count(oldmanParam.getSql());
    }

    public OldmanVo getBYId(Integer id) {
        OldmanVo oldmanVo = oldmanRepository.getByPkId(id).convertVo();
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getEqualMap().put("oldmanId", oldmanVo.getId());
        List<OldmanAttrBo> oldmanAttrBoList = oldmanAttrRepository.findWithSpec(jpaSpecBo);
        Map<String, String> typeMap = Maps.newHashMap();
        oldmanAttrBoList.forEach(item -> {
            if (typeMap.containsKey(item.getType().getValue().toString())) {
                typeMap.put(item.getType().getValue().toString(), typeMap.get(item.getType().getValue().toString()) + "," + item.getValue().getDesc() + (StringUtils.isNotBlank(item.getExt()) ? "_" + item.getExt() : ""));
            } else {
                typeMap.put(item.getType().getValue().toString(), item.getValue().getDesc() + (StringUtils.isNotBlank(item.getExt()) ? "_" + item.getExt() : ""));
            }
        });
        oldmanVo.setTypeMap(typeMap);

        UserBo userBo = UserUtils.getUser();
        if (Lists.newArrayList(1,2).contains(userBo.getRole())){
            IntelligentDeviceBo bo = intelligentDeviceRepository.findByOldmanId(oldmanVo.getId());
            if(bo!=null) {
                oldmanVo.setIdeviceId(bo.getId());
                oldmanVo.setIdeviceName(bo.getName());
                if (bo.getStartTime()!=null) {
                    oldmanVo.setIdeviceStartTime(bo.getStartTime().format(YY_MM_DD));
                }
                if (bo.getEndTime()!=null) {
                    oldmanVo.setIdeviceEndTime(bo.getEndTime().format(YY_MM_DD));
                }
            }
        }
        if (Lists.newArrayList(1,2,5).contains(userBo.getRole())){
            HomBedBo bo = homeBedRepository.findByOldmanId(oldmanVo.getId());
            if(bo!=null) {
                oldmanVo.setHomeBedId(bo.getId());
                oldmanVo.setHomeBedOrgan(bo.getOrgan());
                if (bo.getTime()!=null) {
                    oldmanVo.setHomeBedTime(bo.getTime().format(YY_MM_DD));
                }
            }
            HomeDoctorBo homeDoctorBo = homeDoctorRepository.findByOldmanId(oldmanVo.getId());
            if(homeDoctorBo!=null) {
                oldmanVo.setHomeDoctorId(homeDoctorBo.getId());
                oldmanVo.setHomeDoctorName(homeDoctorBo.getName());
                oldmanVo.setHomeDoctorOrgan(homeDoctorBo.getOrgan());
                if (homeDoctorBo.getTime()!=null) {
                    oldmanVo.setHomeDoctorTime(homeDoctorBo.getTime().format(YY_MM_DD));
                }
            }
        }
        return oldmanVo;
    }

    @Transactional
    public void editOrAdd(EditOldmanParam request) {
        OldmanEntity oldmanEntity = request.convertEntity();
        List<OldmanAttrEntity> oldmanAttrEntityList = request.convertAttrEntity();
        IntelligentDeviceEntity intelligentDeviceEntity = request.convertId();
        HomeBedEntity homeBedEntity = request.convertHomeBed();
        HomeDoctorEntity homeDoctorEntity = request.convertHomeDoctor();
        List<Integer> allType = oldmanAttrEntityList.stream().map(OldmanAttrEntity::getType).collect(Collectors.toList());
        oldmanAttrEntityList = oldmanAttrEntityList.stream().filter(item -> item.getValue() != null).collect(Collectors.toList());
        if (oldmanEntity.getId() != null) {
            oldmanRepository.dynamicUpdateByPkId(oldmanEntity);
            oldmanAttrRepository.deleteByType(oldmanEntity.getId(), allType);
        } else {
            oldmanEntity = oldmanRepository.saveAndReturn(oldmanEntity);
            for (OldmanAttrEntity item : oldmanAttrEntityList) {
                item.setOldmanId(oldmanEntity.getId());
                item.setIdCard(oldmanEntity.getIdCard());
            }
        }

        if (!CollectionUtils.isEmpty(oldmanAttrEntityList)) {
            oldmanAttrRepository.batchInsertEntity(oldmanAttrEntityList);
        }
        if (intelligentDeviceEntity!=null) {
            if (intelligentDeviceEntity.getId() != null) {
                intelligentDeviceRepository.dynamicUpdateByPkId(intelligentDeviceEntity);
            }else{
                intelligentDeviceRepository.save(intelligentDeviceEntity);
            }
        }else if(request.getIdeviceId()!=null){
            intelligentDeviceRepository.deleteById(request.getIdeviceId());
        }
        if (homeBedEntity!=null) {
            if (homeBedEntity.getId() != null) {
                homeBedRepository.dynamicUpdateByPkId(homeBedEntity);
            }else{
                homeBedRepository.save(homeBedEntity);
            }
        }else if(request.getHomeBedId()!=null){
            homeBedRepository.deleteById(request.getHomeBedId());
        }
        if(homeDoctorEntity!=null) {
            if (homeDoctorEntity.getId() != null) {
                homeDoctorRepository.dynamicUpdateByPkId(homeDoctorEntity);
            }else{
                homeDoctorRepository.save(homeDoctorEntity);
            }
        }else if(request.getHomeDoctorId()!=null){
            homeDoctorRepository.deleteById(request.getHomeDoctorId());
        }

    }

    @Transactional
    public List<CheckResultBo> addIncome(Pair<List<String>, List<List<String>>> excelData) {
        List<OldmanAttrBo> oldmanAttrBoList = Lists.newArrayList();
        for (int i = 0; i < excelData.getSecond().size(); i++) {
            OldmanAttrBo oldmanAttrBo = new OldmanAttrBo();
            oldmanAttrBo.setIdCard(excelData.getSecond().get(i).get(1));
            oldmanAttrBo.setType(OldmanAttrEnum.OldmanAttrType.A4);
            if(StringUtils.isNotBlank(excelData.getSecond().get(i).get(3))){
                oldmanAttrBo.setValue(OldmanAttrEnum.Income.C);
            }
            if(StringUtils.isNotBlank(excelData.getSecond().get(i).get(4))){
                oldmanAttrBo.setValue(OldmanAttrEnum.Income.G);
            }
            if(StringUtils.isNotBlank(excelData.getSecond().get(i).get(5))){
                oldmanAttrBo.setValue(OldmanAttrEnum.Income.H);
            }
            if(StringUtils.isNotBlank(excelData.getSecond().get(i).get(6))){
                oldmanAttrBo.setValue(OldmanAttrEnum.Income.B);
            }
            oldmanAttrBoList.add(oldmanAttrBo);
        }
        List<String> idCardList = oldmanAttrBoList.stream().map(OldmanAttrBo::getIdCard).collect(Collectors.toList());
        Map<String,OldmanBo> map = oldmanRepository.getByIdCards(idCardList).stream().collect(Collectors.toMap(OldmanBo::getIdCard,Function.identity()));

        oldmanAttrBoList.forEach(item->{
            if (map.containsKey(item.getIdCard())) {
                item.setOldmanId(map.get(item.getIdCard()).getId());
                //todo优化 改成批量删除
                oldmanAttrRepository.deleteByType(item.getOldmanId(), Lists.newArrayList(item.getType().getValue()));
            }
        });
        oldmanAttrRepository.batchInsert(oldmanAttrBoList.stream().filter(item->item.getOldmanId()!=null).collect(Collectors.toList()));
        return Lists.newArrayList();
    }

    @Transactional
    public List<CheckResultBo> addHealth(Pair<List<String>, List<List<String>>> excelData) {
        List<OldmanAttrBo> oldmanAttrBoList = Lists.newArrayList();
        for (int i = 0; i < excelData.getSecond().size(); i++) {
            OldmanAttrBo oldmanAttrBo = new OldmanAttrBo();
            oldmanAttrBo.setIdCard(excelData.getSecond().get(i).get(1));
            oldmanAttrBo.setType(OldmanAttrEnum.OldmanAttrType.A11);
            if(StringUtils.isNotBlank(excelData.getSecond().get(i).get(2))){
                oldmanAttrBo.setValue(OldmanAttrEnum.ZL.A);
                oldmanAttrBo.setExt(excelData.getSecond().get(i).get(2));
            }
            if(StringUtils.isNotBlank(excelData.getSecond().get(i).get(4))){
                oldmanAttrBo.setValue(OldmanAttrEnum.ZL.B);
                oldmanAttrBo.setExt(excelData.getSecond().get(i).get(4));
            }
            oldmanAttrBoList.add(oldmanAttrBo);
        }
        List<String> idCardList = oldmanAttrBoList.stream().map(OldmanAttrBo::getIdCard).collect(Collectors.toList());
        Map<String,OldmanBo> map = oldmanRepository.getByIdCards(idCardList).stream().collect(Collectors.toMap(OldmanBo::getIdCard,Function.identity()));

        oldmanAttrBoList.forEach(item->{
            if (map.containsKey(item.getIdCard())) {
                item.setOldmanId(map.get(item.getIdCard()).getId());
                //todo优化 改成批量删除
                oldmanAttrRepository.deleteByType(item.getOldmanId(), Lists.newArrayList(item.getType().getValue()));
            }
        });
        oldmanAttrRepository.batchInsert(oldmanAttrBoList.stream().filter(item->item.getOldmanId()!=null).collect(Collectors.toList()));
        return Lists.newArrayList();
    }
}
