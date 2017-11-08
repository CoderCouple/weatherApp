package com.android.aaditya.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {
    ArrayList<TenDaySummary> mTenDaySummary;
    ArrayList<OneDaySummary> mOneDaySummary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        /*RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

        // Initialize contacts
        mTenDaySummary = TenDaySummary.createTenDaySummaryList(20);
        // Create adapter passing in the sample user data
        TenDaySummaryAdapter adapter1 = new TenDaySummaryAdapter(this, mTenDaySummary);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter1);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));



        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        // Initialize contacts
        mOneDaySummary = OneDaySummary.createOneDaySummaryList(20);
        // Create adapter passing in the sample user data
        OneDaySummaryAdapter adapter2 = new OneDaySummaryAdapter(this, mOneDaySummary);
        // Attach the adapter to the recyclerview to populate items
        list.setAdapter(adapter2);
        // Set layout manager to position the items
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        // That's all!
        */
    }
}
