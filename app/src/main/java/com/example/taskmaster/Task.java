package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;

public class Task extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);


        Button showLabel = findViewById(R.id.button); // captures the particular button i want to click
        showLabel.setOnClickListener(new View.OnClickListener() { //listens to when it is clicked to execute this function.
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence confirmation = "Successfully Submitted";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, confirmation, duration);
                toast.show();
                toast.setGravity(Gravity.TOP | Gravity.LEFT,  500,850);

                Tasks xx = new Tasks("title","body", "state");
                EditText title = findViewById(R.id.editText);
                String titleText = title.getText().toString();

                EditText description = findViewById(R.id.editText2);
                String descriptionText = description.getText().toString();

                Tasks yy = new Tasks (titleText, descriptionText, "new");

                MyDatabase myDb;

                AWSAppSyncClient awsAppSyncClient;

                awsAppSyncClient = AWSAppSyncClient.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                        .build();



                myDb = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "add_task").allowMainThreadQueries().build();
                myDb.taskToDatabase().save(yy);

                Intent backToMain = new Intent(Task.this, MainActivity.class);
                Task.this.startActivity(backToMain);
            }
        });

    }

    public void runMutation(){
        CreateTasksInput createTasksInput = CreateTasksInput.builder().
                .title
    }
}
