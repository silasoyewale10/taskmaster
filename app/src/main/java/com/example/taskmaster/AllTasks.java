package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.amplify.generated.graphql.ListTaskssQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class AllTasks extends AppCompatActivity {
    private AWSAppSyncClient awsAppSyncClient;
    String callTheTag = "silas";
    List<Tasks> listOfTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        awsAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();
        this.listOfTasks = new ArrayList<>();
        getTasks();


        RecyclerView recyclerView = findViewById(R.id.fragment3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyTaskRecyclerViewAdapter(listOfTasks, null));


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
            for(ListTaskssQuery.Item item: response.data().listTaskss().items()){
                Tasks a = new Tasks(item.title(), item.body(), item.state());
                listOfTasks.add(a);
                // 1:49:34 of video
            };
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e(callTheTag, e.toString());
        }
    };

}