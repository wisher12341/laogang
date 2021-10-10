package com.lejian.laogang.service;

import com.lejian.laogang.controller.contract.request.OldmanParam;
import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.controller.contract.request.PolicyParam;
import com.lejian.laogang.pojo.bo.*;
import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.pojo.vo.PolicyVo;
import com.lejian.laogang.repository.MessageRepository;
import com.lejian.laogang.repository.OldmanRepository;
import com.lejian.laogang.repository.PolicyOldmanRepository;
import com.lejian.laogang.repository.PolicyRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private OldmanRepository oldmanRepository;
    @Autowired
    private PolicyOldmanRepository policyOldmanRepository;
    @Autowired
    private MessageRepository messageRepository;


    @Transactional
    public void add(PolicyParam policyParam, OldmanParam oldmanParam) {
        PolicyBo policyBo = policyRepository.saveAndReturn(PolicyBo.convert(policyParam));
        bindOldman(policyBo,oldmanParam);
        MessageBo messageBo = new MessageBo();
        messageBo.setRoleId(2);
        messageBo.setSender("系统消息");
        messageBo.setType(1);
        messageBo.setContent(String.format("新政策！<a href='/policy/detail?id=%s'>%s</a>",policyBo.getId(),policyParam.getName()));
        messageRepository.save(messageBo);
    }

    /**
     * 绑定政策老人
     * @param policyBo
     * @param oldmanParam
     */
    private void bindOldman(PolicyBo policyBo, OldmanParam oldmanParam) {
        int pageNo=0;
        int pageSize=500;
        List<OldmanBo> oldmanBoList = oldmanRepository.findByPage(pageNo, pageSize, oldmanParam.getSql());
        while (oldmanBoList.size()==500){
            policyOldmanRepository.batchInsert(oldmanBoList.stream().map(bo->{
                PolicyOldmanBo policyOldmanBo = new PolicyOldmanBo();
                policyOldmanBo.setOldmanId(bo.getId());
                policyOldmanBo.setPolicyId(policyBo.getId());
                return policyOldmanBo;
            }).collect(Collectors.toList()));
            pageNo++;
            oldmanBoList = oldmanRepository.findByPage(pageNo, pageSize, oldmanParam.getSql());
        }
        if (CollectionUtils.isNotEmpty(oldmanBoList)){
            policyOldmanRepository.batchInsert(oldmanBoList.stream().map(bo->{
                PolicyOldmanBo policyOldmanBo = new PolicyOldmanBo();
                policyOldmanBo.setOldmanId(bo.getId());
                policyOldmanBo.setPolicyId(policyBo.getId());
                return policyOldmanBo;
            }).collect(Collectors.toList()));
        }
    }

    public List<PolicyVo> getByPage(PageParam pageParam, PolicyParam policyParam) {
        List<PolicyBo> boList = policyRepository.findByPageWithSpec(pageParam.getPageNo(),pageParam.getPageSize(),policyParam.convert());
        return boList.stream().map(PolicyBo::convertVo).collect(Collectors.toList());
    }

    public Long count(PolicyParam policyParam) {
        return policyRepository.countWithSpec(policyParam.convert());
    }

    public List<OldmanVo> getOldmanByPage(PageParam pageParam, Integer id) {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getEqualMap().put("policyId",id);
        List<PolicyOldmanBo> policyOldmanBoList = policyOldmanRepository.findByPageWithSpec(pageParam.getPageNo(),pageParam.getPageSize(),jpaSpecBo);
        List<OldmanBo> oldmanBoList = oldmanRepository.getByPkIds(policyOldmanBoList.stream().map(PolicyOldmanBo::getOldmanId).collect(Collectors.toList()));
        return oldmanBoList.stream().map(OldmanBo::convertVo).collect(Collectors.toList());
    }

    public Long oldmanCount(Integer id) {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getEqualMap().put("policyId",id);
        return policyOldmanRepository.countWithSpec(jpaSpecBo);
    }
}
