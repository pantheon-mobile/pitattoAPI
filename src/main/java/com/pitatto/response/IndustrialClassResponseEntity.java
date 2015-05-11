package com.pitatto.response;

import java.util.ArrayList;

import lombok.Data;

@Data
public class IndustrialClassResponseEntity extends BaseResponseEntity {

    /** 業種 */
    private ArrayList<IdName> divisions;
}
