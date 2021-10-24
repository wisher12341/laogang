package com.lejian.laogang.service;

import com.lejian.laogang.controller.contract.request.IntelligentDeviceParam;
import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.pojo.bo.IntelligentDeviceBo;
import com.lejian.laogang.pojo.vo.IntelligentDeviceVo;
import com.lejian.laogang.repository.IntelligentDeviceRepository;
import com.lejian.laogang.repository.entity.IntelligentDeviceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntelligentDeviceService {

    @Autowired
    private IntelligentDeviceRepository intelligentDeviceRepository;



    public List<IntelligentDeviceVo> getByPage(PageParam pageParam, IntelligentDeviceParam param) {
        List<IntelligentDeviceBo> boList = intelligentDeviceRepository.findByPageWithSpec(pageParam.getPageNo(),pageParam.getPageSize(),param.convert());
        return boList.stream().map(IntelligentDeviceBo::convertVo).collect(Collectors.toList());
    }

    public Long count(IntelligentDeviceParam param) {
        return intelligentDeviceRepository.countWithSpec(param.convert());
    }

    public void add(IntelligentDeviceParam request) {
        IntelligentDeviceEntity entity = request.convertEntity();
        intelligentDeviceRepository.save(entity);
    }
}
