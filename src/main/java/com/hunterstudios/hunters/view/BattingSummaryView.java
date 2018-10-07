package com.hunterstudios.hunters.view;

import com.hunterstudios.hunters.entity.BattingSummary;
import java.util.List;
import lombok.Data;

@Data
public class BattingSummaryView {
    private List<BattingSummary> effectiveSummary;
    private List<BattingSummary> ineffectiveSummary;
    private BattingSummary total;
}
