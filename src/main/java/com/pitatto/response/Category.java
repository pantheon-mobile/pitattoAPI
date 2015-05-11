package com.pitatto.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 就活サイトを扱うクラス。
 * 
 * @author PMS笠井
 * 
 */

@Data
@SuppressWarnings("serial")
public class Category implements Serializable {

    /** カテゴリ名 */
    private String categoryName;
    /** メインフラグ */
    private int mainFlag;
}
