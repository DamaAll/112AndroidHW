package com.example.checkbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CheckBox chk1, chk2, chk3, chk4;
    ImageView output1, output2, output3, output4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化各個元件
        chk1 = findViewById(R.id.chk1);
        chk2 = findViewById(R.id.chk2);
        chk3 = findViewById(R.id.chk3);
        chk4 = findViewById(R.id.chk4);

        output1 = findViewById(R.id.output1);
        output2 = findViewById(R.id.output2);
        output3 = findViewById(R.id.output3);
        output4 = findViewById(R.id.output4);

        // 監聽勾選狀態的變化
        chk1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    output1.setVisibility(ImageView.VISIBLE);
                } else {
                    output1.setVisibility(ImageView.INVISIBLE);
                }
            }
        });

        chk2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    output2.setVisibility(ImageView.VISIBLE);
                } else {
                    output2.setVisibility(ImageView.INVISIBLE);
                }
            }
        });

        chk3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    output3.setVisibility(ImageView.VISIBLE);
                } else {
                    output3.setVisibility(ImageView.INVISIBLE);
                }
            }
        });

        chk4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    output4.setVisibility(ImageView.VISIBLE);
                } else {
                    output4.setVisibility(ImageView.INVISIBLE);
                }
            }
        });
    }
}