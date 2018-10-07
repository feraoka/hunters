package com.hunterstudios.hunters.view;

import java.util.List;
import lombok.Data;

@Data
public class TitleView {
    @Data
    public static class NameValue {
        private List<String> names;
        private float value;
    }
    private int year;
    private NameValue average;
    private NameValue homerun;
    private NameValue rbi;
    private NameValue steal;
}
