package com.pitatto.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pitatto.entity.UserEntity;
import com.pitatto.repository.UserRepository;

@Component
public final class DateUtils {

    private static UserRepository userRepository;

    @Autowired
    UserRepository tUserRepository;

    private static long sessionTime = 16 * 60 * 60 * 1000;

    @PostConstruct
    public void init() {
        DateUtils.userRepository = tUserRepository;
    }

    /**
     * 現在日時をyyyy/MM/dd HH:mm:ss形式で取得する.<br>
     */
    public static String getNowDate() {
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    /**
     * セッションが有効かどうかを返す.<br>
     */
    public static boolean isInSession(String sessionDate) {
        Long sessionDateLong;
        try {
            sessionDateLong = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(sessionDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        Long currentTimeLong = System.currentTimeMillis();
        return (sessionDateLong + sessionTime <= currentTimeLong) ? false : true;
    }

    /**
     * セッションタイムアウトをリセット.<br>
     */
    public static void resetSessionTime(String id) {
        // 該当レコードを取得
        UserEntity update = userRepository.getByMemberId(Integer.parseInt(id));
        if (update != null) {
            update.setUser_password_salt(DateUtils.getNowDate());
            userRepository.save(update);
        }
    }

    /**
     * 現在日時をyyyy/MM/dd HH:mm:ss形式で取得する.<br>
     */
    public static String getExpiredDate() {
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis() - sessionTime);
        return df.format(date);
    }
}
