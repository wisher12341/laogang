package com.lejian.laogang.repository.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name = "policy")
public class PolicyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String content;
    @Column
    private Integer type;
    @Column
    private String wh;

    @Column(name = "start_time")
    private LocalDate startTime;
    @Column(name = "end_time")
    private LocalDate endTime;

    @Column(name = "create_time")
    private Timestamp createTime;
    @Column
    private Integer status;
}
