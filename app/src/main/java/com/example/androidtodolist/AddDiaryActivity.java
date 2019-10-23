package com.example.androidtodolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddDiaryActivity extends AppCompatActivity {
    AppDatabase db;
    String taskTitle;
    String taskContent;
    String taskDate;
    EditText editTitle;
    EditText editContent;
    EditText editDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        @SuppressLint("WrongViewCast") Button btn = (Button) findViewById(R.id.btnBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDiaryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        final Button btn_Add_Task = (Button) findViewById(R.id.btn_AddTask);

        btn_Add_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
                finish();
            }
        });

        final Button btn_Add_Date = (Button) findViewById(R.id.btn_Date);

        btn_Add_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
    }



    public void addTask() {
        editTitle = findViewById(R.id.edit_text_Title);
        editContent = findViewById(R.id.edit_text_Content);
        editDate = findViewById(R.id.edit_text_Date);

        taskTitle = editTitle.getText().toString();
        taskContent = editContent.getText().toString();
        taskDate = editDate.getText().toString();

        if (taskTitle.isEmpty()) {
            Toast.makeText(this, "Title must not null", Toast.LENGTH_SHORT).show();
            return;
        }

        if (taskContent.isEmpty()) {
            Toast.makeText(this, "Content must not null", Toast.LENGTH_SHORT).show();
            return;
        }

        if (taskDate.isEmpty()) {
            Toast.makeText(this, "Date must not null", Toast.LENGTH_SHORT).show();
            return;
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Diary diary = new Diary();
                diary.setTitle(taskTitle);
                diary.setContent(taskContent);
                diary.setDatetime(taskDate);
                db.diaryDao().insert(diary);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(AddDiaryActivity.this, taskTitle + " has been added successfully", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    private void showDatePicker(){
        DatePickerDialog date = new DatePickerDialog(this , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                @SuppressLint("WrongViewCast") EditText editDate = findViewById(R.id.edit_text_Date);
                editDate.setText(i+" "+(i1+1)+" "+i2);
            }
        }, 2019, 01, 01);
        date.show();
    }

}
