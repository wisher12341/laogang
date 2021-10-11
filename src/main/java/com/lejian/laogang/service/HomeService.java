package com.lejian.laogang.service;

import com.google.common.collect.Maps;
import com.lejian.laogang.controller.contract.request.OldmanParam;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.repository.HomeBedRepository;
import com.lejian.laogang.repository.HomeDoctorRepository;
import com.lejian.laogang.repository.IntelligentDeviceRepository;
import com.lejian.laogang.repository.OldmanAttrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HomeService {

    @Autowired
    private HomeBedRepository homeBedRepository;
    @Autowired
    private HomeDoctorRepository homeDoctorRepository;
    @Autowired
    private IntelligentDeviceRepository intelligentDeviceRepository;
    @Autowired
    private OldmanAttrRepository oldmanAttrRepository;

    public Map<String, Object> geCount(OldmanParam oldmanParam) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("家庭医生",homeDoctorRepository.count());
        map.put("家庭病床",homeBedRepository.count());
        map.put("智能设备",intelligentDeviceRepository.count());
        Map<String,Long> a = oldmanAttrRepository.getGroupCountWithOldman(oldmanParam.convert().getSql(),"14");
        map.put("长护险",a.get("1"));
        map.put("居家养老(民政局)",a.get("2"));
        map.put("居家养老(镇级)",a.get("3"));
        return map;
    }
}
