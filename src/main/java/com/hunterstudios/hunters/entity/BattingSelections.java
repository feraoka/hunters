package com.hunterstudios.hunters.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class BattingSelections {
    private static final List<Selection> _resultList = new ArrayList<Selection>() {{
        add(new Selection("", 0));
        add(new Selection("安打", 1));
        add(new Selection("二塁打", 2));
        add(new Selection("三塁打", 3));
        add(new Selection("本塁打", 4));
        add(new Selection("ゴロアウト", 5));
        add(new Selection("ゴロエラー", 6));
        add(new Selection("フライアウト", 7));
        add(new Selection("フライエラー", 8));
        add(new Selection("四球", 9));
        add(new Selection("死球", 10));
        add(new Selection("三振", 11));
        add(new Selection("三振振逃", 12));
        add(new Selection("犠打", 13));
        add(new Selection("打撃妨害", 14));
    }};

    private static final List<Selection> _directionList = new ArrayList<Selection>() {{
        add(new Selection("", 0));
        add(new Selection("ピッチャー", 1));
        add(new Selection("キャッチャー", 2));
        add(new Selection("ファースト", 3));
        add(new Selection("セカンド", 4));
        add(new Selection("サード", 5));
        add(new Selection("ショート", 6));
        add(new Selection("レフト", 7));
        add(new Selection("センター", 8));
        add(new Selection("ライト", 9));
        add(new Selection("レフトオーバー", 10));
        add(new Selection("センターオーバー", 11));
        add(new Selection("ライトオーバー", 12));
    }};

    private static final List<Selection> _rbiList = new ArrayList<Selection>() {{
        add(new Selection("", 0));
        add(new Selection("1", 1));
        add(new Selection("2", 2));
        add(new Selection("3", 3));
        add(new Selection("4", 4));
    }};

    private static final List<Selection> _stealList = new ArrayList<Selection>() {{
        add(new Selection("", 0));
        add(new Selection("1", 1));
        add(new Selection("2", 2));
        add(new Selection("3", 3));
    }};

    private List<Selection> resultList = _resultList;
    private List<Selection> directionList = _directionList;
    private List<Selection> rbiList = _rbiList;
    private List<Selection> stealList = _stealList;
}
