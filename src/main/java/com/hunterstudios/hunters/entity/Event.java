package com.hunterstudios.hunters.entity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Data;

@Data
public class Event {
    private int id;
    private LocalDateTime date;
    private String location;
    private String ground;
    private String type;
    private String opponent;
    private EventStatus status;
    private String note;
    private boolean expense;
    private Game game;
    private List<Batter> batters;
    private List<Member> attendees;
}
