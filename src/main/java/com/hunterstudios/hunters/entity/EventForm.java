package com.hunterstudios.hunters.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class EventForm {
    private int id;
    @NotBlank(message="必須です")
    private String type;
    private boolean expense;
    private String opponent;
    @NotNull(message="日時を入力してください")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;
    private String location;
    private String locationNumber;
    private String note;
    private List<Integer> attendees;
}
