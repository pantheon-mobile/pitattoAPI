package com.pitatto.request;

import lombok.Data;

@Data
public class FavoriteListRequest {
    /** メンバーID */
    private String id;
    /** オフセット */
    private String offset;
    /** 最大取得件数 */
    private String limit;
    /** ソート */
    private String sort;
    /** 年度 */
    private String year;
}
