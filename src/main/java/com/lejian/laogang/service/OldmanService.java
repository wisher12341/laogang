package com.lejian.laogang.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lejian.laogang.controller.contract.request.OldmanParam;
import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.label.LabelEnum;
import com.lejian.laogang.pojo.bo.LocationBo;
import com.lejian.laogang.pojo.bo.OldmanBo;
import com.lejian.laogang.pojo.vo.LocationVo;
import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.repository.LocationRepository;
import com.lejian.laogang.repository.OldmanAttrRepository;
import com.lejian.laogang.repository.OldmanRepository;
import com.lejian.laogang.repository.WhereBo;
import com.lejian.laogang.repository.entity.OldmanEntity;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OldmanService {

    @Autowired
    private OldmanRepository oldmanRepository;
    @Autowired
    private OldmanAttrRepository oldmanAttrRepository;
    @Autowired
    private LocationRepository locationRepository;


    public Map<String, Object> getGroupCount(List<String> fieldNameList, List<String> labelIdList) {
        WhereBo whereBo = LabelEnum.generateWhere(labelIdList);
        Map<String, Object> map = Maps.newHashMap();
        fieldNameList.forEach(fieldName -> {
            Map<String, Long> result;
            if (OldmanEntity.haveField(fieldName)) {
                result = oldmanRepository.getGroupCount(fieldName, whereBo);
            } else {
                result = oldmanAttrRepository.getGroupCount(fieldName, whereBo);
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
        List<OldmanBo> oldmanBoList = oldmanRepository.findByPageWithSpec(pageParam.getPageNo(), pageParam.getPageSize(), oldmanParam.convert());
        return oldmanBoList.stream().map(OldmanBo::convertVo).collect(Collectors.toList());
    }

    public Map<String, Object> getAgeGroupCount(List<String> labelIdList) {
        //todo
        Map<String, Object> map =Maps.newHashMap();
        Map<String,Long> male = Maps.newHashMap();
        male.put("男",10L);
        male.put("女",5L);
        map.put("60-69",male);
        map.put("70-79",male);
        map.put("80-89",male);
        return map;
    }

    public List<LocationVo> getAllLocation(OldmanParam oldmanParam) {
        Map<String,Long> locationMap= oldmanRepository.getGroupCount("location_id",oldmanParam.getWhereBo());
        List<LocationBo> locationBoList = locationRepository.getByPkIds(locationMap.keySet().stream().map(Integer::valueOf).collect(Collectors.toList()));
        return locationBoList.stream().map(item->item.convertVo(locationMap)).collect(Collectors.toList());
    }

    public Map<String, Object> getCount(List<OldmanParam> request) {
        Map<String, Object> map = Maps.newHashMap();
        for (int i=1;i<=request.size();i++){
            map.put(i+"",oldmanRepository.countWithSpec(request.get(i-1).convert()));
        }
        return map;
    }
}
