package com.team.androidpos.ui;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.team.androidpos.model.vo.SaleReportVO;

import java.math.RoundingMode;
import java.text.DecimalFormat;
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

        BarDataSet dataSet = new BarDataSet(entries, "Categories");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setValueTextSize(14f);
        dataSet.setHighlightEnabled(false);

        return new BarData(dataSet);
    }

    public static PieData toPieChartData(List<SaleReportVO> list) {
        List<PieEntry> entries = new ArrayList<>();
        for (SaleReportVO vo : list) {
            entries.add(new PieEntry(vo.getPrice(), vo.getCategory()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setSliceSpace(2f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueLinePart1OffsetPercentage(100);
        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setValueTextSize(14f);
        dataSet.setSelectionShift(0f);

        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                DecimalFormat df = new DecimalFormat("###,###.#");
                df.setRoundingMode(RoundingMode.CEILING);
                return df.format(value) + " %";
            }
        });

        return new PieData(dataSet);
    }

}
