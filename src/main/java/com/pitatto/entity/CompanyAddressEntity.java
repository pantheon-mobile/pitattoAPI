package com.pitatto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "m_company_address")
public class CompanyAddressEntity {

    @Id
    @GeneratedValue
    private int rowid;

    @Column(nullable = false)
    private int company_id;

    @Column(columnDefinition = "int default 1")
    private int headquater_assort;

    @Column
    private String zip;

    @Column
    private String address_all;

    @Column
    private String address_1;

    @Column
    private String address_2;

    @Column
    private String address_3;

    @Column
    private int country_id;

    @Column
    private int country_region_id;

    @Column
    private int country_state_id;

    @Column
    private int prefecture_id;

    @Column
    private int city_id;

    @Column
    private int city_block_id;

    @Column
    private int town_id;

    @Column
    private String address_detail;

    @Column
    private String company_address_note;

    @Column
    private String tel_number;

    @Column
    private int tel_number_assort;

    @Column
    private String tel_number_2;

    @Column
    private int tel_number_assort_2;

    @Column
    private String company_tel_note;

    @Column
    private int sort_order;

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
