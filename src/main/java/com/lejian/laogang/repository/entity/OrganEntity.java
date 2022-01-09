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
@Table(name = "organ")
public class OrganEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String descri;

    @Column
    private String service;
    @Column
    private String address;
    @Column
    private String web;

    @Column
    private String email;
    @Column
    private String phone;
    @Column(name = "worker_time")
    private String workerTime;

    @Column
    private String ssqk;
    @Column
    private String rzyq;
    @Column(name = "bed_number")
    private Integer bedNumber;

    @Column
    private Integer type;
    private String gps;
    @Column(name = "create_time")
    private Timestamp createTime;
    private Integer status;
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "service_type")
    private Integer serviceType;
}
