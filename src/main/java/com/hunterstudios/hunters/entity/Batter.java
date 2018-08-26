package com.hunterstudios.hunters.entity;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class Batter {
    private int id;
    private int eventId;
    private int memberId;
    private int bOrder;
    private Member member;
    private List<Batting> battings;

    public List<Batting> getBattings(int inning) {
        return battings.stream().filter(b -> b.getInning() == inning).collect(Collectors.toList());
    }
}
