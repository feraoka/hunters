package com.hunterstudios.hunters.entity;

import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class BatterForm {
    private int eventId;
    @UniqueElements
    private List<Integer> orderList;
}
