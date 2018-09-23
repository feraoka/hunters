package com.hunterstudios.hunters.entity;

import lombok.Data;

@Data
public class Batting {
    private int id;
    private int batterId;
    /** イニング */
    private int inning;
    /**
     * 打席番号<br>
     * 同一イニングで複数打席がある場合にインクリメントして使用
     */
    private int number;
    /**
     * 結果<br/>
     * 1: 安打
     * 2: 二塁打
     * 3: 三塁打
     * 4: 本塁打
     * 5: ゴロアウト
     * 6: ゴロエラー
     * 7: フライアウト
     * 8: フライエラー
     * 9: 四球
     * 10: 死球
     * 11: 三振
     * 12: 三振振逃
     * 13: 犠打
     * 14: 打撃妨害
     */
    private int result;

    /** 打球方向 */
    private int direction;
    /**打点 */
    private int rbi;
    /** 得点 */
    private int point;
    /** 盗塁 */
    private int steal;

    private static final String[] DIRECTIONS = {"", "投", "捕", "一", "二", "三", "遊", "左", "中", "右"};
    private static final String[] RESULT = {"", "安", "二", "三", "本", "ゴ", "ゴE", "飛", "飛E", "四球", "死球", "三振",
            "振逃", "犠", "打撃妨害"};

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(DIRECTIONS[direction])
                .append(RESULT[result]);
        if (point > 0) {
            builder.append("*");
        }
        if (rbi > 0) {
            builder.append(rbi);
        }
        for (int i = 0; i < steal; i++) {
            builder.append("s");
        }
        return builder.toString();
    }
}
