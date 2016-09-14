package com.lesson1.android2.littlenote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DealElement extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvTitleNote, tvDescNote, tvDateNote, tvMap;
    private ArrayAdapter elementAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_element);

        initViews();

        setSupportActionBar(toolbar);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitleNote = (TextView)findViewById(R.id.tvTitleNote);
        tvDescNote = (TextView)findViewById(R.id.tvDescNote);
        tvDateNote = (TextView)findViewById(R.id.tvDateNote);
        tvMap = (TextView)findViewById(R.id.tvMap);

    }
}
