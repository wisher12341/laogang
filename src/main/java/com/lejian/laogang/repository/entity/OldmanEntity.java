package com.lejian.laogang.repository.entity;


import com.google.common.collect.Lists;
import com.lejian.laogang.aop.CodeField;
import com.lejian.laogang.log.LogFieldRecord;
import com.lejian.laogang.log.LogRecord;
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
@LogRecord
public class OldmanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @LogFieldRecord("性别")
    @Column
    private Integer male;
    @LogFieldRecord("姓名")
    @Column
    private String name;
    @LogFieldRecord("户籍")
    @Column
    private Integer huji;
    @LogFieldRecord("出生日期")
    @Column
    private LocalDate birthday;
    @LogFieldRecord("常住地")
    @CodeField
    @Column
    private String residence;
    @LogFieldRecord("户籍地")
    @CodeField
    @Column(name = "huji_address")
    private String hujiAddress;
    @LogFieldRecord("常住地房子类型")
    @Column
    private Integer homeowner;
    @LogFieldRecord("房型")
    @Column(name = "house_type")
    private Integer houseType;
    @LogFieldRecord("楼层")
    @Column
    private Integer floor;
    @LogFieldRecord("手机")
    @CodeField
    @Column
    private String phone;
    @LogFieldRecord("国籍")
    @Column
    private String country;
    @LogFieldRecord("身份证")
    @CodeField
    @Column(name = "id_card")
    private String idCard;
    @LogFieldRecord("区县级行政区")
    @Column(name = "area_country")
    private String areaCountry;
    @LogFieldRecord("乡镇（街道）")
    @Column(name = "area_town")
    private String areaTown;
    @LogFieldRecord("社区级行政区")
    @Column(name = "area_village")
    private String areaVillage;
    @LogFieldRecord("政治面貌")
    @Column(name = "area_custom_one")
    private String areaCustomOne;
    @LogFieldRecord("政治面貌")
    @Column
    private Integer politics;
    @LogFieldRecord("学历")
    @Column
    private Integer education;
    @LogFieldRecord("座机")
    @CodeField
    @Column(name = "landline_number")
    private String landlineNumber;
    @LogFieldRecord("收入")
    @Column
    private Integer income;
    @LogFieldRecord("新冠疫苗种类")
    @Column
    private String vaccine;
    @LogFieldRecord("新冠疫苗第一次时间")
    @Column(name = "vaccine_first")
    private String vaccineFirst;
    @LogFieldRecord("新冠疫苗第二次时间")
    @Column(name = "vaccine_sec")
    private String vaccineSec;
    @LogFieldRecord("商业保险")
    @Column(name = "commercial_insurance")
    private String commercialInsurance;
    @LogFieldRecord("血型")
    @Column
    private String bloodiness;
    @LogFieldRecord("视力情况")
    @Column
    private Integer eyesight;
    @LogFieldRecord("精神状态")
    @Column
    private Integer psychosis;
    @Column(name = "location_id")
    private Integer locationId;
    @LogFieldRecord(value = "状态",defaultValue = "正常")
    @Column
    private Integer status;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "is_zd")
    private Integer isZd;
    @LogFieldRecord("居住地")
    @CodeField
    @Column
    private String address;
    @LogFieldRecord("人户情况")
    @Column
    private Integer rh;
    @Column
    private String lng;
    @Column
    private String lat;
    @LogFieldRecord("退伍军人是否参战")
    @Column
    private String war;
    @Column(name = "datachange_time")
    private Timestamp datachangeTime;
    @LogFieldRecord("操作人")
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "doctor_id")
    private Integer doctorId;

    public static boolean haveField(String fieldName) {
        return Lists.newArrayList("male","huji","area_village").contains(fieldName);
    }
}
