package com.lejian.laogang.repository.entity;

import com.lejian.laogang.log.LogFieldRecord;
import com.lejian.laogang.log.LogRecord;
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
@Table(name = "home_bed")
@LogRecord("oldmanId")
public class HomeBedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "oldman_id")
    private Integer oldmanId;
    @Column
    @LogFieldRecord("家庭病床机构")
    private String organ;
    @Column
    @LogFieldRecord("家庭病床签约时间")
    private LocalDate time;
    @Column(name = "create_time")
    private Timestamp createTime;
}
