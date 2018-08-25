package com.hunterstudios.hunters.entity;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventStatus {
    PLANNED(0),
    CLOSED(1),
    DONE(2),
    CANCELED(3),
    NOT_DEFINED(99);

    private int value;

    public static EventStatus getEventStatus(int value) {
        return Arrays.stream(values())
                .filter(v -> v.getValue() == value)
                .findFirst()
                .orElse(NOT_DEFINED);
    }
}
