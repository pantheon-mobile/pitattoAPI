package com.pitatto.response;

import java.util.ArrayList;

import lombok.Data;

@Data
public class UserIdAvailabilityResponseEntity {

    /** リターンコード */
    public String resultCode;

    /** エラーメッセージ */
    public String errMsg;
}
