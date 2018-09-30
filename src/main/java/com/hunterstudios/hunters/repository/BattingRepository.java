package com.hunterstudios.hunters.repository;

import com.hunterstudios.hunters.entity.Batting;
import com.hunterstudios.hunters.entity.BattingSearch;
import com.hunterstudios.hunters.entity.BattingSummary;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BattingRepository {

    /**
     * 指定した期間の打撃成績を返す
     * @param period period to be queried
     * @return list of battings
     */
    List<BattingSummary> getBattingSummary(Period period);

    /**
     * 直近 n 試合の打撃成績を返す
     * @param n last n games
     * @return list of battings
     */
    List<BattingSummary> getBattingSummaryOfLastNGames(int n);

    Batting select(BattingSearch battingSearch);
    void insert(Batting batting);
    void update(Batting batting);
    void delete(int id);
    List<Batting> selectByInning(BattingSearch battingSearch);
}
