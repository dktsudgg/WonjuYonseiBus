package com.example.yeongbin.ybus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yeongbin.ybus.R;
import com.example.yeongbin.ybus.classes.ViewPagerItem;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class BusMap extends AppCompatActivity  {

    //인덱스
    List<ViewPagerItem> indexes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_image);
        Intent intent=getIntent();

        ImageView index0 = (ImageView)findViewById(R.id.imgIndex0);
        ImageView index1 = (ImageView)findViewById(R.id.imgIndex1);
        ImageView index2 = (ImageView)findViewById(R.id.imgIndex2);

        //인덱스리스트
        indexes = new ArrayList<ViewPagerItem>();
        indexes.add(new ViewPagerItem(index0, "30"));
        indexes.add(new ViewPagerItem(index1, "34"));
        indexes.add(new ViewPagerItem(index2, "31"));

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new SamplePagerAdapter());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i<indexes.size(); i++){
                    ImageView index = indexes.get(i).getImageView();
                    //현재화면의 인덱스 위치면 녹색
                    if(i == position){
                        index.setImageResource(R.drawable.ic_action_name_ok);
                    }
                    //그외
                    else{
                        index.setImageResource(R.drawable.ic_action_name);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /*
   // @Override
    public void onFlipperActionCallback(int position) {
        Log.e("onFlipperActionCallback", "---");
        for(int i=0; i<indexes.size(); i++){
            ImageView index = indexes.get(i);
            //현재화면의 인덱스 위치면 녹색
            if(i == position){
                index.setImageResource(R.drawable.ic_action_name_ok);
            }
            //그외
            else{
                index.setImageResource(R.drawable.ic_action_name);
            }
        }
    }
    */
    public class SamplePagerAdapter extends PagerAdapter {

        private final int[] sDrawables = {R.drawable.bus_30, R.drawable.bus_34, R.drawable.bus_31};

        @Override
        public int getCount() {
            return sDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            LinearLayout linearLayout = new LinearLayout(container.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            TextView pic_num = new TextView(container.getContext());
            pic_num.setGravity(Gravity.CENTER);
            pic_num.setTextSize((float)(pic_num.getTextSize() * 1.2));
            pic_num.setText(indexes.get(position).getPicnum());

            PhotoView photoView = new PhotoView(container.getContext());
            Glide.with(container.getContext()).load(sDrawables[position]).into(photoView);
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            /*photoView.setImageResource(sDrawables[position]);
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);*/

            linearLayout.addView(pic_num);
            linearLayout.addView(photoView);

            // Now just add PhotoView to ViewPager and return it
            //container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(linearLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return linearLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
