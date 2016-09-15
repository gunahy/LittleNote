package com.lesson1.android2.littlenote;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lesson1.android2.littlenote.db.AppDB;
import com.lesson1.android2.littlenote.settings.Initialable;
import com.lesson1.android2.littlenote.settings.MyAppActivity;

public class DealEdit extends MyAppActivity implements View.OnClickListener {

    private AppDB db;
    private EditText etTitleNote, etDescNote, etDateNote, etMap;
    private Button btnSave;
    private String nTitleNote, nDescNote, nDateNote, nCoord;
    private int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deal);

        initViews();
        db = initDB(this);

        setViewListeners();
    }

    private void setViewListeners(){
        btnSave.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        etTitleNote = (EditText) findViewById(R.id.etTitleNote);
        etDescNote = (EditText) findViewById(R.id.etDescNote);
        etDateNote = (EditText) findViewById(R.id.etDateNote);
        etMap = (EditText) findViewById(R.id.etMap);
        btnSave = (Button) findViewById(R.id.btnSaveNote);
    }

    @Override
    protected AppDB initDB(Context context) {
        return super.initDB(context);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            userID = (Integer)getIntent().getExtras().get(COL_USERID);
            nTitleNote = etTitleNote.getText().toString();
            nDescNote = etDescNote.getText().toString();
            nDateNote = etDateNote.getText().toString();
            nCoord = etMap.getText().toString();

            db.addNote(userID, nTitleNote, nDescNote, nDateNote, nCoord);
            finish();
        }
    }
}
