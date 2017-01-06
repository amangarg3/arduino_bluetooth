package com.example.android.bluetoothchat;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.DatabaseClass;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by aman on 1/1/17.
 */

public class GraphObjectFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.graph_collection_object, container, false);
        Bundle args = getArguments();
        ((TextView) rootView.findViewById(R.id.text1)).setText(
                Integer.toString(args.getInt(ARG_OBJECT)));

        LineChart lineChart = (LineChart) rootView.findViewById(R.id.line_chart1);
        //  HorizontalBarChart hbarChart = (HorizontalBarChart) findViewById(R.id.hbar_graph);  for horizontal graph

        Cursor c = null;
        DatabaseClass linechartData = new DatabaseClass(getActivity());
        linechartData.open();
        int icount = linechartData.countRows();
        if (icount == 0) {
            Toast.makeText(getActivity(), "Database is empty so can't display the graph", Toast.LENGTH_LONG).show();
            linechartData.close();
            return rootView;
        } else {
            c = linechartData.getRecord();
            linechartData.close();
        }
        //int icount = c.getInt(0);
        int a, i = 0;

        ArrayList<Entry> entries = new ArrayList();
        ArrayList<String> labels = new ArrayList<String>();

        do {
            a = Integer.parseInt(c.getString(1));
            entries.add(new BarEntry(a, i));
            i++;
            labels.add(c.getString(2));
        }
        while (c.moveToNext());

        LineDataSet dataset = new LineDataSet(entries, "# of Calls");

        LineData data = new LineData(labels, dataset);
        lineChart.setData(data);
        lineChart.setDescription("Bar Graph");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        //lineChart.animateY(3000);
        return rootView;
    }

}