package com.lesson1.android2.littlenote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DealElement extends AppCompatActivity {

    private static final String COL_TITLE = "title";
    private static final String COL_DESC = "description";
    private static final String COL_DATE = "date";
    private static final String COL_USERID = "userid";
    private static final String COL_COORD = "coord";

    private Toolbar toolbar;
    private TextView tvTitleNote, tvDescNote, tvDateNote, tvMap;
    private String nTitle, nDesc, nDate, nCoord, nUserID;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_element);

        initViews();
        intent = getIntent();

        setViewsNoteValue(intent);
        setSupportActionBar(toolbar);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitleNote = (TextView)findViewById(R.id.etTitleNote);
        tvDescNote = (TextView)findViewById(R.id.etDescNote);
        tvDateNote = (TextView)findViewById(R.id.etDateNote);
        tvMap = (TextView)findViewById(R.id.etMap);
    }

    private void setViewsNoteValue(Intent intent){
        tvTitleNote.setText(intent.getExtras().getString(COL_TITLE));
        tvDescNote.setText(intent.getExtras().getString(COL_DESC));
        tvDateNote.setText(intent.getExtras().getString(COL_DATE));
        tvMap.setText(intent.getExtras().getString(COL_COORD));
    }

}
