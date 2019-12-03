package com.example.common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.common.audiolabel.AudioLabelView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.btn);
        AudioLabelView audioLabelView = findViewById(R.id.audioLabelView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioLabelView.newLabel("dsd","123456");
            }
        });
    }
}
