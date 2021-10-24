package com.lejian.laogang.controller.contract.request;

import lombok.Data;

@Data
public class GetHomeDoctorRequest {
    private PageParam pageParam;
    private HomeDoctorParam param;
}
