package com.lesson1.android2.littlenote;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.lesson1.android2.littlenote.db.AppDB;

import java.io.Serializable;

public class DealsList extends AppCompatActivity {

    private static final String EXTRA_USER_ID = "userId";

    private int userId;
    private Cursor cursor;
    private NotesCursorAdapter nAdapter;
    private ListView lvUserNote;
    private AppDB db;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_list);

        userId = (Integer)getIntent().getExtras().get(EXTRA_USER_ID);

        initViews();
        initDB();
        initListView();

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "User id=" + userId, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvUserNote = (ListView) findViewById(R.id.lvUserNote);

    }

    private void initListView(){
        cursor = db.getAllUserNotes(userId);
        nAdapter = new NotesCursorAdapter(DealsList.this, cursor, 0);
        lvUserNote.setAdapter(nAdapter);
    }

    private void initDB() {
        db = new AppDB(this);
        db.openConnection(true);
    }



}
