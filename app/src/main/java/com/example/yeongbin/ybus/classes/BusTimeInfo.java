package com.example.yeongbin.ybus.classes;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class BusTimeInfo implements Parcelable {
	private String bus_seq;
	private String from_jangyanri;
	private String from_yonsei;
	private String start_location;
	
	public BusTimeInfo(String s) {
		bus_seq=s;
	}

	protected BusTimeInfo(Parcel in) {
		bus_seq = in.readString();
		from_jangyanri = in.readString();
		from_yonsei = in.readString();
		start_location = in.readString();
	}

	public void Bus_h_bus(String s) {
		from_jangyanri =s;
	}
	public void Bus_y_bus(String s) {
		from_yonsei =s;
	}
	
	public String getBus_seq() {
		return bus_seq;
	}

	public String getFrom_jangyanri() {
		return from_jangyanri;
	}

	public void setFrom_jangyanri(String from_jangyanri) {
		this.from_jangyanri = from_jangyanri;
	}

	public String getFrom_yonsei() {
		return from_yonsei;
	}

	public void setFrom_yonsei(String from_yonsei) {
		this.from_yonsei = from_yonsei;
	}

	public String get_start_location() {
		return start_location;
	}

	public void set_start_location(String start_location) {
		this.start_location = start_location;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return toJsonString();
	}
	
	public String toJsonString() {
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("bus_seq", bus_seq);
			jsonObject.put("from_jangyanri", from_jangyanri);
			jsonObject.put("from_yonsei", from_yonsei);
			return jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	// Parcelable code..

	public static final Creator<BusTimeInfo> CREATOR = new Creator<BusTimeInfo>() {
		@Override
		public BusTimeInfo createFromParcel(Parcel in) {
			return new BusTimeInfo(in);
		}

		@Override
		public BusTimeInfo[] newArray(int size) {
			return new BusTimeInfo[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(bus_seq);
		parcel.writeString(from_jangyanri);
		parcel.writeString(from_yonsei);
		parcel.writeString(start_location);
	}
}
