package com.pitatto.response;

import java.util.ArrayList;

import lombok.Data;

@Data
public class FavoriteListResponseEntity extends BaseResponseEntity {

    /** 全件数 */
    public String total;

    /** 企業 */
    private ArrayList<Favorite> companies;
}
