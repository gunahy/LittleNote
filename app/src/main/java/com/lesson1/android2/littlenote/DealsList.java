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

public class DealsList extends AppCompatActivity implements Initialable {

    private static final String EXTRA_USER_ID = "userId";

    private int userId;
    private Cursor cursor;
    private NotesCursorAdapter nAdapter;
    private ListView lvUserNote;
    private AppDB db;
    private Toolbar toolbar;
    private AdapterView.OnItemClickListener onItemClickListener;
    private FloatingActionButton fab;
    private View.OnClickListener onClickListener;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_list);

        userId = (Integer)getIntent().getExtras().get(EXTRA_USER_ID);

        initDB();
        initViews();
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
//                cursor = db.getUserNote(1, 1);
//                String a = cursor.getString(cursor.getColumnIndex(COL_TITLE));
//                Toast.makeText(DealsList.this, a, Toast.LENGTH_LONG).show();


//                intent = new Intent(DealsList.this, DealElement.class);
//                intent.putExtra(COL_USERID, userId);
//                intent.putExtra(COL__ID, 1);
//                Toast.makeText(DealsList.this, "userID=" + userId + " id=" + id, Toast.LENGTH_LONG).show();
//                startActivity(intent);
            }
        };
        return onItemClickListener;
    }

    private View.OnClickListener addNote(){
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DealsList.this, EditDeal.class);
                intent.putExtra(COL_USERID, userId);
                startActivity(intent);
            }
        };
        return onClickListener;
    }

    @Override
    public void initViews(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvUserNote = (ListView) findViewById(R.id.lvUserNote);
    }

    @Override
    public void initListView(){
        cursor = db.getAllUserNotes(userId);
        nAdapter = new NotesCursorAdapter(DealsList.this, cursor, 0);
        lvUserNote.setAdapter(nAdapter);
    }

    @Override
    public void initDB() {
        db = new AppDB(this);
        db.openConnection(false);
    }

}
