package com.lejian.laogang.service;

import com.google.common.collect.Lists;
import com.lejian.laogang.controller.contract.request.OrganOldmanParam;
import com.lejian.laogang.controller.contract.request.OrganParam;
import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.pojo.bo.*;
import com.lejian.laogang.pojo.vo.OrganOldmanVo;
import com.lejian.laogang.pojo.vo.OrganVo;
import com.lejian.laogang.repository.OldmanAttrRepository;
import com.lejian.laogang.repository.OldmanRepository;
import com.lejian.laogang.repository.OrganOldmanRepository;
import com.lejian.laogang.repository.OrganRepository;
import com.lejian.laogang.repository.entity.OldmanAttrEntity;
import com.lejian.laogang.repository.entity.OrganOldmanEntity;
import com.lejian.laogang.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganOldmanService {

    @Autowired
    private OrganOldmanRepository organOldmanRepository;
    @Autowired
    private OrganRepository organRepository;
    @Autowired
    private OldmanAttrRepository oldmanAttrRepository;
    @Autowired
    private OldmanRepository oldmanRepository;

    public List<OrganOldmanVo> getByPage(OrganParam organParam, PageParam pageParam) {
        return organOldmanRepository.findByPageWithSpec(pageParam.getPageNo(), pageParam.getPageSize(), organParam.convert()).stream().map(OrganOldmanBo::convertVo).collect(Collectors.toList());
    }

    public List<OrganOldmanVo> getOldmanByPage(OrganOldmanParam param, PageParam pageParam) {
        UserBo userBo = UserUtils.getUser();
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getEqualMap().put("userId",userBo.getId());
        OrganBo organBo = organRepository.findWithSpec(jpaSpecBo).stream().findFirst().orElse(null);
        param.setOrganId(organBo.getId());
        return organOldmanRepository.findByPageWithSpec(pageParam.getPageNo(), pageParam.getPageSize(), param.convert()).stream().map(OrganOldmanBo::convertVo).collect(Collectors.toList());

    }

    public Long getOldmanCount(OrganOldmanParam param) {
        return organOldmanRepository.countWithSpec(param.convert());
    }

    public OrganOldmanVo getById(Integer id) {
        return organOldmanRepository.getByPkId(id).convertVo();
    }


    @Transactional
    public void editOrAdd(OrganOldmanParam request) {
        OrganOldmanEntity oldmanEntity = request.convertEntity();
        if (oldmanEntity.getId() != null) {
            organOldmanRepository.dynamicUpdateByPkId(oldmanEntity);
        } else {
            UserBo userBo = UserUtils.getUser();
            JpaSpecBo jpaSpecBo = new JpaSpecBo();
            jpaSpecBo.getEqualMap().put("userId",userBo.getId());
            OrganBo organBo = organRepository.findWithSpec(jpaSpecBo).stream().findFirst().orElse(null);
            oldmanEntity.setOrganId(organBo.getId());
            OrganOldmanEntity result = organOldmanRepository.saveAndReturn(oldmanEntity);
            updateOldmanServiceType(result.getIdCard(),organBo);
        }
    }

    private void updateOldmanServiceType(String idCard, OrganBo organBo) {
        OldmanBo oldmanBo = oldmanRepository.getByIdCards(Lists.newArrayList(idCard)).stream().findFirst().orElse(null);
        if (oldmanBo!=null) {
            OldmanAttrEntity oldmanAttrEntity = new OldmanAttrEntity();
            oldmanAttrEntity.setType(13);
            oldmanAttrEntity.setValue(organBo.getServiceType());
            oldmanAttrEntity.setExt(String.valueOf(organBo.getId()));
            oldmanAttrEntity.setIdCard(oldmanBo.getIdCard());
            oldmanAttrEntity.setOldmanId(oldmanBo.getId());
            oldmanAttrRepository.deleteByType(idCard,Lists.newArrayList(13));
            oldmanAttrRepository.save(oldmanAttrEntity);
        }

    }
}
