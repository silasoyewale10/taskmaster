package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button goToAddTaskPage = findViewById(R.id.button3); //go to add taks page
        goToAddTaskPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAddTask = new Intent(MainActivity.this, Task.class);
                MainActivity.this.startActivity(goToAddTask);

//                Tasks
            }

        });
        Button goToAllTasksPage = findViewById(R.id.button4);
        goToAllTasksPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAllTasks = new Intent(MainActivity.this, AllTasks.class);
                MainActivity.this.startActivity((gotoAllTasks));
            }
        });



// items in radio group and their ids.  Just in case i need my buttons back.

         // Hygiene   hygieneRadioButton
        //socialHangOut  socialHangOutRadioButton
        //javaPractice  javaPracticeRadioButton

//        RadioGroup tasksRadioGroup = findViewById(R.id.tasksRadioGroup);
//        tasksRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                String choiceOfTask = "default";
//                if(checkedId == R.id.hygieneRadioButton){
//                    choiceOfTask = "hygiene";
//                    Intent gotoTaskDetailPage = new Intent(MainActivity.this, TaskDetail.class);
//                    gotoTaskDetailPage.putExtra("taskName", "hygiene");
//
//                    MainActivity.this.startActivity((gotoTaskDetailPage));
//
//
//                } else if(checkedId == R.id.javaPracticeRadioButton){
//                    choiceOfTask = "javaPractice";
//                    Intent gotoTaskDetailPage = new Intent(MainActivity.this, TaskDetail.class);
//                    gotoTaskDetailPage.putExtra("taskName", "javaPractice");
//
//                    MainActivity.this.startActivity((gotoTaskDetailPage));
//                }else if(checkedId == R.id.socialHangOutRadioButton){
//                    choiceOfTask = "socialHangOut";
//                    Intent gotoTaskDetailPage = new Intent(MainActivity.this, TaskDetail.class);
//                    gotoTaskDetailPage.putExtra("taskName", "socialHangOut");
//
//                    MainActivity.this.startActivity((gotoTaskDetailPage));
//                }
//                SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                SharedPreferences.Editor editor = p.edit();
//                editor.putString("task", choiceOfTask);
//                editor.apply();
//            }
//
//        });
//



        Button goToSettingsPage = findViewById(R.id.button2);
        goToSettingsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSettingsPage = new Intent(MainActivity.this, Settings.class);
                MainActivity.this.startActivity((goToSettingsPage));
            }
        });



    }

    @Override
    protected  void onResume(){
        super.onResume();
        TextView tt = findViewById(R.id.textView4);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String chosenUserName = sp.getString("userName", "default");
        tt.setText(chosenUserName + "'s tasks");
        tt.setVisibility(View.VISIBLE);

        }
//    }
}
