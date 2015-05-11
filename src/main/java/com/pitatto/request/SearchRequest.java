package com.pitatto.request;

import lombok.Data;

@Data
public class SearchRequest {
    /** メンバーID */
    private String id;
    /** 業種 */
    private String category;
    /** 本社所在地 */
    private String address;
    /** フリーワード */
    private String word;
    /** オフセット */
    private String offset;
    /** 最大取得件数 */
    private String limit;
    /** ソート */
    private String sort;
    /** 年度 */
    private String year;
}
