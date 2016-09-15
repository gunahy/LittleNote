package com.lesson1.android2.littlenote;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lesson1.android2.littlenote.adapters.NotesCursorAdapter;
import com.lesson1.android2.littlenote.db.AppDB;
import com.lesson1.android2.littlenote.settings.Initialable;
import com.lesson1.android2.littlenote.settings.MyAppActivity;

public class DealsList extends MyAppActivity {


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

        userId = (Integer)getIntent().getExtras().get(COL_USERID);

        db = initDB(this);
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
                intent = new Intent(DealsList.this, DealElement.class);
                intent.putExtra(COL__ID, (int)id);
                startActivity(intent);

            }
        };
        return onItemClickListener;
    }

    private View.OnClickListener addNote(){
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DealsList.this, DealEdit.class);
                intent.putExtra(COL_USERID, userId);
                startActivity(intent);
            }
        };
        return onClickListener;
    }

    @Override
    protected void initViews(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvUserNote = (ListView) findViewById(R.id.lvUserNote);
    }

    @Override
    protected void initListView(){
        cursor = db.getAllUserNotes(userId);
        nAdapter = new NotesCursorAdapter(DealsList.this, cursor, 0);
        lvUserNote.setAdapter(nAdapter);
    }

    @Override
    protected AppDB initDB(Context context) {
        return super.initDB(context);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initListView();
    }
}
