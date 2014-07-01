package com.emos.canbo.status;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class Status {

	public int state;
	public long time;
//	public int type;
	
	public Status(int state, long time) {
		this.state = state;
		this.time = time;
	}

	@Override
	public String toString() {
		return Integer.toString(state) + "@" + Long.toOctalString(time);
	}
	
	public static void main(String[] args) {

//		Map<String, Map<Integer, Status>> bigmap = new HashMap<String, Map<Integer,Status>>();
//		Map<Integer, Status> map = new HashMap<Integer, Status>();
//		map.put(80, new Status(80, System.currentTimeMillis()/1000));
//		map.put(80, new Status(81, System.currentTimeMillis()/1000));
//		bigmap.put("hello", map);
//		JSONObject json = JsonUtil.mapToJson(bigmap);
//		System.out.print(json.toString());
//		System.out.print(new Status(81, System.currentTimeMillis()/1000).toString());
		
//		System.out.println(new Status(88,System.currentTimeMillis()/1000).toString().split("@")[0]);
	}
	
}
