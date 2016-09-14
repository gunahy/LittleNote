package com.lesson1.android2.littlenote.db;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.provider.BaseColumns;

        import java.io.Serializable;

/**
 * Created by admi0 on 05.09.2016.
 */
public class AppDB {

    //Параметры подключения к БД
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "AppDB.db";
    private final Context ctx;

    //Переменные используемые для запросов SQL
    private static final String TEXT_TYPE = " TEXT";
    private static final String NUMERIC_TYPE_BYTE = " BYTE";
    private static final String NUMERIC_TYPE_INT = " INTEGER";
    private static final String DELIMITER = ",";
    private static final String WHERE_ARGS = " = ?";
    private static final String EMPTY_ROW = "Новая заметка";

    //Текстовые SQL запросы
    private static final String SQL_CREATE_USERS = "CREATE TABLE " + Users.TABLE_USERS + " (" +
            Users._ID + " INTEGER PRIMARY KEY," +
            Users.COLUMN_NAME_ENTRY_NAME + TEXT_TYPE + DELIMITER +
            Users.COLUMN_NAME_LAST_NAME + TEXT_TYPE + DELIMITER +
            Users.COLUMN_NAME_AGE + NUMERIC_TYPE_BYTE +
            " )";
    private static final String SQL_CREATE_USERS_NOTES = "CREATE TABLE " + UserNotes.TABLE_USER_NOTE + " (" +
            UserNotes._ID + " INTEGER PRIMARY KEY," +
            UserNotes.COLUMN_NAME_USER_ID + NUMERIC_TYPE_INT + DELIMITER +
            UserNotes.COLUMN_NAME_TITLE + TEXT_TYPE + DELIMITER +
            UserNotes.COLUMN_NAME_DESCRIPTION + TEXT_TYPE +
            UserNotes.COLUMN_NAME_DATE + TEXT_TYPE +
            UserNotes.COLUMN_NAME_COORD + TEXT_TYPE +
            " )";
    private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS " + Users.TABLE_USERS;
    private static final String SQL_DELETE_USERS_NOTES = "DROP TABLE IF EXISTS " + UserNotes.TABLE_USER_NOTE;

    private AppDBHelper appDBHelper;
    private SQLiteDatabase mDB;
    private ContentValues v_user, v_note;

    public AppDB(Context context) {
        ctx = context;
    }

    /*
     * Создаем внутренние статические классы для описания таблиц базы данных
     */

    //Внутренний класс для описания таблицы Users
    public static class Users implements BaseColumns {
        public static final String TABLE_USERS = "users";
        public static final String COLUMN_NAME_ENTRY_NAME = "name";
        public static final String COLUMN_NAME_LAST_NAME = "lastname";
        public static final String COLUMN_NAME_AGE = "age";
    }

    //Внутренний класс для описания таблицы UserNote
    public static class UserNotes implements BaseColumns {
        public static final String TABLE_USER_NOTE = "usernote";
        public static final String COLUMN_NAME_USER_ID = "userid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_COORD = "coord";
    }

    //Подключаемся к БД
    public void openConnection(boolean readOnly) {
        appDBHelper = new AppDBHelper(ctx, DB_NAME, null, DB_VERSION);
        if (readOnly) {
            mDB = appDBHelper.getReadableDatabase();
        }else {mDB = appDBHelper.getWritableDatabase();}
    }

    // закрыть подключение
    public void closeConnection() {
        if (appDBHelper !=null) appDBHelper.close();
    }

    // получить все данные из таблицы Users
    public Cursor getAllUsers() {
        return mDB.query(Users.TABLE_USERS, null, null, null, null, null, null);
    }

    // выбрать все заметки пользователя
    public Cursor getAllUserNotes(long userId) {
        return mDB.query(UserNotes.TABLE_USER_NOTE, null, UserNotes.COLUMN_NAME_USER_ID + WHERE_ARGS, new String[]{String.valueOf(userId)}, null, null, null);
    }

    // выбрать заметку польователя
    public Cursor getUserNote(int userId,long id){
        return mDB.query(UserNotes.TABLE_USER_NOTE, null, UserNotes.COLUMN_NAME_USER_ID
                + " = " + userId + " AND " + UserNotes._ID + " = " + id, null, null, null, null);
    }

    // добавить запись в Users
    public long addUser(String name, String lastName, int age) {
        v_user = new ContentValues();
        v_note = new ContentValues();

        v_user.put(Users.COLUMN_NAME_ENTRY_NAME, name);
        v_user.put(Users.COLUMN_NAME_LAST_NAME, lastName);
        v_user.put(Users.COLUMN_NAME_AGE, age);

        long id = mDB.insert(Users.TABLE_USERS, null, v_user);
        v_note.put(UserNotes.COLUMN_NAME_USER_ID, id);
        v_note.put(UserNotes.COLUMN_NAME_TITLE, EMPTY_ROW);

        mDB.insert(UserNotes.TABLE_USER_NOTE, null, v_note);
        return id;
    }

    // добавить заметку польователя
    public long addNote(int userId, String title, String decs, String date, String coord) {
        v_note = new ContentValues();
        v_note.put(UserNotes.COLUMN_NAME_USER_ID, userId);
        v_note.put(UserNotes.COLUMN_NAME_TITLE, title);
        v_note.put(UserNotes.COLUMN_NAME_DESCRIPTION, decs);
        v_note.put(UserNotes.COLUMN_NAME_DATE, date);
        v_note.put(UserNotes.COLUMN_NAME_COORD, coord);
        return mDB.insert(UserNotes.TABLE_USER_NOTE, null, v_note);
    }

    // удалить запись из Users
    public void deleteUser(long id) {
        mDB.delete(Users.TABLE_USERS, Users._ID + " = " + id, null);
        mDB.delete(UserNotes.TABLE_USER_NOTE, UserNotes.COLUMN_NAME_USER_ID + " = " + id, null);
    }

    // обновить запись из Users
    public void updateUser(long id, String name, String lastName, int age) {
        v_user = new ContentValues();
        v_user.put(Users.COLUMN_NAME_ENTRY_NAME, name);
        v_user.put(Users.COLUMN_NAME_LAST_NAME, lastName);
        v_user.put(Users.COLUMN_NAME_AGE, age);
        mDB.update(Users.TABLE_USERS, v_user, Users._ID + " = " + id, null);
    }

    public void updateNote(long userId, String title, String decription, String date, String coord) {
        v_note = new ContentValues();
        v_note.put(UserNotes.COLUMN_NAME_TITLE, title);
        v_note.put(UserNotes.COLUMN_NAME_DESCRIPTION, decription);
        v_note.put(UserNotes.COLUMN_NAME_DATE, date);
        v_note.put(UserNotes.COLUMN_NAME_COORD, coord);
        mDB.update(UserNotes.TABLE_USER_NOTE, v_note, UserNotes.COLUMN_NAME_USER_ID + " = " + userId, null);
    }

    //Очистить таблицу Users и вместе с ней userNote
    public void clearTableUser(){
        mDB.delete(Users.TABLE_USERS, null, null);
        mDB.delete(UserNotes.TABLE_USER_NOTE, null, null);
    }

    public class AppDBHelper extends SQLiteOpenHelper {

        public AppDBHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, dbName, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_USERS);
            db.execSQL(SQL_CREATE_USERS_NOTES);
            //db.rawQuery("INSERT INTO " + UserNotes.TABLE_USER_NOTE + " (" + UserNotes.COLUMN_NAME_USER_ID + ") ")

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_USERS);
            db.execSQL(SQL_DELETE_USERS_NOTES);
            onCreate(db);
        }



    }


}
