package com.pitatto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "m_company_industrial_class")
public class CompanyIndustrialClassEntity {

    @Id
    @Column
    @GeneratedValue
    private int rowid;

    @Column
    private int company_id;

    @Column
    private int industrial_class_id;

    @Column
    private int industrial_class_l_id;

    @Column
    private int industrial_class_m_id;

    @Column
    private int industrial_class_s_id;

    @Column(columnDefinition = "int default 1")
    private int main_industrial_class_flag;

    @Column
    private String company_industrial_class_note;

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
