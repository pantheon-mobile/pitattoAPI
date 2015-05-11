package com.pitatto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "m_prefecture")
public class PrefectureEntity {

    @Id
    @GeneratedValue
    private int rowid;

    @Column(nullable = false)
    private int country_id;

    @Column
    private Integer country_region_id;

    @Column
    private Integer country_state_id;

    @Column(nullable = false)
    private String prefecture_code;

    @Column(nullable = false)
    private String prefecture_name;

    @Column(nullable = false)
    private String prefecture_kana_name;

    @Column
    private String prefecture_name_eng;

    @Column
    private Integer sort_order;

    @Column
    private Integer display_priority;

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
