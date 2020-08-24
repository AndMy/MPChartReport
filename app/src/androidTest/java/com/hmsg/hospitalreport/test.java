package com.hmsg.hospitalreport;

import android.content.Context;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.hmsg.hospitalreport.bean.Cell;
import com.hmsg.hospitalreport.chart.LineChartMarkView;

public class test {
    private View.OnClickListener blockListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Cell cell = (Cell) view.getTag();
            if (cell != null) {
            }
        }
    };
}
