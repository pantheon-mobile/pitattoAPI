package com.pitatto.request;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

/**
 * 企業基本情報を扱うクラス。 お気に入り管理APIで使用する。
 * 
 * @author PMS笠井
 * 
 */
@Data
@SuppressWarnings("serial")
public class CompanyReq implements Serializable {
    /** メンバーID */
    private String id;
    /** 企業コード */
    private String code;
    /** ランク */
    private String rank;
}
