package com.hunterstudios.hunters.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BattingIndex {
    private int order;
    private int inning;
    private int number;

    public BattingIndex(BattingIndex index) {
        this.order = index.getOrder();
        this.inning = index.getInning();
        this.number = index.getNumber();
    }

    public String toUri() {
        return String.format("%d/%d/%d", order, inning, number);
    }
}
