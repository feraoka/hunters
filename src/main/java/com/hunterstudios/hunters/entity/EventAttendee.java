package com.hunterstudios.hunters.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class EventAttendee {
    private int id;
    private String type;
    private LocalDateTime date;
    List<Member> members;

    public boolean hasMember(int id) {
        return members.stream().anyMatch(m -> m.getId() == id);
    }
}
