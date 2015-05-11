package com.pitatto.utils;

public final class ResponseUtil {
    // 返却するリターンコード
    /** エラーがなく正常に処理された場合 */
    public static final String RETURN_CODE_0000 = "0000";
    /** パスワード期限切れ */
    public static final String RETURN_CODE_0001 = "0001";
    public static final String RETURN_MSG_0001 = "パスワード期限切れ";
    /** ログイン状態でない */
    public static final String RETURN_CODE_0002 = "0002";
    public static final String RETURN_MSG_0002 = "ログイン状態でない";
    /** 認証エラー */
    public static final String RETURN_CODE_1000 = "1000";
    public static final String RETURN_MSG_1000 = "認証エラー";
    /** IDが既に使用されている */
    public static final String RETURN_CODE_1500 = "1500";
    public static final String RETURN_MSG_1500 = "IDが既に使用されている";
    /** IDが最小文字数に達してない */
    public static final String RETURN_CODE_1501 = "1501";
    public static final String RETURN_MSG_1501 = "IDが最小文字数に達してない";
    /** IDが最大文字数を超えている */
    public static final String RETURN_CODE_1502 = "1502";
    public static final String RETURN_MSG_1502 = "IDが最大文字数を超えている";
    /** IDが禁則文字を含んでいる */
    public static final String RETURN_CODE_1503 = "1503";
    public static final String RETURN_MSG_1503 = "IDが禁則文字を含んでいる";
    /** メールアドレス形式でない */
    public static final String RETURN_CODE_1504 = "1504";
    public static final String RETURN_MSG_1504 = "メールアドレス形式でない";
    /** メール送信失敗 */
    public static final String RETURN_CODE_1505 = "1505";
    public static final String RETURN_MSG_1505 = "メール送信失敗";
    /** 必須項目に値が無い */
    public static final String RETURN_CODE_1506 = "1506";
    public static final String RETURN_MSG_1506 = "必須項目に値が無い";
    /** 最小文字数不足(詳細はerrMsg) */
    public static final String RETURN_CODE_1507 = "1507";
    public static final String RETURN_MSG_1507 = "最小文字数不足";
    /** 最大文字数超過(詳細はerrMsg) */
    public static final String RETURN_CODE_1508 = "1508";
    public static final String RETURN_MSG_1508 = "最大文字数超過";
    /** 該当する企業が存在しない（お気に入りの削除） */
    public static final String RETURN_CODE_2001 = "2001";
    public static final String RETURN_MSG_2001 = "該当する企業が存在しない";
    /** 不正な引数 */
    public static final String RETURN_CODE_2101 = "2101";
    public static final String RETURN_MSG_2101 = "不正な引数";
    /** セッションタイムアウト */
    public static final String RETURN_CODE_3000 = "3000";
    public static final String RETURN_MSG_3000 = "セッションタイムアウト";
    /** システムエラー */
    public static final String RETURN_CODE_3001 = "3001";
    public static final String RETURN_MSG_3001 = "システムエラー";
    /** 致命的なエラー */
    public static final String RETURN_CODE_3002 = "3002";
    public static final String RETURN_MSG_3002 = "致命的なエラーです。";
}
