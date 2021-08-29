package com.lejian.laogang.repository.entity;


import com.google.common.collect.Lists;
import com.lejian.laogang.pojo.bo.BaseBo;
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
@Table(name = "oldman_view")
public class OldmanViewEntity{

    @Column
    private Integer male;
    @Column
    private String name;
    @Column
    private Integer huji;
    @Column
    private LocalDate birthday;
    @Column
    private String country;
    @Id
    @Column(name = "id_card")
    private String idCard;
    @Column(name = "area_country")
    private String areaCountry;
    @Column(name = "area_town")
    private String areaTown;
    @Column(name = "area_village")
    private String areaVillage;
    @Column(name = "area_custom_one")
    private String areaCustomOne;
    @Column
    private Integer politics;
    @Column
    private Integer education;
    @Column
    private Integer eyesight;
    @Column
    private Integer psychosis;
    private String location;
    @Column(name = "is_zd")
    private Integer isZd;
    @Column
    private String label;

}
