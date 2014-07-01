package com.emos.canbo.status;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
	public static JSONObject mapToJson(Map<String, Map<Integer, Status>> map, long cmpTime){
		JSONObject ret = new JSONObject();
		try {
			Map<Integer, Status> mm = null;
			Iterator it0 = map.keySet().iterator();
			while(it0.hasNext()){
				String mac = (String)it0.next();
				mm = map.get(mac);
				JSONObject obj = new JSONObject();
				Iterator it1 = mm.keySet().iterator();
				while(it1.hasNext()){
					int key = (Integer)it1.next();
					Status status = mm.get(key);
					if(cmpTime - status.time < PickerConstant.TIME_HOLDING){
						obj.put(Integer.toString(key), status.state);
					}
				}
				if(obj.length() != 0){
					ret.put(mac, obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return ret;
	}
	
	public static JSONObject getJsonFromMap(Map<String, Map<Integer, Status>> map, String key, long nowTime) throws JSONException {
		Map<Integer, Status> temp = map.get(key);
		if(temp != null){
			JSONObject ret = new JSONObject();
			Iterator it0 = temp.keySet().iterator();
			while(it0.hasNext()){
				int tmpKey = (Integer)it0.next();
				Status status = temp.get(tmpKey);
				if(nowTime - status.time < PickerConstant.TIME_HOLDING){
					ret.put(Integer.toString(tmpKey), status.state);
				}
			}
			if(ret.length() != 0){
				return ret;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
}
