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
@Table(name = "home_doctor")
public class HomeDoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "oldman_id")
    private Integer oldmanId;
    @Column
    private String  name;
    @Column
    private String organ;
    @Column
    private LocalDate time;
    @Column(name = "create_time")
    private Timestamp createTime;
}
