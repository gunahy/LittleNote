package com.lesson1.android2.littlenote;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.lesson1.android2.littlenote.db.AppDB;
import com.lesson1.android2.littlenote.settings.MyAppActivity;

public class DealElement extends MyAppActivity {

    private Toolbar toolbar;
    private TextView tvTitleNote, tvDescNote, tvDateNote, tvMap;
    private Intent intent;
    private AppDB db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_element);

        initViews();
        db = initDB(this);
        intent = getIntent();

        setSupportActionBar(toolbar);
        setViewsNoteValue();
    }


    @Override
    protected void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitleNote = (TextView)findViewById(R.id.tvTitleNote);
        tvDescNote = (TextView)findViewById(R.id.tvDescNote);
        tvDateNote = (TextView)findViewById(R.id.tvDateNote);
        tvMap = (TextView)findViewById(R.id.tvMap);
    }

    @Override
    protected AppDB initDB(Context context) {
        return super.initDB(context);
    }

    private void setViewsNoteValue(){
        if (intent != null){
            int noteID = (Integer) intent.getExtras().get(COL__ID);
            cursor = db.getUserNote(noteID);
            if (cursor.moveToFirst()) {
                tvTitleNote.setText(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                tvDescNote.setText(cursor.getString(cursor.getColumnIndex(COL_DESC)));
                tvDateNote.setText(cursor.getString(cursor.getColumnIndex(COL_DATE)));
                tvMap.setText(cursor.getString(cursor.getColumnIndex(COL_COORD)));
            }
            else {
                throw new NullPointerException("cursor = null");
            }

        }
    }
}
