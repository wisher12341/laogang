package com.lejian.laogang.service;

import com.google.common.collect.Lists;
import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.MessageBo;
import com.lejian.laogang.pojo.bo.UserBo;
import com.lejian.laogang.pojo.vo.MessageVo;
import com.lejian.laogang.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<MessageVo> getByPage(PageParam pageParam, UserBo userBo) {
        List<Sort.Order> orders = Lists.newArrayList();
        orders.add(new Sort.Order(Sort.Direction.DESC, "createTime"));
        Sort sort = Sort.by(orders);
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getEqualMap().put("userId",userBo.getId());
        List<MessageBo> messageBos = messageRepository.findByPageWithSpec(pageParam.getPageNo(),pageParam.getPageSize(),jpaSpecBo,sort);
        return messageBos.stream().map(MessageBo::convertVo).collect(Collectors.toList());
    }

    public Long count(PageParam pageParam, UserBo userBo) {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getEqualMap().put("userId",userBo.getId());
        return messageRepository.countWithSpec(jpaSpecBo);
    }
}
