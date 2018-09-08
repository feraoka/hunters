package com.hunterstudios.hunters.repository;

import com.hunterstudios.hunters.entity.Event;
import com.hunterstudios.hunters.entity.EventAttendee;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EventRepository {
    Event select(int id);
    List<Event> selectByPeriod(Period period);
    Integer getLastYear();
    List<String> getYearList();
    Event getGameAndBattings(int id);

    /**
     * 直近 n 回の実施済みイベントのリストを返す
     * @param n last n events to query
     * @return list of events of last n
     */
    List<Event> getLastNEvents(int n);

    /**
     * 直近 n 回の実施済みイベントとその参加者のリストを返す
     * @param n last n events to get
     * @return list of events of last n events
     */
    List<EventAttendee> getEventAttendeesInLastNEvents(int n);
}
