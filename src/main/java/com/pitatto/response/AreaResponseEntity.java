package com.pitatto.response;

import java.util.ArrayList;

import lombok.Data;

@Data
public class AreaResponseEntity extends BaseResponseEntity {
    /** 地域 */
    private ArrayList<IdName> areas;
}
