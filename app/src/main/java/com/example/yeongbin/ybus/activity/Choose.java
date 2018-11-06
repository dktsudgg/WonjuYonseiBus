package com.example.yeongbin.ybus.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.content.Intent;
import android.widget.TextView;

import com.example.yeongbin.ybus.R;

public class Choose extends AppCompatActivity {
    TextView hac;
    TextView sam;
    TextView sae;
    TextView schedual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        hac = (TextView) findViewById(R.id.hacguen_text);
        sam = (TextView) findViewById(R.id.samguri_text);
        sae = (TextView) findViewById(R.id.saedong_text);
        schedual= (TextView) findViewById(R.id.schedual_text);

        hac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Hacguen.class);
                startActivity(intent);
                // TextView 클릭될 시 할 코드작성
            }
        });

        sam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Samguri.class);
                startActivity(intent);
                // TextView 클릭될 시 할 코드작성
            }
        });

        sae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Saedong.class);
                startActivity(intent);
                // TextView 클릭될 시 할 코드작성
            }
        });
        schedual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ScrollingActivity.class);
                startActivity(intent);
                // TextView 클릭될 시 할 코드작성
            }
        });




    }
}
