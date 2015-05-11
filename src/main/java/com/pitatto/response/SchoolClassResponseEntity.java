package com.pitatto.response;

import java.util.ArrayList;

import lombok.Data;

@Data
public class SchoolClassResponseEntity extends BaseResponseEntity {

    /** 地域 */
    private ArrayList<SchoolIdName> schoolClass;
}
