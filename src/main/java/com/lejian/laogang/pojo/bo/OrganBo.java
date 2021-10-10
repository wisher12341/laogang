package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.pojo.vo.OrganVo;
import com.lejian.laogang.repository.entity.OrganEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class OrganBo extends BaseBo {
    private String name;
    private String desc;
    private String service;
    private String address;
    private String web;
    private String email;
    private String phone;
    private String workerTime;
    private String ssqk;
    private String rzyq;
    private Integer bedNumber;
    private Integer type;
    private String gps;
    private Integer id;

    @Override
    public OrganEntity convert() {
        OrganEntity organEntity = new OrganEntity();
        BeanUtils.copyProperties(this,organEntity);
        return organEntity;
    }

    public static OrganBo convert(OrganEntity organEntity) {
        OrganBo organBo = new OrganBo();
        BeanUtils.copyProperties(organEntity,organBo);
        return organBo;
    }

    public OrganVo convertVo(){
        OrganVo vo = new OrganVo();
        BeanUtils.copyProperties(this,vo);
        if (this.gps.contains(",")) {
            String[] arr = this.gps.split(",");
            vo.setLng(arr[0]);
            vo.setLat(arr[1]);
        }
        return vo;
    }
}
