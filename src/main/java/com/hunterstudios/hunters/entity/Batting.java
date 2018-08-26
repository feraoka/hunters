package com.hunterstudios.hunters.entity;

import lombok.Data;

@Data
public class Batting {
    private int id;
    private int batterId;
    /**
     * イニング
     */
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
    /**
     * 打数<br/>
     * 四死球、犠打などは0
     * その他は1
     */
    private int dasu;
    /**
     * 打球<br>
     * 0: ゴロ
     * 1: フライ
     * 2: ライナー
     * 3: オーバー
     */
    private int dakyu;
    /**
     * 打球方向
     */
    private int direction;
    /**
     * 打点
     */
    private int rbi;
    /**
     * 得点
     */
    private int point;
    /**
     * 盗塁
     */
    private int steal;
    /**
     * 塁打
     */
    private int hits;
    /**
     * 単打
     */
    private int hit1;
    /**
     * 二塁打
     */
    private int hit2;
    /**
     * 三塁打
     */
    private int hit3;
    /**
     * 本塁打
     */
    private int homerun;
    /**
     * 三振
     */
    private int sout;
    /**
     * 四球
     */
    private int fball;
    /**
     * 死球
     */
    private int dball;
    /**
     * メモ
     */
    private String note;

    private static final String[] DIRECTIONS = {"", "投", "捕", "一", "二", "三", "遊", "左", "中", "右"};
    private static final String[] RESULT = {"", "安", "二", "三", "本", "ゴ", "ゴE", "飛", "飛E", "四球", "死球", "三振",
            "振逃", "犠", "打撃妨害"};

    @Override
    public String toString() {
        return DIRECTIONS[direction] + RESULT[result];
    }
}
