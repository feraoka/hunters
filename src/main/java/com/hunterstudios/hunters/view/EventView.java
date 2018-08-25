package com.hunterstudios.hunters.view;

import java.util.Date;
import lombok.Data;

@Data
public class EventView {
    private int id;
    private String type;
    private Date date;
    private String location;
    private String opponent;
}
