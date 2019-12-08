package com.team.androidpos.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.team.androidpos.R;
import com.team.androidpos.model.vo.SaleReportVO;
import com.team.androidpos.ui.ChartDataHelper;

public class SaleReportFragment extends Fragment {

    private SaleReportViewModel viewModel;
    private BarChart barChart;
    private PieChart pieChart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(SaleReportViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sale_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barChart = view.findViewById(R.id.barChart);
        barChart.getAxisRight().setEnabled(false);
        //barChart.getXAxis().setDrawLabels(false);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setGranularity(1);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.setScaleEnabled(false);
        barChart.setDescription(null);

        pieChart = view.findViewById(R.id.pieChart);
        pieChart.setDescription(null);
        pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        pieChart.getLegend().setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setExtraOffsets(24, 24, 32, 24);
        pieChart.setHoleRadius(pieChart.getHoleRadius() - 8);
        pieChart.setTransparentCircleRadius(pieChart.getTransparentCircleRadius() - 8);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.getSaleReport().observe(this, list -> {

            barChart.getXAxis().setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    SaleReportVO vo = list.get((int) (value - 1));
                    return vo.getCategory();
                }
            });

            barChart.setData(ChartDataHelper.toBarChartData(list));
            barChart.animateY(1000);

            PieData pieData = ChartDataHelper.toPieChartData(list);
            pieChart.setData(pieData);
            pieChart.setCenterText(String.valueOf(pieData.getYValueSum()));
            pieChart.animateY(1000);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        barChart = null;
        pieChart = null;
    }
}
