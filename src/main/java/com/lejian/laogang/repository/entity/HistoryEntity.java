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
@Table(name = "history")
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long count;
    @Column(name = "area_village")
    private String areaVillage;
    @Column
    private Integer type;
    @Column
    private Integer value;
    @Column
    private String time;
    @Column(name = "create_time")
    private Timestamp createTime;
}
