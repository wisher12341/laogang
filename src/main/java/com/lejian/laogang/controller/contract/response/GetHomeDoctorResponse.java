package com.lejian.laogang.controller.contract.response;

import com.lejian.laogang.pojo.vo.HomBedBVo;
import com.lejian.laogang.pojo.vo.HomeDoctorVo;
import lombok.Data;

import java.util.List;

@Data
public class GetHomeDoctorResponse {
    private List<HomeDoctorVo> voList;
    private Long count;
}
