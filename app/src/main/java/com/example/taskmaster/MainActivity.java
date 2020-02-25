package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amazonaws.amplify.generated.graphql.ListTaskssQuery;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class MainActivity extends AppCompatActivity {
    private AWSAppSyncClient awsAppSyncClient;
    String callTheTag = "silas";

    List<Tasks> listOfTasks = new ArrayList<>();
    Context context;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        awsAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();
        this.listOfTasks = new ArrayList<>();

        recyclerView = findViewById(R.id.fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MyTaskRecyclerViewAdapter(listOfTasks, null));

        context = this.getApplicationContext();

//        RecyclerView recyclerView = findViewById(R.id.fragment);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.setAdapter(new MyTaskRecyclerViewAdapter(listOfTasks, null));


        final Button goToAddTaskPage = findViewById(R.id.button3); //go to add taks page
        goToAddTaskPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAddTask = new Intent(MainActivity.this, Task.class);
                MainActivity.this.startActivity(goToAddTask);
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


        Button goToSettingsPage = findViewById(R.id.button2);
        goToSettingsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSettingsPage = new Intent(MainActivity.this, Settings.class);
                MainActivity.this.startActivity((goToSettingsPage));
            }
        });




        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("INIT", "onResult: " + userStateDetails.getUserState());
                        if(userStateDetails.getUserState().equals(UserState.SIGNED_OUT)){
                            AWSMobileClient.getInstance().showSignIn(MainActivity.this, new Callback<UserStateDetails>() {
                                @Override
                                public void onResult(UserStateDetails result) {
                                    Log.d(callTheTag, "onResult: " + result.getUserState());
                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.e(callTheTag, "onError: ", e);
                                }
                            });
//AWSMobileClient.getInstance().signOut();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("INIT", "Initialization error.", e);
                    }
                }
        );


    }

    @Override
    protected  void onResume(){
        super.onResume();
        TextView tt = findViewById(R.id.textView4);

        getTasks();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String chosenUserName = sp.getString("userName", "default");
        tt.setText(chosenUserName + "'s tasks");
        tt.setVisibility(View.VISIBLE);

        }



    public void getTasks(){
        awsAppSyncClient.query(ListTaskssQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(tasksCallback);
    }

    private GraphQLCall.Callback<ListTaskssQuery.Data> tasksCallback = new GraphQLCall.Callback<ListTaskssQuery.Data>() {
        @Override
        public void onResponse(@Nonnull Response<ListTaskssQuery.Data> response) {
            Log.i(callTheTag, response.data().listTaskss().items().toString());
            listOfTasks.clear();
            for(ListTaskssQuery.Item item: response.data().listTaskss().items()){
                Tasks a = new Tasks(item.title(), item.body(), item.state());
                Log.i("quang", item.title());
                listOfTasks.add(a);

                // 1:49:34 of video
            };

            Handler handlerMainThread = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message inputMessage) {
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            };
            handlerMainThread.obtainMessage().sendToTarget();
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e(callTheTag, e.toString());
        }
    };
}
