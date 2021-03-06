package com.hunterstudios.hunters.view;

import com.hunterstudios.hunters.entity.Event;
import com.hunterstudios.hunters.helper.DateHelper;
import java.util.Date;
import lombok.Data;

@Data
public class EventView {
    private Event event;
    private Date date;

    public EventView(Event event) {
        this.event = event;
        this.date = DateHelper.toDate(event.getDate());
    }
}
