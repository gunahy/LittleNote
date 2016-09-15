package com.lesson1.android2.littlenote.settings;

import android.content.Context;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lesson1.android2.littlenote.db.AppDB;

/**
 * Created by Kobzar on 15.09.2016.
 */
public abstract class MyAppActivity extends AppCompatActivity implements Initialable {

    protected AppDB db;
    /*
     * Инициализация элементов UI
     */
    protected abstract void initViews();

    /*
     * Инициализация базы данных
     */
    protected AppDB initDB(Context context){
        db = new AppDB(context);
        db.openConnection(false);
        return db;
    }

    /*
     * Инициализация ListView
     */
    protected void initListView(){}

    /*
     * Закрываем базу данных при выходе
     * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.closeConnection();
    }

}
