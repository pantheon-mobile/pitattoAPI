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
public class Site implements Serializable {

    /** サイト名 */
    private String siteName;
    /** リンクの有無 */
    private int available;
}
