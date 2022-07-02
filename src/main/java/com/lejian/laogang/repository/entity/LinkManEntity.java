package com.lejian.laogang.repository.entity;


import com.lejian.laogang.aop.CodeField;
import com.lejian.laogang.log.LogRecord;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name = "linkman")
public class LinkManEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @CodeField
    @Column
    private String phone;
    @Column
    private String relation;
    @Column(name = "id_card")
    @CodeField
    private String idCard;
    @Column(name = "oldman_id")
    private Integer oldmanId;
    @Column
    private Integer iscall;
    @Column(name = "create_time")
    private Timestamp createTime;

}
