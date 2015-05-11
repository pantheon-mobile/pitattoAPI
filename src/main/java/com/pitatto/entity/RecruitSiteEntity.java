package com.pitatto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "t_company_recruit_site_page")
public class RecruitSiteEntity {

    @Id
    @GeneratedValue
    private int rowid;

    @Column(nullable = false)
    private int company_id;

    @Column
    private int recruit_site_year_id;

    @Column
    private String company_recruit_site_name;

    @Column(nullable = false)
    private String recruit_site_company_id;

    @Column
    private String company_recruit_site_page_url;

    @Column(columnDefinition = "int default 1")
    private Integer main_recruit_site_page_flag;

    @Column(columnDefinition = "int default 1")
    private Integer publish_status;

    @Column(columnDefinition = "int default 1")
    private Integer entry_flag;

    @Column(columnDefinition = "int default 1")
    private Integer posting_flag;

    @Column(columnDefinition = "int default 1")
    private Integer seminar_open_flag;

    @Column(columnDefinition = "int default 1")
    private Integer new_arrivals;

    @Column
    private String recruit_site_update_date;

    @Column
    private String recruit_site_publish_date;

    @Column
    private String company_recruit_site_page_note;

    @Column(columnDefinition = "int default 1")
    private Integer sort_order;

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
