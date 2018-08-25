package com.hunterstudios.hunters.entity;

import lombok.Data;

@Data
public class Game {
    private int id;
    private int eventId;
    private int result;
    private boolean batFirst;
    private String scoreA;
    private String scoreB;
    private int pointGot;
    private int pointLost;
    private Event event;
}
