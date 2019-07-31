package com.meysam.tasbih.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.meysam.tasbih.Classes.Doaha;
import com.meysam.tasbih.R;

import java.util.ArrayList;
import java.util.List;

public class NemoudarActivity extends AppCompatActivity {

    BarChart barChart;
    List<Doaha> doahaList;
    ArrayList<Doaha> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemoudar);

        barChart = findViewById(R.id.barChart);

        BarDataSet barDataSet= new BarDataSet(dataValues(),"DataSet");

        BarData barData = new BarData();
        barData.addDataSet(barDataSet);

        barChart.setData(barData);
        barChart.invalidate();

    }

    private ArrayList<BarEntry> dataValues(){
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        dataVals.add(new BarEntry(0 , 3));
        dataVals.add(new BarEntry(1 , 5));
        dataVals.add(new BarEntry(2 , 6));
        dataVals.add(new BarEntry(3 , 7));
        dataVals.add(new BarEntry(4 , 8));
        dataVals.add(new BarEntry(5 , 9));
        return dataVals;


    }
}
