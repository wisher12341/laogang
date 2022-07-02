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
@Table(name = "intelligent_device")
@LogRecord("oldmanId")
public class IntelligentDeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "oldman_id")
    private Integer oldmanId;
    @LogFieldRecord("智能设备名称")
    @Column
    private String  name;
    @LogFieldRecord("智能设备开始时间")
    @Column(name = "start_time")
    private LocalDate startTime;
    @LogFieldRecord("智能设备结束时间")
    @Column(name = "end_time")
    private LocalDate endTime;
    @Column(name = "create_time")
    private Timestamp createTime;
}
