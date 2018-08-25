package com.hunterstudios.hunters.entity;

import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class Event {
    private int id;
    private OffsetDateTime date;
    private String location;
    private String ground;
    private String type;
    private String opponent;
    private EventStatus status;
    private String note;
    private boolean expense;
}
