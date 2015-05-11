package com.pitatto.response;

import java.io.Serializable;

import lombok.Data;

/**
 * idとnameを扱うクラス。 業種リスト取得API、地域リスト取得APIで使用する。
 * 
 * @author PMS笠井
 * 
 */

@Data
@SuppressWarnings("serial")
public class IdName implements Serializable {

    /** ID */
    private String id;
    /** 名前 */
    private String name;
}
