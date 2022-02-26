package com.lejian.laogang.repository.entity;

import com.lejian.laogang.aop.CodeField;
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
@Table(name = "organ_oldman")
public class OrganOldmanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @CodeField
    @Column(name = "id_card")
    private String idCard;
    @Column
    private String body;
    @Column(name = "start_time")
    private LocalDate startTime;
    @Column(name = "end_time")
    private LocalDate endTime;
    @Column
    private String bed;
    @Column(name = "organ_id")
    private Integer organId;
    @Column
    private Integer status;
    @Column(name = "create_time")
    private Timestamp createTime;
}
