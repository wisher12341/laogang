package com.lejian.laogang.service;

import com.google.common.collect.Maps;
import com.lejian.laogang.repository.HomeBedRepository;
import com.lejian.laogang.repository.HomeDoctorRepository;
import com.lejian.laogang.repository.IntelligentDeviceRepository;
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

    public Map<String, Object> geCount() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("家庭医生",homeDoctorRepository.count());
        map.put("家庭病床",homeBedRepository.count());
        map.put("智能设备",intelligentDeviceRepository.count());
        return map;
    }
}
