package com.lejian.laogang.service;

import com.lejian.laogang.controller.contract.request.OldmanParam;
import com.lejian.laogang.controller.contract.request.OrganParam;
import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.pojo.bo.OrganBo;
import com.lejian.laogang.pojo.vo.OrganVo;
import com.lejian.laogang.repository.OrganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganService {

    @Autowired
    private OrganRepository organRepository;

    public List<OrganVo> getByPage(OrganParam organParam, PageParam pageParam) {
        return organRepository.findByPageWithSpec(pageParam.getPageNo(),pageParam.getPageSize(),organParam.convert()).stream().map(OrganBo::convertVo).collect(Collectors.toList());
    }
}
