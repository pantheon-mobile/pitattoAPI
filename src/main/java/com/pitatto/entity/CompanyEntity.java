package com.pitatto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "m_company")
public class CompanyEntity {

    @Id
    @GeneratedValue
    private int rowid;

    @Column(nullable = false)
    private String company_code;

    @Column(nullable = false)
    private String company_name;

    @Column
    private String company_kana_name;

    @Column
    private String company_name_alias;

    @Column
    private String company_kana_name_alias;

    @Column
    private String company_name_alias_2;

    @Column
    private String company_kana_name_alias_2;

    @Column
    private String company_name_eng;

    @Column
    private Integer corporate_form_id;

    @Column
    private Integer corporate_form_id_2;

    @Column(columnDefinition = "int default 1")
    private Integer corporate_form_position;

    @Column
    private String company_hp_url;

    @Column
    private String company_domain_name;

    @Column(columnDefinition = "int default 0")
    private Integer company_group_assort;

    @Column
    private Integer establishment_assort;

    @Column
    private String company_establishment;

    @Column
    private String company_info_note;

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
