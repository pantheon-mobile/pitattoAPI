package com.pitatto.request;

import java.util.ArrayList;

import lombok.Data;

@Data
public class FavoriteRequest {
    /** 会社情報 */
    private ArrayList<CompanyReq> company;
}
