package com.pitatto.response;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

/**
 * schoolClassidとschoolClassnameを扱うクラス。
 * 
 * @author PMS笠井
 * 
 */

@Data
@SuppressWarnings("serial")
public class SchoolIdName implements Serializable {

    /** ID */
    private String schoolClassId;
    /** 名前 */
    private String schoolClassName;
}
