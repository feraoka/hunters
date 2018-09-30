package com.hunterstudios.hunters.entity;

import lombok.Data;

@Data
public class BattingForm {
    private String uri;
    private int result;
    private int direction;
    private int rbi;
    private boolean point;
    private int steal;
    private boolean delete;
    private String next;
    private String prev;
}
