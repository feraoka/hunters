package com.hunterstudios.hunters.view;

import java.util.List;
import lombok.Data;

@Data
public class BattingGameView {
    @Data
    public static class MemberBatting {
        private String name;
        private List<String> result;
    }
    private List<MemberBatting> data;
}
