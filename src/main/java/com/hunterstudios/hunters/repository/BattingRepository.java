package com.hunterstudios.hunters.repository;

import com.hunterstudios.hunters.entity.Batter;
import com.hunterstudios.hunters.entity.Batting;
import com.hunterstudios.hunters.entity.BattingSummary;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BattingRepository {
    /**
     * 指定した試合のイニング数を返す
     * @param eventId イベントID
     * @return イニング数
     */
    Integer getNumInnings(int eventId);

    /**
     * 指定した試合のバッターリストを返す
     * @param eventId イベントID
     * @return バッターのリスト
     */
    List<Batter> getBatterList(int eventId);

    /**
     * バッター指定でバッティング結果を返す
     * @param batterId バッターID
     * @return 打撃成績のリスト
     */
    List<Batting> getBattingsByBatterId(int batterId);

    List<BattingSummary> getBattingSummary(Period period);
}
