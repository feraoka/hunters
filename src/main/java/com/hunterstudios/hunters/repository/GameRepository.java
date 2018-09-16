package com.hunterstudios.hunters.repository;

import com.hunterstudios.hunters.entity.Game;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GameRepository {
    Integer getLastYear();
    List<String> getYearList();
    List<Game> selectByPeriod(Period period);
    Integer getCount(Period period);
}
