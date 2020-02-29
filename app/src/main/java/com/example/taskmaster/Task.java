package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.amplify.generated.graphql.CreateTasksMutation;
import com.amazonaws.amplify.generated.graphql.ListTaskssQuery;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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





        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                    @Override
                    public void onResult(final UserStateDetails userStateDetails) {
                        Log.i("INIT", "onResult: " + userStateDetails.getUserState());
                        if(userStateDetails.getUserState().equals(UserState.SIGNED_OUT)){
                            AWSMobileClient.getInstance().showSignIn(Task.this, new Callback<UserStateDetails>() {
                                @Override
                                public void onResult(UserStateDetails result) {
                                    Log.d(callTheTag, "onResult: " + result.getUserState());
                                    if(result.getUserState().equals(UserState.SIGNED_IN)){


//                                        Uri selectedImage = data.getData();
//                                        ImageView imageView = findViewById(R.id.image);
//                                        imageView.setImageURI(selectedImage);
//                                        uploadWithTransferUtility(selectedImage);

                                    }
                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.e(callTheTag, "onError: ", e);
                                }
                            });

                        }
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e("INIT", "Initialization error.", e);
                    }
                }
        );




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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8080 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.image);
            imageView.setImageURI(selectedImage);
            uploadWithTransferUtility(selectedImage);


            // String picturePath contains the path of selected Image
        }
    }
    public void pickImage(View v){
        Log.d(callTheTag, "hey");

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String [] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }else{
            Intent i = new Intent(
                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, 8080);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode != 0) {
            return;
        }
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(
                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, 8080);
        }
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

    public void uploadWithTransferUtility(Uri uri) {
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(uri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance()))
                        .build();

        File file = new File(getApplicationContext().getFilesDir(), "sample.txt");  //"sample.txt"
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.append("Howdy World!");
            writer.close();
        }
        catch(Exception e) {
            Log.e(callTheTag, e.getMessage());
        }

        TransferObserver uploadObserver =
                transferUtility.upload(
                        "public/sample.txt",
                        new File(picturePath));

        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d(callTheTag, "ID:" + id + " bytesCurrent: " + bytesCurrent
                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }

        Log.d(callTheTag, "Bytes Transferred: " + uploadObserver.getBytesTransferred());
        Log.d(callTheTag, "Bytes Total: " + uploadObserver.getBytesTotal());
    }


}