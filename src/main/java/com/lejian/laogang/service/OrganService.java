package com.lejian.laogang.service;

import com.lejian.laogang.controller.contract.request.OldmanParam;
import com.lejian.laogang.controller.contract.request.OrganOldmanParam;
import com.lejian.laogang.controller.contract.request.OrganParam;
import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.OrganBo;
import com.lejian.laogang.pojo.bo.UserBo;
import com.lejian.laogang.pojo.vo.OrganOldmanVo;
import com.lejian.laogang.pojo.vo.OrganVo;
import com.lejian.laogang.repository.OrganRepository;
import com.lejian.laogang.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganService {

    @Autowired
    private OrganRepository organRepository;

    public List<OrganVo> getByPage(OrganParam organParam, PageParam pageParam) {
        return organRepository.findByPageWithSpec(pageParam.getPageNo(),pageParam.getPageSize(),organParam.convert()).stream().map(OrganBo::convertVo).collect(Collectors.toList());
    }

    public OrganVo getBYId(Integer id) {
        return organRepository.getByPkId(id).convertVo();
    }

    public OrganVo getByUser() {
        UserBo userBo = UserUtils.getUser();
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getEqualMap().put("userId",userBo.getId());
        return organRepository.findWithSpec(jpaSpecBo).stream().map(OrganBo::convertVo).findFirst().orElse(null);
    }

    @Transactional
    public void edit(OrganParam request) {
        organRepository.dynamicUpdateByPkId(request.convertEntity());
    }

}
