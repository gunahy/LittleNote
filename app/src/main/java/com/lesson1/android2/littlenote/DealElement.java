package com.lesson1.android2.littlenote;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class DealElement extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_fragment);

        initViews();

        setSupportActionBar(toolbar);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
