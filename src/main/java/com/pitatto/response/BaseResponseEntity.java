package com.pitatto.response;

import lombok.Data;

@Data
public class BaseResponseEntity {

    /** リターンコード */
    public String resultCode;

    /** エラーメッセージ */
    public String errMsg;
}
