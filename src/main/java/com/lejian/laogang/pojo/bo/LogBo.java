package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.repository.entity.LogEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
public class LogBo extends BaseBo{
    private Integer operation;
    private String table;
    private String pk;
    private Integer userId;
    private String traceId;
    private String data;
    private String pkType;
    private String createTime;

    public static LogBo empty(){
        LogBo logBo = new LogBo();
        logBo.setData("");
        return logBo;
    }

    @Override
    public LogEntity convert() {
        LogEntity entity = new LogEntity();
        BeanUtils.copyProperties(this,entity);
        return entity;
    }
}
