package com.lejian.laogang.service;

import com.google.common.collect.Lists;
import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.MessageBo;
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

    public List<MessageVo> getByPage(PageParam pageParam) {
        List<Sort.Order> orders = Lists.newArrayList();
        orders.add(new Sort.Order(Sort.Direction.DESC, "startTime"));
        Sort sort = Sort.by(orders);
        List<MessageBo> messageBos = messageRepository.findByPageWithSpec(pageParam.getPageNo(),pageParam.getPageSize(),new JpaSpecBo(),sort);
        return messageBos.stream().map(MessageBo::convertVo).collect(Collectors.toList());
    }

    public Long count(PageParam pageParam) {
        return messageRepository.count();
    }
}
