package com.example.androidtodolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DiaryAdapter.OnItemClicked {
    RecyclerView recyclerviewUser;
    AppDatabase db;
    DiaryAdapter diaryAdapter;
    public static List<Diary> Tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        recyclerviewUser = findViewById(R.id.recyclerview_id);
        recyclerviewUser.setLayoutManager(new LinearLayoutManager((this)));
        recyclerviewUser.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final Button btn_Add = findViewById(R.id.btn_ADD);

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddScreen();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getandDisplayTask();
    }

    @SuppressLint("StaticFieldLeak")
    public void getandDisplayTask() {
        new AsyncTask<Void, Void, List<Diary>>() {
            @Override
            protected List<Diary> doInBackground(Void... voids) {
                Tasks = db.diaryDao().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        diaryAdapter = new DiaryAdapter(this, Tasks);
                        diaryAdapter.setOnClick(MainActivity.this);
                        recyclerviewUser.setAdapter(diaryAdapter);
                    }
                });
                return null;
            }
        }.execute();
    }

    private void openAddScreen() {
        Intent intent = new Intent(MainActivity.this, AddDiaryActivity.class);
        startActivity(intent);
    }

    private void openUpdateScreen(Diary diary) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        intent.putExtra("id", diary.getId());
        intent.putExtra("task", diary.getTitle());
        intent.putExtra("content", diary.getContent());
        intent.putExtra("date", diary.getDatetime());

        startActivity(intent);
    }

    @Override
    public void onClickItemUpdate(int position) {
        openUpdateScreen(Tasks.get(position));
    }

    @Override
    public void onClickItemDelete(final int position) {
        Log.i("TAG", "clicked at " + position);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.diaryDao().delete(Tasks.get(position));
                Log.i("TAG", "delete success");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                diaryAdapter.Tasks.remove(position);
                diaryAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}






