package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button settingButton = findViewById(R.id.button5);

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = findViewById(R.id.editText4); //usernmae field on the settings page

                String inputText = input.getText().toString();
                //find out the username and save it to shared prefs.
                SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                p.edit();
                SharedPreferences.Editor editor = p.edit();
                editor.putString("userName", inputText);
                editor.apply();
            }
        });
    }


}
