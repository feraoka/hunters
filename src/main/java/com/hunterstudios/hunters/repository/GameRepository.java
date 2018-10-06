package com.hunterstudios.hunters.repository;

import com.hunterstudios.hunters.entity.Game;
import com.hunterstudios.hunters.entity.GameTeam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GameRepository {
    Game getGame(int eventId);
    Integer getLastYear();
    List<String> getYearList();
    List<Game> selectByPeriod(Period period);
    List<Game> selectByTeam(String team);
    Integer getCount(Period period);
    void insert(Game game);
    void update(Game game);
    List<GameTeam> getSummaryByTeam();
}
