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

import static com.example.payo.activities.AnalyseSMS.pieChartData;

public class PieCharts extends AppCompatActivity {
    AnyChartView pieChart, barChart;
    Map<String, Integer> GraphData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        pieChart = findViewById(R.id.pieChart);
        //barChart = findViewById(R.id.barChart);

        GraphData = pieChartData;
        setUpPieChart();
        //setUpBarchart();
    }

    public void setUpPieChart()
    {

        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries =  new ArrayList<>();


        Iterator<String> iterator = GraphData.keySet().iterator();
        if(iterator.hasNext())
        {
            do{
                String key = iterator.next();
                dataEntries.add(new ValueDataEntry(key, GraphData.get(key)));
            }while(iterator.hasNext());
        }
        else
            return;

        pie.data(dataEntries);
        pie.title("Income vs Expense");
        pieChart.setChart(pie);
    }

    void setUpBarchart()
    {
        Cartesian cartesian = AnyChart.column();
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Rouge", 80540));
        data.add(new ValueDataEntry("Foundation", 94190));
        data.add(new ValueDataEntry("Mascara", 102610));
        data.add(new ValueDataEntry("Lip gloss", 110430));
        data.add(new ValueDataEntry("Lipstick", 128000));
        data.add(new ValueDataEntry("Nail polish", 143760));
        data.add(new ValueDataEntry("Eyebrow pencil", 170670));
        data.add(new ValueDataEntry("Eyeliner", 213210));
        data.add(new ValueDataEntry("Eyeshadows", 249980));

        Column column = cartesian.column(data);
        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Top 10 Cosmetic Products by Revenue");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Product");
        cartesian.yAxis(0).title("Revenue");

        barChart.setChart(cartesian);
    }
}
