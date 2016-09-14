package com.lesson1.android2.littlenote;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Kobzar on 07.09.2016.
 */
public class QuickCursorAdapter extends CursorAdapter {

    private final String COL_NAME = "name";
    private final String COL_LAST_NAME = "lastname";
    private final String COL_AGE = "age";
    private final String COL_ID = "_ID";
    private Cursor mCursor;


    private LayoutInflater cursorInflater;

    public QuickCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCursor = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.user_item_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        PersonHolder holder = new PersonHolder(view);
        holder.tvName.setText(cursor.getString(cursor.getColumnIndex(COL_NAME)) + " " + cursor.getString(cursor.getColumnIndex(COL_LAST_NAME)));
        holder.tvAge.setText(cursor.getString(cursor.getColumnIndex(COL_AGE)));
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    private class PersonHolder {
        TextView tvName;
        TextView tvAge;
        public PersonHolder(View itemView) {
            tvName = (TextView) itemView.findViewById(R.id.etName);
            tvAge = (TextView) itemView.findViewById(R.id.tvAge);
        }
    }
}
