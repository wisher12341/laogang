package com.lejian.laogang.repository.entity;

import com.lejian.laogang.enums.BusinessEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name = "oldman_attr")
public class OldmanAttrEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "oldman_id")
    private Integer oldmanId;
    @Column(name = "id_card")
    private String idCard;
    @Column
    private Integer type;
    @Column
    private Integer value;
    @Column
    private String ext;
    @Column(name = "create_time")
    private Timestamp createTime;
}
