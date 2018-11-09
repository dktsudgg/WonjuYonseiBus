package com.example.yeongbin.ybus.activity;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yeongbin.ybus.R;
import com.example.yeongbin.ybus.classes.BusTimeChecker;
import com.example.yeongbin.ybus.classes.BusTimeInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
//학교에서 출발과  그외 데이터를 나눠야함 비고부분 (연세대 그외만 만들면되 31번은 그외출 34번은 연세대출 그외출 30번은 연세대출
public class Bus31Fragment extends Fragment {
    //Handler mHandler = null;
    public ArrayList<BusTimeInfo> bustime=null;
    public String nextbus=null;
    public int where=0;
    public int busNum;

    SimpleStringRecyclerViewAdapter simpleStringRecyclerViewAdapter;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_list, container, false);

        Bundle bundle = getArguments();
        bustime=bundle.getParcelableArrayList("BusTime");

        bustime=get_31bus(bustime);
        nextbus=bundle.getString("NextBusTime");
        busNum=bundle.getInt("BusNumber");

        Log.e("nextbus value : ", nextbus+"");
        for (BusTimeInfo bus : bustime) {
            if(bus.getFrom_yonsei().matches(nextbus)){
                where=bustime.indexOf(bus);
                Log.e("Where value : ", where+"");
            }
        }
        setupRecyclerView(rv);
        timeThread a= new timeThread();
        a.start();
        rv.getLayoutManager().scrollToPosition(where);
        return rv;
    }
    public ArrayList<BusTimeInfo> get_31bus(ArrayList<BusTimeInfo> busList){
        long t = System.currentTimeMillis();
        Date currentDate = new Date(t);
        SimpleDateFormat dateTime = new SimpleDateFormat("E");



        String currentTime = dateTime.format(t);
        System.out.println("-"+currentTime+"-");
        if (currentTime.equals("월") ||currentTime.equals("화")||currentTime.equals("수")||currentTime.equals("목")||currentTime.equals("금"))
        {
            for(int i=0;i<28;i++){
                try {
                    busList.remove(0);
                }
                catch (IndexOutOfBoundsException e){
                    break;
                }
        }
        }
        else
        {
            for(int i=0;i<28;i++){
                try {
                    busList.remove(28);
                }
                catch(IndexOutOfBoundsException e){
                    break;
                }
            }
        }
        return busList;
    }
    public class timeThread extends Thread
    {
        Handler mHandler = new Handler();

        public void run() {
            while (true) {

                try {
                    JSONObject result = BusTimeChecker.getNextBus(bustime);

                    nextbus = result.get("NextBusTime").toString();
                    // Log.e("timeThread n: ", nextbus);

                    if(simpleStringRecyclerViewAdapter != null)
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                simpleStringRecyclerViewAdapter.notifyDataSetChanged();
                            }
                        });

                    Thread.sleep(20000);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        simpleStringRecyclerViewAdapter = new SimpleStringRecyclerViewAdapter(getActivity(),
                //  getRandomSublist(Cheeses.sCheeseStrings, 30)));//치즈를 시간대로 변경

                bustime);
        recyclerView.setAdapter(simpleStringRecyclerViewAdapter);//치즈를 시간대로 변경

        //recyclerView.

    }

 /*   private List<String> getSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        //Random random = new Random();
        for (int i=0;i<BusTimeChecker.MAX;i++) {
            list.add(array[i]);
        }
        return list;
    }*/

    public class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<BusTimeInfo> mValues;
        private int index_nextbus=0;

        private static final int FOOTER_VIEW = 1;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            //public final ImageView mImageView;
            public final TextView mTextView;
            public final TextView mTextView2;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                //mImageView = view.findViewById(R.id.avatar);
                mTextView = view.findViewById(R.id.text1);
                mTextView2 = view.findViewById(R.id.text2);
            }

        }

        public class FooterViewHolder extends SimpleStringRecyclerViewAdapter.ViewHolder {

            public FooterViewHolder(View view) {
                super(view);
            }

        }

        public SimpleStringRecyclerViewAdapter(Context context, List<BusTimeInfo> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);

            mBackground = mTypedValue.resourceId;
            mValues = items;
            // index_nextbus=index;
        }

        @Override
        public SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            // Footer인경우.
            if(viewType == FOOTER_VIEW){
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                view.setBackgroundResource(mBackground);

                TextView tv = view.findViewById(R.id.text1);

                ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
                layoutParams.height = layoutParams.height * 7;
                tv.requestLayout();

                return new SimpleStringRecyclerViewAdapter.FooterViewHolder(view);
            }

            // 기존 item
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final SimpleStringRecyclerViewAdapter.ViewHolder holder, int position) {

            // Footer일경우는 아무 설정 안하고 종료
            if(holder instanceof SimpleStringRecyclerViewAdapter.FooterViewHolder){
                return ;
            }

            // 비고 (출발지)
            Log.e("get_start_location", mValues.get(position).get_start_location());
            holder.mTextView2.setText(mValues.get(position).get_start_location());

            holder.mBoundString = mValues.get(position).getFrom_yonsei();

            // 출발시간
            holder.mTextView.setText(mValues.get(position).getFrom_yonsei());
            // System.out.println(mValues.get(position).toString()+" "+nextbus);
            String temp=mValues.get(position).getFrom_yonsei().toString();

            if (temp.equals(nextbus))
            {
                holder.mTextView.setTextColor(Color.RED);
               // System.out.println(position+" "+where);
                where=position;
            }
            if (!temp.equals(nextbus))
            {
                holder.mTextView.setTextColor(Color.BLACK);
            }


/*
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ScrollingActivity.class);
                    intent.putExtra(ScrollingActivity.EXTRA_NAME, holder.mBoundString);

                    context.startActivity(intent);
                }
            });*/

           /* RequestOptions options = new RequestOptions();
            Glide.with(holder.mImageView.getContext())
                    .load(BusTimeChecker.getRandomCheeseDrawable())
                    .apply(options.fitCenter())
                    .into(holder.mImageView);*/
        }


        /*@Override
        public int getItemCount() {
            return mValues.size();
        }*/

        @Override
        public int getItemViewType(int position) {
            if (position == mValues.size()) {
                // This is where we'll add footer.
                return FOOTER_VIEW;
            }

            return super.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            if (mValues == null) {
                return 0;
            }

            if (mValues.size() == 0) {
                //Return 1 here to show nothing
                return 1;
            }

            // Add extra view to show the footer view
            return mValues.size() + 1;
        }
    }
}
