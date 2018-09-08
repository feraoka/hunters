package com.hunterstudios.hunters.entity;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.Data;

@Data
public class EventAttendee {
    private int id;
    private String type;
    private OffsetDateTime date;
    List<Member> members;

    public boolean hasMember(int id) {
        return members.stream().anyMatch(m -> m.getId() == id);
    }
}
