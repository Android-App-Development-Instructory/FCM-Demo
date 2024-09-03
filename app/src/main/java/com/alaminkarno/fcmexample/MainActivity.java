package com.alaminkarno.fcmexample;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.alaminkarno.fcmexample.helpers.NotificationHelper;
import com.alaminkarno.fcmexample.utils.AppConstant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextView tokenTV;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        Button notificationBTN = findViewById(R.id.sendNotificationBTN);
        tokenTV = findViewById(R.id.fcmTokenTV);
        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("UserList");

        createNotificationChannel();

        getFCMToken();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationBTN.setOnClickListener(view -> showNotification());
        }

    }

    void getFCMToken() {

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("FCM", task.getResult());
                tokenTV.setText(task.getResult());

                saveTokenInToDatabase(task.getResult());

                Toast.makeText(MainActivity.this, "Token Found!!!", Toast.LENGTH_SHORT).show();

            } else {
                Log.d("FCM", Objects.requireNonNull(task.getException()).toString());
                tokenTV.setText(task.getException().toString());
            }
        });

    }

    private void saveTokenInToDatabase(String fcm) {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
         DatabaseReference fcmRef = userRef.child(currentUser.getUid()).child("fcmToken");
         fcmRef.setValue(fcm).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if(task.isSuccessful()){
                     Toast.makeText(MainActivity.this, "FCM UPDATED!!!", Toast.LENGTH_SHORT).show();
                 }
             }
         });
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    void showNotification() {

        NotificationHelper.displayNotification(this,"Hello","How are You ?");
    }

    void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(AppConstant.CHANNEL_ID,AppConstant.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(AppConstant.CHANNEL_DESCRIPTION);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

    }
}