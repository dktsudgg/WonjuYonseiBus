package com.example.yeongbin.ybus.classes;

import com.example.yeongbin.ybus.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Random;
public class BusTimeChecker {

    public final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
    public int MAX;
	public static ArrayList<String> th1=null;
	public static String[] sCheeseStrings=null;
    public static final Random RANDOM = new Random();

	public ArrayList<BusTimeInfo> requesutData(String url, int bus_num) throws IOException{
		String connUrl = url+bus_num;

		Connection conn = Jsoup
             .connect(connUrl)
             .header("Content-Type", "application/json;charset=UTF-8")
             .userAgent(USER_AGENT)
             .method(Connection.Method.GET)
             .ignoreContentType(true);
		ArrayList<String> yArrayList = new ArrayList<String>();
		ArrayList<String> hArrayList = new ArrayList<String>();

		Document doc = conn.get();
		ArrayList<BusTimeInfo> busList = new ArrayList<BusTimeInfo>();

		MAX = doc.select("div.txt_cont02").size();    // 03, 04여도 상관없음. 레코드 길이 반환 위함.
		for (int i=0;i<MAX;i++)
		{
			//String number_bus_time=doc.select("div.txt_cont01").eq(i).text();
			String h_bus_time=doc.select("div.txt_cont02").eq(i).text();
            String y_bus_time=doc.select("div.txt_cont03").eq(i).text();
            String start_location=doc.select("div.txt_cont04").eq(i).text();


            BusTimeInfo bus = new BusTimeInfo(i+1+"");
			bus.setFrom_jangyanri(h_bus_time);
			bus.setFrom_yonsei(y_bus_time);
			bus.set_start_location(start_location);

			busList.add(bus);



		}
		return busList;

	}
	public static int getRandomCheeseDrawable() {
		switch (RANDOM.nextInt(5)) {
			default:
			case 0:
				return R.drawable.wqewqe;



		}
	}
	public static JSONObject getNextBus(ArrayList<BusTimeInfo> busList) {


		long t = System.currentTimeMillis();

		SimpleDateFormat dayTime = new SimpleDateFormat("kk:mm");

		Date currentDate = new Date(t);
		String currentTime = dayTime.format(t);
		StringTokenizer stnz = new StringTokenizer(currentTime, ":");
		int hour_current = Integer.parseInt(stnz.nextToken());
		int minutes_current = Integer.parseInt(stnz.nextToken());
        int current =100*hour_current+minutes_current;


		for(int j=0;j<busList.size();j++)
		{
		    if(busList.get(j).getFrom_yonsei().equals("-") == false){
                try {
                    StringTokenizer stnzz = new StringTokenizer(busList.get(j).getFrom_yonsei(), ":");
                    try {
                        int hour = Integer.parseInt(stnzz.nextToken());
                        int minutes = Integer.parseInt(stnzz.nextToken());
                        int time = 100 * hour + minutes;
                        if (current <= time) {
                            //다음 차 표시

                            String gone_bus_time = "";
                            // "-" 인경우  "-"가 연속일 경우를 생각하지 않음
                            if (j != 0 && busList.get(j - 1).getFrom_yonsei().equals("-")) {
                                if (j > 1)
                                    gone_bus_time = busList.get(j - 2).getFrom_yonsei();
                            } else if (j != 0)
                                gone_bus_time = busList.get(j - 1).getFrom_yonsei();
                            else
                                continue;

                            //System.out.println(busList.get(j).getFrom_yonsei());
                            int diff_result = -1;
                            //전차와 지금의 시간차를 계산하는 메소드   **1시간 이상 차이는 고려하지 않음
                            diff_result = findDiff(gone_bus_time, hour_current, minutes_current);
                            //if(diff_result<=10)
                            //System.out.println("전차는 "+diff_result+"전에 출발 했습니다.");

                            JSONObject result = new JSONObject();
                            result.put("NextBusTime", busList.get(j).getFrom_yonsei());
                            result.put("PreBusTimeDiff", diff_result);


                            return result;
                        }


                    } catch (NoSuchElementException e) {
                    }
                    //catch(NumberFormatException e)
                }catch(NumberFormatException e){

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

		}
		JSONObject result2 = new JSONObject();
		int end=0;
		String end2="0:00";
		try {
			result2.put("NextBusTime", end2);
			result2.put("PreBusTimeDiff", end);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result2;

	}


	public static int findDiff(String gone_time,int real_hour,int real_minutes) {

		int result=0;
		StringTokenizer gone_bus = new StringTokenizer(gone_time, ":");
		int gone_hour = Integer.parseInt(gone_bus.nextToken());
		int gone_minutes = Integer.parseInt(gone_bus.nextToken());


		if(gone_hour==real_hour) {
			result=real_minutes-gone_minutes;
		}
		else {
			result=real_minutes+60-gone_minutes;
		}

		return result;
	}

}
