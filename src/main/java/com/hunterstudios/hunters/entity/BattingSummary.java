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

    public void calculate() {
        average = (float)hit / dasu;
        // NOTE: 出塁率の分母は 打数+四死球+犠飛 であるが、省略して 打席数 を使用している
        obp = (float)(hit + fball + dball) / daseki;
        slagging = (float)(hit1 + hit2 * 2 + hit3 * 3 + homerun * 4) / dasu;
        noi = (int)((obp + slagging / 3) * 1000);
    }
}
