package com.demo.firebasefirsttry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Class clazz = null;
            if (view.getId() == R.id.login) {
                clazz = AuthenticationActivity.class;
            } else if (view.getId() == R.id.realtime_database) {
                clazz = RealtimeDatabaseActivity.class;
            } else if (view.getId() == R.id.crashlytics) {
                clazz = CrashlyticsActivity.class;
            } else if (view.getId() == R.id.remote_config) {
                clazz = RemoteConfigActivity.class;
            } else if (view.getId() == R.id.storage) {
                clazz = StorageActivity.class;
            }

            if (clazz != null) {
                Intent intent = new Intent(MainActivity.this, clazz);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.login).setOnClickListener(mOnClickListener);
        findViewById(R.id.realtime_database).setOnClickListener(mOnClickListener);
        findViewById(R.id.crashlytics).setOnClickListener(mOnClickListener);
        findViewById(R.id.remote_config).setOnClickListener(mOnClickListener);
        findViewById(R.id.storage).setOnClickListener(mOnClickListener);
    }
}