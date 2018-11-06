package com.example.yeongbin.ybus.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yeongbin.ybus.classes.BusTimeChecker;
import com.example.yeongbin.ybus.classes.BusTimeInfo;
import com.example.yeongbin.ybus.R;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;


public class ScrollingActivity extends AppCompatActivity  {


    public static int bus_number=0;
    public JSONObject result =null;
    public String nextBusTime=null;
    public int preBusTimeDiff=0;
    public static final String EXTRA_NAME = "bus_time";
    public Bundle bundle_30 = new Bundle();
    public Bundle bundle_31 = new Bundle();
    public Bundle bundle_34 = new Bundle();

    public static HashMap<String, ArrayList<BusTimeInfo>> ainfo = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        //Intent intent = getIntent();
       // final String cheeseName = intent.getStringExtra(EXTRA_NAME);
        try {
            Thread th = new Thread() {
                public void run() {




                    for(int x=0;x<3;x++)
                    {
                        //ArrayList<String> time = new ArrayList<>();
                        ArrayList<BusTimeInfo> busTimeInfos = new ArrayList<>();
                        if(x==0)
                        bus_number=30;
                        else  if (x==1)
                            bus_number=34;
                        else if (x==2)
                            bus_number=31;

                        try {
                            BusTimeChecker a = new BusTimeChecker();
                            //ArrayList<BusTimeInfo> busTimeInfos;//ainfo.get(bus_number+"");
                            busTimeInfos = a.requesutData("http://its.wonju.go.kr/busroute/selectCityScheduleView.do?rn=", bus_number);
                            ainfo.put(""+bus_number, busTimeInfos);

                            System.out.println(bus_number);
                            result = a.getNextBus(busTimeInfos);
                            nextBusTime = result.get("NextBusTime").toString();
                            preBusTimeDiff = (int) result.get("PreBusTimeDiff");
                            System.out.println(nextBusTime+" 루프 도냐");

                            /*try {

                                for (int j = 0; j < BusTimeChecker.MAX; j++) {


                                    StringTokenizer stnzz = new StringTokenizer(busTimeInfos.get(j).getFrom_yonsei(), ":");
                                    String hour = stnzz.nextToken();
                                    String minutes = stnzz.nextToken();

                                    time.add(hour + ":" + minutes);
                                    if (hour==""){
                                        break;
                                    }
                                }

                                //catch(NumberFormatException e) {}
                            } catch (Exception e) {
                            }*/

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //BusTimeChecker.th1 = time;

                        if(x==0){

                            bundle_30.putParcelableArrayList("BusTime",busTimeInfos);
                            bundle_30.putString("NextBusTime",nextBusTime);
                            bundle_30.putInt("PrevBusTime",preBusTimeDiff);
                            bundle_30.putInt("BusNumber", 30);
                        }
                        else if(x==1){

                            bundle_34.putParcelableArrayList("BusTime",busTimeInfos);
                            bundle_34.putString("NextBusTime",nextBusTime);
                            bundle_34.putInt("PrevBusTime",preBusTimeDiff);
                            bundle_34.putInt("BusNumber", 34);
                        }
                        else if(x==2) {

                            bundle_31.putParcelableArrayList("BusTime",busTimeInfos);
                            bundle_31.putString("NextBusTime", nextBusTime);
                            bundle_31.putInt("PrevBusTime", preBusTimeDiff);
                            bundle_31.putInt("BusNumber", 31);
                        }
                    }

                }

            };
            th.start();
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" "); // cheeseName

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        loadBackdrop();
        FrameLayout frame = findViewById(R.id.frame);
        ImageView imageView = findViewById(R.id.backdrop);

        imageView.setOnClickListener(new MyListener());

        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Frame", "---");
            }
        });

    }

    private void setupViewPager(final ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(2);
        final Adapter adapter = new Adapter(getSupportFragmentManager());







        Bus30Fragment busStop30 = new Bus30Fragment();
        Bus34Fragment busStop34 = new Bus34Fragment();
        Bus31Fragment busStop31 = new Bus31Fragment();



        busStop30.setArguments(bundle_30);
        busStop34.setArguments(bundle_34);
        busStop31.setArguments(bundle_31);

        adapter.addFragment(busStop30, "30번");
        adapter.addFragment(busStop34, "34번");
        adapter.addFragment(busStop31, "31번");

        viewPager.setAdapter(adapter);

    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    private void loadBackdrop() {
        final ImageView imageView = findViewById(R.id.backdrop);
        Glide.with(this).load(BusTimeChecker.getRandomCheeseDrawable()).apply(RequestOptions.centerCropTransform()).into(imageView);
    }
    class MyListener implements View.OnClickListener{
                    @Override
                    public void onClick(View v) {
                        Log.e("MyListener", "---");
                        Intent intent = new Intent(getApplicationContext(),Choose.class);
                        startActivity(intent);
                    }
                }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
