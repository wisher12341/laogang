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
@Table(name = "message")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "role_id")
    private Integer roleId;
    @Column
    private String sender;
    @Column
    private Integer type;
    @Column(name = "create_time")
    private Timestamp createTime;
}
