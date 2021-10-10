package com.lejian.laogang.controller.contract.response;

import com.lejian.laogang.pojo.vo.MessageVo;
import lombok.Data;

import java.util.List;

@Data
public class GetMessageResponse {
    private List<MessageVo> voList;
    private Long count;
}
