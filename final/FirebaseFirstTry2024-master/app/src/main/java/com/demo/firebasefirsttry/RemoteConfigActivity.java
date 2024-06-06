package com.demo.firebasefirsttry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class RemoteConfigActivity extends AppCompatActivity {

    private TextView mText;
    private String TAG = "RemoteConfigActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Firebase Remote Config");
        setContentView(R.layout.activity_remote_config);

        mText = findViewById(R.id.textView);

        FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build();
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    boolean updated = task.getResult();
                    Log.d(TAG, "Config params updated: " + updated);
                    Toast.makeText(RemoteConfigActivity.this, "Fetch and activate succeeded",
                            Toast.LENGTH_SHORT).show();
                    useDifferentVersion();
                } else {
                    Toast.makeText(RemoteConfigActivity.this, "Fetch failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void useDifferentVersion() {
        boolean isUseNewVersion = FirebaseRemoteConfig.getInstance().getBoolean("use_new_version");
        if (isUseNewVersion) {
            mText.setText("New Version!!");
        } else {
            mText.setText("Old Version!!");
        }
    }
}