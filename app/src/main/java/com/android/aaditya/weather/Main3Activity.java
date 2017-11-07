package com.android.aaditya.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    ArrayList<OneDaySummary> mOneDaySummary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        // Initialize contacts
        mOneDaySummary = OneDaySummary.createOneDaySummaryList(20);
        // Create adapter passing in the sample user data
        OneDaySummaryAdapter adapter = new OneDaySummaryAdapter(this, mOneDaySummary);
        // Attach the adapter to the recyclerview to populate items
        list.setAdapter(adapter);
        // Set layout manager to position the items
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        // That's all!

    }
}
