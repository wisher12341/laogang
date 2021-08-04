package com.lejian.laogang.service;

import com.lejian.laogang.controller.contract.request.GetLabelRequest;
import com.lejian.laogang.enums.label.LabelEnum;
import com.lejian.laogang.enums.label.LabelFirst;
import com.lejian.laogang.pojo.bo.LabelBo;
import com.lejian.laogang.pojo.vo.LabelVo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabelService {

    public List<LabelVo> getLabel(GetLabelRequest request){
        return LabelEnum.findByParent(request.getParent()).stream()
                .map(LabelEnum::label)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(LabelBo::getSort))
                .map(LabelBo::convert)
                .collect(Collectors.toList());
    }

    public List<LabelVo> getLabelFirst() {
        return LabelFirst.label().stream().map(LabelBo::convert).collect(Collectors.toList());
    }
}
