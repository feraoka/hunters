package com.hunterstudios.hunters.entity;

import lombok.Data;

@Data
public class Member {
    private int id;
    private String nickname;
    /**
     * 1: 現役
     * 2: 助っ人
     */
    private int status;

    public static boolean isValidStatus(int status) {
        return status == 1 || status == 2;
    }
}
