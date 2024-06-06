package com.demo.firebasefirsttry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RealtimeDatabaseActivity extends AppCompatActivity {

    private TextView mContent;
    private EditText mInputContent;
    private Button mSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Firebase Realtime Database");
        setContentView(R.layout.activity_realtime_database);

        mContent = findViewById(R.id.content);
        mInputContent = findViewById(R.id.input);
        mSend = findViewById(R.id.send);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputContent = mInputContent.getEditableText().toString();
                if (TextUtils.isEmpty(inputContent)) {
                    return;
                }
                useRealtimeDatabase(inputContent);
                mInputContent.setText("");
            }
        });

        getRealtimeValue();
    }

    private void getRealtimeValue() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-demo-42197-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference ref = database.getReference("messages");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    return;
                }
                String newValue = snapshot.getValue(String.class);
                mContent.setText(newValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RealtimeDatabaseActivity.this, error.toException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void useRealtimeDatabase(String content) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-demo-42197-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference ref = database.getReference("messages");
        ref.setValue(content);
    }
}