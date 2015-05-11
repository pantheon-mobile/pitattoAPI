package com.pitatto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "m_country_region")
public class CountryRegionEntity {

    @Id
    @GeneratedValue
    private int rowid;

    @Column(nullable = false)
    private int country_id;

    @Column
    private String parent_country_region_code;

    @Column
    private String country_region_code;

    @Column(nullable = false)
    private String country_region_formal_name;

    @Column
    private String country_region_formal_kana_name;

    @Column
    private String country_region_formal_name_eng;

    @Column
    private String country_region_common_name;

    @Column
    private String country_region_common_kana_name;

    @Column
    private String country_region_common_name_eng;

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
