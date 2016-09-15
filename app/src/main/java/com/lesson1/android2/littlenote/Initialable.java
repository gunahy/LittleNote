package com.lesson1.android2.littlenote;

import com.lesson1.android2.littlenote.db.AppDB;

/**
 * Created by admi0 on 14.09.2016.
 */
public interface Initialable {

    String COL_TITLE = "title";
    String COL_DESC = "description";
    String COL_DATE = "date";
    String COL_USERID = "userid";
    String COL_COORD = "coord";
    String COL__ID = "_ID";


    /*

    * Инициализация элементов UI
    */
    void initViews();

    /*
    * Инициализация базы данных
    */
    void initDB();

    /*
    * Инициализация ListView
    */
    void initListView();

}
