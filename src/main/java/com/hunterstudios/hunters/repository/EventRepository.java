package com.hunterstudios.hunters.repository;

import com.hunterstudios.hunters.entity.Event;
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
}
