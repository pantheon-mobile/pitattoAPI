package com.pitatto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "m_industrial_class_l")
public class IndustrialClassLEntity {

    @Id
    @GeneratedValue
    private int rowid;

    @Column
    private String industrial_class_l_code;

    @Column
    private String industrial_class_l_name;

    @Column
    private String industrial_class_l_kana_name;

    @Column(nullable = false)
    private int sort_order;

    @Column(nullable = false)
    private int enable_flag;

    @Column(nullable = false)
    private int display_flag;

    @Column(nullable = false)
    private int exist_flag;

    @Column(nullable = false)
    private String create_date;

    @Column(nullable = false)
    private String update_date;
}
