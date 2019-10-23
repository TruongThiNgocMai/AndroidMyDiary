package com.example.androidtodolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {

    AppDatabase db;
    EditText editTitle;
    EditText editContent;
    EditText editDate;
    Button btnUpdate;
    int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        @SuppressLint("WrongViewCast") Button btn = (Button) findViewById(R.id.btnBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final Button btn_Add_Date = (Button) findViewById(R.id.btn_Date);

        btn_Add_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        editTitle = findViewById(R.id.edit_text_Title);
        editContent = findViewById(R.id.edit_text_Content);
        editDate = findViewById(R.id.edit_text_Date);

        btnUpdate = findViewById(R.id.btn_Update);

        int id = getIntent().getIntExtra("id", 0);
        taskId = id;
        String title = getIntent().getStringExtra("task");
        String content = getIntent().getStringExtra("content");
        String date = getIntent().getStringExtra("date");

        editTitle.setText(title);
        editContent.setText(content);
        editDate.setText(date);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTodoToDatabase();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void updateTodoToDatabase() {
        final String taskTitle = editTitle.getText().toString();
        final String taskContent = editContent.getText().toString();
        final String taskDate = editDate.getText().toString();


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Diary newDiary = new Diary();
                newDiary.setTitle(taskTitle);
                newDiary.setContent(taskContent);
                newDiary.setDatetime(taskDate);
                newDiary.setId(taskId);
                db.diaryDao().updateOne(newDiary);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                showSuccessDialog();
            }
        }.execute();
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage("Update Success")
                .setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
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
