package com.pitatto.request;

import lombok.Data;

@Data
public class InsertPasswordRequest {
    private String loginId;
    private int studentId;
    private String password;
}
