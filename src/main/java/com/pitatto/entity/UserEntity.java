package com.pitatto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "m_user")
public class UserEntity {

    @GeneratedValue
    private int rowid;

    @Id
    @Column(nullable = false)
    private int memberId;

    @Column(nullable = false)
    private String userLoginCode;

    @Column(nullable = false)
    private String user_password_salt;

    @Column(nullable = false)
    private String user_password_hash;

    @Column(columnDefinition = "int default 0")
    private int password_change_status;

    @Column(columnDefinition = "int default 0")
    private int password_change;

    @Column(nullable = false, columnDefinition = "int default 1")
    private int enable_flag;

    @Column(nullable = false, columnDefinition = "int default 1")
    private int display_flag;

    @Column(nullable = false, columnDefinition = "int default 1")
    private int exist_flag;

    @Column(nullable = false)
    private String create_date;

    @Column(nullable = false)
    private String update_date;
}
