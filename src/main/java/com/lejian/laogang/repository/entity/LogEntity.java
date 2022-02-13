package com.lejian.laogang.repository.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

//@DynamicInsert
//@DynamicUpdate
//@Data
//@Entity
//@Table(name = "log")
public class LogEntity {
    private Long id;
    //操作类型 1插入 2编辑 3删除
    private Integer type;
    private String table;
    private Integer pk;
    @Column(name = "user_id")
    private Integer userId;
    //批次
    private String batchId;
    private String data;
    @Column(name = "create_time")
    private Timestamp createTime;

}
