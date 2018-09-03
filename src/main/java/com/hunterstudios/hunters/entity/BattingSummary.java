package com.hunterstudios.hunters.entity;

import lombok.Data;

@Data
public class BattingSummary {
    private int memberId;
    private String name;
    private int game;
    private int daseki;
    private int dasu;
    private int steal;
    private int rbi;
    private int hit;
    private int hit1;
    private int hit2;
    private int hit3;
    private int homerun;
    private int sout;
    private int dball;
    private int fball;
    private int ruida;
    private float average;
    /**
     * 出塁率
     */
    private float obp;
    /**
     * 長打率
     */
    private float slagging;
    /**
     * NOI
     */
    private int noi;
}
