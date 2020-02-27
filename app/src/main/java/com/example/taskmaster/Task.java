package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.amplify.generated.graphql.CreateTasksMutation;
import com.amazonaws.amplify.generated.graphql.ListTaskssQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.List;

import javax.annotation.Nonnull;

import type.CreateTasksInput;

public class Task extends AppCompatActivity {
    private AWSAppSyncClient awsAppSyncClient;
    String callTheTag = "silas";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);



        awsAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

//        getTasks();

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

                runTasksCreateMutation(titleText,descriptionText, "new");


            }
        });

    }

    public void runTasksCreateMutation(String title, String body, String state ){
        CreateTasksInput createTasksInput = CreateTasksInput.builder()
                .title(title)
                .body(body)
                .state(state)
                .build();
        awsAppSyncClient.mutate(CreateTasksMutation.builder().input(createTasksInput).build())
                .enqueue(addMutattionCallback);
    }
    private GraphQLCall.Callback<CreateTasksMutation.Data> addMutattionCallback = new GraphQLCall.Callback<CreateTasksMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateTasksMutation.Data> response) {
            Log.i(callTheTag, "Added Tasks");
            Intent backToMain = new Intent(Task.this, MainActivity.class);
            Task.this.startActivity(backToMain);
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e(callTheTag, e.toString());
        }
    };

}