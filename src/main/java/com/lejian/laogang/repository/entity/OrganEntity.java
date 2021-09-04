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
    private String lng;
    @Column
    private String lat;
    @Column
    private String name;
    @Column
    private String desc;

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
    @Column(name = "create_time")
    private Timestamp createTime;
}
