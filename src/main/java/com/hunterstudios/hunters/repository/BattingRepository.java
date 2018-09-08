package com.hunterstudios.hunters.repository;

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
}
