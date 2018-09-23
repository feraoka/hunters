package com.hunterstudios.hunters.view;

import java.util.List;
import lombok.Data;

@Data
public class BattingEditView {
    private int eventId;
    /**
     * order, inning, number の順に格納される
     */
    private List<List<List<String>>> battings;
    private int inning;
}
