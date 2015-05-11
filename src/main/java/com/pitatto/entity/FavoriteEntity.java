package com.pitatto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "t_member_favorite")
public class FavoriteEntity {

    @Id
    @GeneratedValue
    private int rowid;

    @Column(nullable = false)
    private int member_id;

    @Column(nullable = false)
    private int company_id;

    @Column(nullable = false)
    private String rank;

    @Column(nullable = false)
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
