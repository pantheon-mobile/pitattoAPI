package com.pitatto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "m_industrial_class_m")
public class IndustrialClassMEntity {

    @Id
    @GeneratedValue
    private int rowid;

    @Column
    public Integer industrial_class_l_id;

    @Column
    private String industrial_class_m_code;

    @Column
    private String industrial_class_m_name;

    @Column
    private String industrial_class_m_kana_name;

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
