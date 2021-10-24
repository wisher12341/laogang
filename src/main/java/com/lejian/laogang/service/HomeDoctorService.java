package com.lejian.laogang.service;

import com.lejian.laogang.controller.contract.request.HomBedBParam;
import com.lejian.laogang.controller.contract.request.HomeDoctorParam;
import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.pojo.bo.HomBedBo;
import com.lejian.laogang.pojo.bo.HomeDoctorBo;
import com.lejian.laogang.pojo.vo.HomBedBVo;
import com.lejian.laogang.pojo.vo.HomeDoctorVo;
import com.lejian.laogang.repository.HomeBedRepository;
import com.lejian.laogang.repository.HomeDoctorRepository;
import com.lejian.laogang.repository.entity.HomeBedEntity;
import com.lejian.laogang.repository.entity.HomeDoctorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeDoctorService {

    @Autowired
    private HomeDoctorRepository homeDoctorRepository;



    public List<HomeDoctorVo> getByPage(PageParam pageParam, HomeDoctorParam param) {
        List<HomeDoctorBo> boList = homeDoctorRepository.findByPageWithSpec(pageParam.getPageNo(),pageParam.getPageSize(),param.convert());
        return boList.stream().map(HomeDoctorBo::convertVo).collect(Collectors.toList());
    }

    public Long count(HomeDoctorParam param) {
        return homeDoctorRepository.countWithSpec(param.convert());
    }

    public void add(HomeDoctorParam request) {
        HomeDoctorEntity entity = request.convertEntity();
        homeDoctorRepository.save(entity);
    }
}
