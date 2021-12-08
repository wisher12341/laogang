package com.lejian.laogang.service;

import com.google.common.collect.Lists;
import com.lejian.laogang.controller.contract.request.OldmanParam;
import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.controller.contract.request.PolicyParam;
import com.lejian.laogang.enums.UserEnum;
import com.lejian.laogang.pojo.bo.*;
import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.pojo.vo.PolicyVo;
import com.lejian.laogang.repository.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void add(PolicyParam policyParam, OldmanParam oldmanParam) {
        PolicyBo policyBo = policyRepository.saveAndReturn(PolicyBo.convert(policyParam));
        bindOldman(policyBo,oldmanParam);
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getEqualMap().put("role",UserEnum.Role.A2.getValue());
        List<UserBo> userBoList = userRepository.findWithSpec(jpaSpecBo);
        userBoList.forEach(user->{
            MessageBo messageBo = new MessageBo();
            messageBo.setSender("系统消息");
            messageBo.setType(1);
            messageBo.setUserId(user.getId());
            messageBo.setTitle("政策");
            messageBo.setContent(String.format("新政策！<a href='/home/policy/info?id=%s'>%s</a>",policyBo.getId(),policyParam.getName()));
            messageRepository.save(messageBo);
        });
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
                policyOldmanBo.setOldmanUserId(bo.getUserId());
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
                policyOldmanBo.setOldmanUserId(bo.getUserId());
                return policyOldmanBo;
            }).collect(Collectors.toList()));
        }
    }

    public List<PolicyVo> getByPage(PageParam pageParam, PolicyParam policyParam) {
        List<Sort.Order> orders = Lists.newArrayList();
        orders.add(new Sort.Order(Sort.Direction.DESC, "createTime"));
        Sort sort = Sort.by(orders);
        List<PolicyBo> boList = policyRepository.findByPageWithSpec(pageParam.getPageNo(),pageParam.getPageSize(),policyParam.convert(),sort);
        return boList.stream().map(PolicyBo::convertVo).collect(Collectors.toList());
    }

    public Long count(PolicyParam policyParam) {
        return policyRepository.countWithSpec(policyParam.convert());
    }

    public List<OldmanVo> getOldmanByPage(PageParam pageParam, Integer id, UserBo userBo) {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getEqualMap().put("policyId",id);
        if (userBo.getRole().intValue() == UserEnum.Role.A2.getValue()){
            jpaSpecBo.getEqualMap().put("oldmanUserId",userBo.getId());
        }
        Map<Integer,PolicyOldmanBo> policyOldmanBoMap = policyOldmanRepository.findByPageWithSpec(pageParam.getPageNo(),pageParam.getPageSize(),jpaSpecBo).stream().collect(Collectors.toMap(PolicyOldmanBo::getOldmanId, Function.identity()));
        List<OldmanBo> oldmanBoList = oldmanRepository.getByPkIds(Lists.newArrayList(policyOldmanBoMap.keySet()));

        return oldmanBoList.stream().map(item->item.convertVo(policyOldmanBoMap.get(item.getId()))).collect(Collectors.toList());
    }

    public Long oldmanCount(Integer id, UserBo userBo) {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getEqualMap().put("policyId",id);
        if (userBo.getRole().intValue() == UserEnum.Role.A2.getValue()){
            jpaSpecBo.getEqualMap().put("oldmanUserId",userBo.getId());
        }
        return policyOldmanRepository.countWithSpec(jpaSpecBo);
    }

    public PolicyVo getById(Integer id) {
        return policyRepository.getByPkId(id).convertVo();
    }

    public void oldmanFinish(Integer finish, Integer oldmanId, Integer policyId) {
        policyOldmanRepository.updateFinish(finish,oldmanId,policyId);
    }

    @Transactional
    public void delete(List<Integer> idList) {
        idList.forEach(id->{
            policyRepository.deleteById(id);
            policyOldmanRepository.deleteByPolicyId(id);
        });
    }

    public void edit(PolicyParam policyParam) {
        PolicyBo policyBo = policyParam.convertToBo();
        policyRepository.dynamicUpdateByPkId(policyBo);
    }
}
