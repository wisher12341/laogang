package com.lejian.laogang.repository.entity;


import com.google.common.collect.Lists;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name = "oldman")
public class OldmanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer male;
    @Column
    private String name;
    @Column
    private Integer huji;
    @Column
    private LocalDate birthday;
    @Column
    private String residence;
    @Column(name = "huji_address")
    private String hujiAddress;
    @Column
    private Integer homeowner;
    @Column(name = "house_type")
    private Integer houseType;
    @Column
    private Integer floor;
    @Column
    private String phone;
    @Column
    private String country;
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
    @Column(name = "landline_number")
    private String landlineNumber;
    @Column
    private Integer income;
    @Column
    private String vaccine;
    @Column(name = "vaccine_first")
    private String vaccineFirst;
    @Column(name = "vaccine_sec")
    private String vaccineSec;
    @Column(name = "commercial_insurance")
    private String commercialInsurance;
    @Column
    private String bloodiness;
    @Column
    private Integer eyesight;
    @Column
    private Integer psychosis;
    @Column(name = "location_id")
    private Integer locationId;
    @Column
    private Integer status;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "is_zd")
    private Integer isZd;
    @Column
    private String war;
    @Column(name = "datachange_time")
    private Timestamp datachangeTime;

    public static boolean haveField(String fieldName) {
        return Lists.newArrayList("male","huji","area_village").contains(fieldName);
    }
}
