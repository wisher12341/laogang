package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OrganOldmanEnum;
import com.lejian.laogang.pojo.vo.OrganOldmanVo;
import com.lejian.laogang.pojo.vo.OrganVo;
import com.lejian.laogang.repository.entity.OrganEntity;
import com.lejian.laogang.repository.entity.OrganOldmanEntity;
import com.lejian.laogang.util.AESUtils;
import com.lejian.laogang.util.StringUtils;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.lejian.laogang.util.DateUtils.YY_MM_DD;

@Data
public class OrganOldmanBo extends BaseBo {
    private Integer id;
    private String name;
    private String idCard;
    private String body;
    private LocalDate startTime;
    private LocalDate endTime;
    private String bed;
    private Integer organId;
    private Integer status;
    @Override
    public OrganOldmanEntity convert() {
        OrganOldmanEntity entity = new OrganOldmanEntity();
        BeanUtils.copyProperties(this,entity);
        AESUtils.encode(entity);
        return entity;
    }

    public static OrganOldmanBo convert(OrganOldmanEntity entity) {
        AESUtils.decode(entity);
        OrganOldmanBo organBo = new OrganOldmanBo();
        BeanUtils.copyProperties(entity,organBo);
        return organBo;
    }

    public OrganOldmanVo convertVo(){
        OrganOldmanVo vo = new OrganOldmanVo();
        BeanUtils.copyProperties(this,vo);
        if (this.getStartTime()!=null) {
            vo.setStartTime(this.getStartTime().format(YY_MM_DD));
        }
        if (this.getEndTime()!=null) {
            vo.setEndTime(this.getEndTime().format(YY_MM_DD));
        }
        if (StringUtils.isNotBlank(body)){
            String[] arr = body.split(",");
            List<String> list = Arrays.stream(arr).map(item->BusinessEnum.find(Integer.parseInt(item), OrganOldmanEnum.Body.class).getDesc()).collect(Collectors.toList());
            vo.setBodyDesc(String.join(",",list));
        }
        return vo;
    }
}
