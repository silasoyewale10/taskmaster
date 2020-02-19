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

public class Task extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);



//        Button showSubmittedlabel = findViewById(R.id.button); // captures the particular button i want to click
//        showSubmittedlabel.setOnClickListener(new View.OnClickListener() { //listens to when it is clicked to execute this function.
//            @Override
//            public void onClick(View v) {
//                TextView submitButton = findViewById(R.id.button);  //captures the buttons id and makes it a text object.
//                submitButton.setText("Successfully Submitted Task");  //sets the text to succes ...
//                submitButton.setVisibility(View.VISIBLE);     //mkaes it shown.
//
//            }
//        }); // performs callback function when this button is clicked.



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
//                toast.setMargin(50,10);

                Tasks xx = new Tasks("title","body", "state");
                EditText title = findViewById(R.id.editText);
                String titleText = title.getText().toString();

                EditText description = findViewById(R.id.editText2);
                String descriptionText = description.getText().toString();

                Tasks yy = new Tasks (titleText, descriptionText, "new");

                MyDatabase myDb;
                myDb = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "add_task").allowMainThreadQueries().build();
                myDb.taskToDatabase().save(yy);

                Intent backToMain = new Intent(Task.this, MainActivity.class);
                Task.this.startActivity(backToMain);
            }
        });

    }
}
