package com.lesson1.android2.littlenote;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lesson1.android2.littlenote.adapters.PersonCursorAdapter;
import com.lesson1.android2.littlenote.db.AppDB;
import com.lesson1.android2.littlenote.settings.MyAppActivity;

public class MainActivity extends MyAppActivity {

    private static final String DIALOG_TITLE_ADD = "Добавить пользователя";
    private static final String DIALOG_TITLE_UPDATE = "Редактировать пользователя";

    private static final String ITEM_TITLE_UPDATE = "Редактировать запись";
    private static final String ITEM_TITLE_DELETE = "Удалить запись";

    private static final String DIALOG_BTN_ADD = "ADD";
    private static final String DIALOG_BTN_UPDATE = "UPDATE";
    private static final String DIALOG_BTN_CANCEL = "CANCEL";

    private static final String ADD_MESSAGE = "Добавлен пользователь с id=";
    private static final String UPDATE_MESSAGE = "Пользователь изменен";

    private Cursor cursor;
    private AppDB db;
    private PersonCursorAdapter cAdapter;
    private ListView usersListView;
    private AlertDialog inputForm;
    private AlertDialog.Builder formBuilder;
    private EditText etName, etLastName, etAge, etEName, etEAge, etELastName;
    private String inputName, inputLastName;
    private Integer inputAge;
    private LayoutInflater inflater;
    private View addDialog, editDialog;
    private Toolbar toolbar;
    private AdapterView.OnItemClickListener onItemClickListener;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = initDB(this);
        initViews();
        initListView();

        //toolbar вместо actionbar
        setSupportActionBar(toolbar);
        //Контекстное меню для пунктов списка
        usersListView.setOnCreateContextMenuListener(this);
        usersListView.setOnItemClickListener(itemListerListener());
    }

    //Слушатель для элемента списка ListView
    private AdapterView.OnItemClickListener itemListerListener() {
        onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO сделать условие, если db.getUserNotes(long id) > 0, выводить список записей. Иначе выводить активити добавления записи.
                intent = new Intent(MainActivity.this, DealsList.class);
                intent.putExtra(COL_USERID, (int) id);
                startActivity(intent);
            }
        };
        return onItemClickListener;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Контекстное меню элементов listView для редактирования или удаления
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        final AdapterView.AdapterContextMenuInfo userMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final int pos = userMenuInfo.position;

        //Кнопка для редактирования пользователя
        menu.add(ITEM_TITLE_UPDATE).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (cursor.moveToPosition(pos)) {

                    editDialog = inflater.inflate(R.layout.edit_dialog, null);
                    etEName = (EditText) editDialog.findViewById(R.id.etEName);
                    etELastName = (EditText) editDialog.findViewById(R.id.etELastName);
                    etEAge = (EditText) editDialog.findViewById(R.id.etEAge);

                    etEName.setText(cursor.getString(cursor.getColumnIndex("name")));
                    etELastName.setText(cursor.getString(cursor.getColumnIndex("lastname")));
                    etEAge.setText(cursor.getString(cursor.getColumnIndex("age")));

                    formBuilder = new AlertDialog.Builder(MainActivity.this);
                    formBuilder.setTitle(DIALOG_TITLE_UPDATE);
                    formBuilder.setView(editDialog);

                    formBuilder
                            .setCancelable(true)
                            .setPositiveButton(DIALOG_BTN_UPDATE, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    inputName = etEName.getText().toString();
                                    inputLastName = etELastName.getText().toString();
                                    inputAge = Integer.parseInt(etEAge.getText().toString());

                                    long updateRecId = userMenuInfo.id;
                                    db.updateUser(updateRecId, inputName, inputLastName, inputAge);
                                    Toast.makeText(MainActivity.this, UPDATE_MESSAGE, Toast.LENGTH_SHORT).show();
                                    dialogInterface.cancel();
                                    initListView();
                                }
                            })
                            .setNegativeButton(DIALOG_BTN_CANCEL, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                    inputForm = formBuilder.create();
                    inputForm.show();
                    return true;
                } else return false;
            }
        });

        //Кнопка для удаления пользователя
        menu.add(ITEM_TITLE_DELETE).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (cursor.moveToPosition(pos)) {
                    db.deleteUser(userMenuInfo.id);
                    Toast.makeText(MainActivity.this, "Пользователь с ID = " + userMenuInfo.id + " удален", Toast.LENGTH_LONG).show();
                    initListView();
                    return true;
                } else return false;
            }
        });


    }

    //Меню для добавления пользователя в список или очистки всей таблицы БД, а также обновление списка
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addUser: {

                addDialog = inflater.inflate(R.layout.add_dialog, null);
                etName = (EditText) addDialog.findViewById(R.id.etName);
                etLastName = (EditText) addDialog.findViewById(R.id.etLastName);
                etAge = (EditText) addDialog.findViewById(R.id.etAge);

                formBuilder = new AlertDialog.Builder(this);
                formBuilder.setTitle(DIALOG_TITLE_ADD);
                formBuilder.setView(addDialog);

                formBuilder
                        .setCancelable(false)
                        .setPositiveButton(DIALOG_BTN_ADD, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                inputName = etName.getText().toString();
                                inputLastName = etLastName.getText().toString();
                                inputAge = Integer.parseInt(etAge.getText().toString());

                                long newRecId = db.addUser(inputName, inputLastName, inputAge);
                                Toast.makeText(MainActivity.this, ADD_MESSAGE + newRecId, Toast.LENGTH_SHORT).show();
                                dialogInterface.cancel();
                                initListView();
                            }
                        })
                        .setNegativeButton(DIALOG_BTN_CANCEL, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();

                            }
                        });
                inputForm = formBuilder.create();
                inputForm.show();

                break;
            }
            case R.id.clearTable: {
                db.clearTableUser();
                initListView();

                break;
            }
            case R.id.refreshList: {
                initListView();
                break;
            }
        }
        return true;
    }

    @Override
    protected void initViews() {
        usersListView = (ListView) findViewById(R.id.userListView);
        inflater = LayoutInflater.from(MainActivity.this);
        toolbar = (Toolbar) this.findViewById(R.id.my_toolbar);
    }

    @Override
    protected AppDB initDB(Context context) {
        return super.initDB(context);
    }

    @Override
    protected void initListView() {
        cursor = db.getAllUsers();
        cAdapter = new PersonCursorAdapter(MainActivity.this, cursor, 0);
        usersListView.setAdapter(cAdapter);
    }
}