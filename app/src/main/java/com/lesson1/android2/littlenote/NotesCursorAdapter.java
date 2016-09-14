package com.lesson1.android2.littlenote;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kobzar on 07.09.2016.
 */
public class NotesCursorAdapter extends CursorAdapter {

    private final String COL_USER_ID = "userid";
    private final String COL_TITLE = "title";
    private final String COL_DESCRIPTION = "description";
    private final String COL_DATE = "date";
    private final String COL_ID = "_ID";
    private Cursor mCursor;


    private LayoutInflater cursorInflater;

    public NotesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCursor = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return cursorInflater.inflate(R.layout.note_item_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        NotesHolder holder = new NotesHolder(view);
        holder.tvItemNote.setText(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
    }

    private class NotesHolder {
        TextView tvItemNote;

        public NotesHolder(View itemView) {
            tvItemNote = (TextView) itemView.findViewById(R.id.tvItemNote);
        }
    }
}
