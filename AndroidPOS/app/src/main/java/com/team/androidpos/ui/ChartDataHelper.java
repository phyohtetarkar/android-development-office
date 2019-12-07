package com.team.androidpos.ui;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.team.androidpos.model.vo.SaleReportVO;

import java.util.ArrayList;
import java.util.List;

public class ChartDataHelper {

    public static BarData toBarChartData(List<SaleReportVO> list) {
        List<BarEntry> entries = new ArrayList<>();
        float i = 1f;
        for (SaleReportVO vo : list) {
            entries.add(new BarEntry(i, vo.getPrice()));
            i += 1f;
        }

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        return new BarData(dataSet);
    }

}
