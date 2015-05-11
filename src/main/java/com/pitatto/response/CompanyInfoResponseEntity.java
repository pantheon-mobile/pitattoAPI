package com.pitatto.response;

import java.util.ArrayList;

import lombok.Data;

@Data
public class CompanyInfoResponseEntity extends BaseResponseEntity {

    /** 企業名 */
    public String name;

    /** カテゴリ */
    private ArrayList<Category> category;

    /** 本社所在地 */
    public String area;

    /** 企業URL */
    public String url;

    /** 最終更新日 */
    public String updateDate;

    /** 郵便番号 */
    public String zip;

    /** 住所 */
    public String address;

    /** 電話番号 */
    public String tel;

    /** 設立日 */
    public String establishment;

    /** 資本金 */
    public String capital;

    /** 代表者名 */
    public String representativeName;

    /** リンク */
    private ArrayList<Link> links;
}
