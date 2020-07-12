package com.example.payo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.payo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.payo.activities.AnalyseSMS.barGraphData;

public class TagBarChartActivity extends AppCompatActivity {

    AnyChartView pieChart, barChart;
    Map<String, Integer> GraphData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_bar_chart);
        //pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChart);
        GraphData = barGraphData;
        setUpBarchart();
    }


    void setUpBarchart()
    {
        Cartesian cartesian = AnyChart.column();
        List<DataEntry> data = new ArrayList<>();

        Iterator<String> iterator = GraphData.keySet().iterator();
        if(iterator.hasNext())
        {
            do{
                String key = iterator.next();
                data.add(new ValueDataEntry(key, GraphData.get(key)));
            }while(iterator.hasNext());
        }
        else
            return;
        Column column = cartesian.column(data);
        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .format("{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Top 10 Cosmetic Products by Revenue");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Tags");
        cartesian.yAxis(0).title("Occurance");

        barChart.setChart(cartesian);
    }
}
