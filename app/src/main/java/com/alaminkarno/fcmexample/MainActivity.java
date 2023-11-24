package com.alaminkarno.fcmexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alaminkarno.fcmexample.utils.AppConstant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private Button notificationBTN;
    private TextView tokenTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        notificationBTN = findViewById(R.id.sendNotificationBTN);
        tokenTV = findViewById(R.id.fcmTokenTV);

        createNotificationChannel();

        getFCMToken();

        notificationBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });


    }

    void getFCMToken(){

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    Log.d("FCM",task.getResult());
                    tokenTV.setText(task.getResult());


                    Toast.makeText(MainActivity.this, "Token Found!!!", Toast.LENGTH_SHORT).show();

                }
                else {
                    Log.d("FCM",task.getException().toString());
                    tokenTV.setText(task.getException().toString());
                }
            }
        });

    }

    void showNotification(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,AppConstant.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Notification")
                .setContentText("This is our test notification")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1,builder.build());

    }

    void createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(AppConstant.CHANNEL_ID,AppConstant.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(AppConstant.CHANNEL_DESCRIPTION);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

    }
}