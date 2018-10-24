package com.example.yeongbin.ybus;


import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.*;
public class ListFragment extends Fragment {
    //Handler mHandler = null;
    public ArrayList<String> bustime=null;
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
        bustime=bundle.getStringArrayList("BusTime");
        nextbus=bundle.getString("NextBusTime");
        busNum=bundle.getInt("BusNumber");

        Log.e("nextbus value : ", nextbus+"");
        for (String string : bustime) {
            if(string.matches(nextbus)){
                where=bustime.indexOf(string);
                Log.e("Where value : ", where+"");
            }
        }
        setupRecyclerView(rv);

        timeThread a= new timeThread();
        a.start();

        rv.getLayoutManager().scrollToPosition(where);
        return rv;
    }
    public class timeThread extends Thread
    {
        Handler mHandler = new Handler();

        public void run() {
            while (true) {

                try {
                    JSONObject result = BusTimeChecker.getNextBus(ScrollingActivity.ainfo.get(""+busNum));
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
        private List<String> mValues;
        private int index_nextbus=0;

        private static final int FOOTER_VIEW = 1;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            //public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                //mImageView = view.findViewById(R.id.avatar);
                mTextView = view.findViewById(R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public class FooterViewHolder extends ViewHolder {

            public FooterViewHolder(View view) {
                super(view);
            }

        }

        public SimpleStringRecyclerViewAdapter(Context context, List<String> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);

            mBackground = mTypedValue.resourceId;
            mValues = items;
           // index_nextbus=index;
        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if(viewType == FOOTER_VIEW){
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                view.setBackgroundResource(mBackground);

                TextView tv = view.findViewById(R.id.text1);

                ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
                layoutParams.height = layoutParams.height * 8;
                tv.requestLayout();

                return new FooterViewHolder(view);
            }

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            if(holder instanceof FooterViewHolder){
                return ;
            }

            holder.mBoundString = mValues.get(position);
            holder.mTextView.setText(mValues.get(position));
           // System.out.println(mValues.get(position).toString()+" "+nextbus);
            String temp=mValues.get(position).toString();

            if (temp.equals(nextbus))
            {
                holder.mTextView.setTextColor(Color.RED);
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
