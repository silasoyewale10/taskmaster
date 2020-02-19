package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);


        String taskNameView = getIntent().getStringExtra("title");
        TextView taskTextView = findViewById(R.id.taskName);
        taskTextView.setText(taskNameView);



        String taskBodyView = getIntent().getStringExtra("body");
        TextView taskDescriptionTextView = findViewById(R.id.body);
        taskDescriptionTextView.setText(taskBodyView);


        String taskStateView = getIntent().getStringExtra("state");
        TextView taskStatusTextView = findViewById(R.id.state);
        taskStatusTextView.setText(taskStateView);

    }
}
