package com.lejian.laogang.repository.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name = "log")
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer operation;
    @Column(name = "table_name")
    private String table;
    private String pk;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "trace_id")
    private String traceId;
    private String data;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "pk_type")
    private String pkType;

}
