package com.lesson1.android2.littlenote;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.lesson1.android2.littlenote.db.AppDB;

public class DealElement extends AppCompatActivity implements Initialable{

    private Toolbar toolbar;
    private TextView tvTitleNote, tvDescNote, tvDateNote, tvMap;
    private String nTitle, nDesc, nDate, nCoord, nUserID;
    private Intent intent;
    private AppDB db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_element);

        initViews();
        intent = getIntent();

        setSupportActionBar(toolbar);

        setViewsNoteValue(intent);

    }

    @Override
    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitleNote = (TextView)findViewById(R.id.tvTitleNote);
        tvDescNote = (TextView)findViewById(R.id.tvDescNote);
        tvDateNote = (TextView)findViewById(R.id.tvDateNote);
        tvMap = (TextView)findViewById(R.id.tvMap);
    }

    @Override
    public void initDB() {
        db = new AppDB(this);
        db.openConnection(false);
    }

    @Override
    public void initListView() {

    }

    private void setViewsNoteValue(Intent intent){

        int noteID = (int)intent.getExtras().get(COL__ID);
        int userID = (int)intent.getExtras().get(COL_USERID);
        cursor = db.getUserNote(userID, noteID);

        tvTitleNote.setText(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
        tvDescNote.setText(cursor.getString(cursor.getColumnIndex(COL_DESC)));
        tvDateNote.setText(cursor.getString(cursor.getColumnIndex(COL_DATE)));
        tvMap.setText(cursor.getString(cursor.getColumnIndex(COL_COORD)));
    }


}
