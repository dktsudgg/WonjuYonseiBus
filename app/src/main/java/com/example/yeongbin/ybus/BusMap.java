package com.example.yeongbin.ybus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

public class BusMap extends AppCompatActivity {


    ViewFlipper flipper;
    //인덱스
    List<ImageView> indexes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_image);
        Intent intent=getIntent();
        flipper = (ViewFlipper)findViewById(R.id.flipper);
        ImageView index0 = (ImageView)findViewById(R.id.t0_1);
        ImageView index1 = (ImageView)findViewById(R.id.t0_2);
        ImageView index2 = (ImageView)findViewById(R.id.t4_1);
        ImageView index3 = (ImageView)findViewById(R.id.t4_2);
        ImageView index4 = (ImageView)findViewById(R.id.t1_1);
        ImageView index5 = (ImageView)findViewById(R.id.t1_2);


        //인덱스리스트
        indexes = new ArrayList<>();
        indexes.add(index0);
        indexes.add(index1);
        indexes.add(index2);

        //xml을 inflate 하여 flipper view에 추가하기
        //inflate
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.bus30_1, flipper, false);
        View view2 = inflater.inflate(R.layout.bus30_2, flipper, false);
        View view3 = inflater.inflate(R.layout.bus34_1, flipper, false);
        View view4 = inflater.inflate(R.layout.bus34_2, flipper, false);
        View view5 = inflater.inflate(R.layout.bus31_1, flipper, false);
        View view6 = inflater.inflate(R.layout.bus31_2, flipper, false);
        //inflate 한 view 추가
        flipper.addView(view1);
        flipper.addView(view2);
        flipper.addView(view3);

        //리스너설정 - 좌우 터치시 화면넘어가기
        flipper.setOnTouchListener(new ViewFlipperAction(this, flipper));


    }
    @Override
    public void onFlipperActionCallback(int position) {
        for(int i=0; i<indexes.size(); i++){
            ImageView index = indexes.get(i);
            //현재화면의 인덱스 위치면 녹색
            if(i == position){
                index.setImageResource(R.drawable.green);
            }
            //그외
            else{
                index.setImageResource(R.drawable.white);
            }
        }
    }


}
