package com.lejian.laogang.service;

import com.lejian.laogang.controller.contract.request.*;
import com.lejian.laogang.pojo.bo.*;
import com.lejian.laogang.pojo.vo.LinkManVo;
import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.pojo.vo.PolicyVo;
import com.lejian.laogang.repository.*;
import com.lejian.laogang.repository.entity.LinkManEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinkManService {

    @Autowired
    private LinkManRepository linkManRepository;



    public List<LinkManVo> getByPage(PageParam pageParam, LinkManParam linkManParam) {
        List<LinkManBo> boList = linkManRepository.findByPageWithSpec(pageParam.getPageNo(),pageParam.getPageSize(),linkManParam.convert());
        return boList.stream().map(LinkManBo::convertVo).collect(Collectors.toList());
    }

    public Long count(LinkManParam linkManParam) {
        return linkManRepository.countWithSpec(linkManParam.convert());
    }

    public void add(AddLinkManParam request) {
        LinkManEntity entity = request.convert();
        linkManRepository.save(entity);
    }
}
