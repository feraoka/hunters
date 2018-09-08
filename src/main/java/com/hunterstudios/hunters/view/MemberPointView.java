package com.hunterstudios.hunters.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class MemberPointView {
    @Data
    public static class Event {
        private Date date;
        private String name;
    }
    @Data
    public static class MemberPoint {
        /**
         * member id
         */
        private int id;
        private String name;
        private int defensePoint;
        private float offensePoint;
        private int noi;
        private List<Boolean> attended;
        public void addDefensePoint(int point) {
            defensePoint += point;
        }
        public void addOffensePoint(float point) {
            offensePoint += point;
        }
    }
    private List<Event> eventList = new ArrayList<>();
    private List<MemberPoint> memberList;
}
