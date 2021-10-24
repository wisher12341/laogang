package com.lejian.laogang.service;

import com.lejian.laogang.controller.contract.request.HomBedBParam;
import com.lejian.laogang.controller.contract.request.IntelligentDeviceParam;
import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.pojo.bo.HomBedBo;
import com.lejian.laogang.pojo.bo.IntelligentDeviceBo;
import com.lejian.laogang.pojo.vo.HomBedBVo;
import com.lejian.laogang.pojo.vo.IntelligentDeviceVo;
import com.lejian.laogang.repository.HomeBedRepository;
import com.lejian.laogang.repository.IntelligentDeviceRepository;
import com.lejian.laogang.repository.entity.HomeBedEntity;
import com.lejian.laogang.repository.entity.IntelligentDeviceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeBedService {

    @Autowired
    private HomeBedRepository homeBedRepository;



    public List<HomBedBVo> getByPage(PageParam pageParam, HomBedBParam param) {
        List<HomBedBo> boList = homeBedRepository.findByPageWithSpec(pageParam.getPageNo(),pageParam.getPageSize(),param.convert());
        return boList.stream().map(HomBedBo::convertVo).collect(Collectors.toList());
    }

    public Long count(HomBedBParam param) {
        return homeBedRepository.countWithSpec(param.convert());
    }

    public void add(HomBedBParam request) {
        HomeBedEntity entity = request.convertEntity();
        homeBedRepository.save(entity);
    }
}
