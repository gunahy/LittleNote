package com.lesson1.android2.littlenote;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lesson1.android2.littlenote.db.AppDB;

import java.io.Serializable;

public class DealsList extends AppCompatActivity {

    private static final String EXTRA_USER_ID = "userId";
    private static final String COL_TITLE = "title";
    private static final String COL_DESC = "description";
    private static final String COL_DATE = "date";
    private static final String COL_USERID = "userid";
    private static final String COL_COORD = "coord";


    private int userId;
    private Cursor cursor;
    private NotesCursorAdapter nAdapter;
    private ListView lvUserNote;
    private AppDB db;
    private Toolbar toolbar;
    private AdapterView.OnItemClickListener onItemClickListener;
    private FloatingActionButton fab;
    private View.OnClickListener onClickListener;
    private EditText etTitleNote, etDescNote, etDateNote, etMap;
    private String nTitle, nDesc, nDate, nCoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_list);

        userId = (Integer)getIntent().getExtras().get(EXTRA_USER_ID);

        initViews();
        initDB();
        initListView();

        setSupportActionBar(toolbar);
        lvUserNote.setOnItemClickListener(itemListerListener());

        fab.setOnClickListener(addNote());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private AdapterView.OnItemClickListener itemListerListener(){
        onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor = db.getUserNote(userId, position);
//                if (cursor.moveToLast()){
                    Intent setIntent = new Intent(DealsList.this, DealElement.class);
//                    eIntent.putExtra(COL_USERID, cursor.getString(cursor.getColumnIndex(COL_USERID)));
//                    eIntent.putExtra(COL_TITLE, cursor.getString(cursor.getColumnIndex(COL_TITLE)));
//                    eIntent.putExtra(COL_DESC, cursor.getString(cursor.getColumnIndex(COL_DESC)));
//                    eIntent.putExtra(COL_DATE, cursor.getString(cursor.getColumnIndex(COL_DATE)));
//                    eIntent.putExtra(COL_COORD, cursor.getString(cursor.getColumnIndex(COL_COORD)));
                    startActivity(setIntent);
//                }
//                else {
//                    Toast.makeText(DealsList.this, "Необходимо добвать запись", Toast.LENGTH_LONG).show();
//                }
                //Toast.makeText(DealsList.this, "userId=" + userId + ", _id=" + position, Toast.LENGTH_LONG).show();

            }
        };
        return onItemClickListener;
    }


    private void initViews(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvUserNote = (ListView) findViewById(R.id.lvUserNote);
        etTitleNote = (EditText) findViewById(R.id.etTitleNote);
        etDescNote = (EditText) findViewById(R.id.etDescNote);
        etDateNote = (EditText) findViewById(R.id.etDateNote);
        etMap = (EditText) findViewById(R.id.etMap);
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

    private View.OnClickListener addNote(){
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(DealsList.this, EditDealActivity.class);
                startActivity(editIntent);
            }
        };
        return onClickListener;
    }
}
