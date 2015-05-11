package com.pitatto.response;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

/**
 * 企業基本情報を扱うクラス。 検索API使用する。
 * 
 * @author PMS笠井
 * 
 */
@Data
@SuppressWarnings("serial")
public class Company implements Serializable {

    /** 企業コード */
    private String code;
    /** 企業名 */
    private String name;
    /** 企業名カナ */
    private String kana;
    /** 業種 */
    private String category;
    /** 地域 */
    private String area;
    /** 企業URL */
    private String url;
    /** 最終更新日 */
    private String lastUpdateDate;
    /** 新規登録日 */
    private String registDate;
    /** 評価 */
    private String rank;
    /** 就活サイト */
    private ArrayList<Site> links;
}
