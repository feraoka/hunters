package com.hunterstudios.hunters.view;

import com.hunterstudios.hunters.entity.Event;
import com.hunterstudios.hunters.helper.DateHelper;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class EventDetailView {
    private Event event;
    private Date date;
    private List<List<String>> scoreboard;
    private List<List<String>> batting;

    public EventDetailView(Event event) {
        this.event = event;
        this.date = DateHelper.toDate(event.getDate());
    }
}
