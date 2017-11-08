package com.android.aaditya.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    //ArrayList<TenDaySummary> mTenDaySummary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Lookup the recyclerview in activity layout
        /*RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

        // Initialize contacts
        mTenDaySummary = TenDaySummary.createTenDaySummaryList(20);
        // Create adapter passing in the sample user data
        TenDaySummaryAdapter adapter = new TenDaySummaryAdapter(this, mTenDaySummary);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
        */
    }
}
