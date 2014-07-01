package com.emos.canbo.status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class CmdPicker
 */
public class CmdPicker extends HttpServlet {

	public static final int TYPE_FULL = 10;
	public static final int TYPE_CUSTOM = 11;
	public static final int TYPE_STATUS = 12;
	
	
	private static final long serialVersionUID = 1L;
	
	Timer timer = null;
	Map<String, Map<Integer, Status>> statusMap = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CmdPicker() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String reqStr = readStr(request);
//		System.out.println(reqStr);

		JSONObject reqJson = null;
		JSONObject responseJson = null;
		try {
			reqJson = new JSONObject(reqStr);
			int requestType = reqJson.getInt(PickerConstant.REQ_TYPE_KEY);
			responseJson = processRequest(requestType, reqJson);
		} catch (JSONException e) {
			e.printStackTrace();
			out.print(e.getMessage().toString());
			out.close();
			return;
		}
		if(responseJson == null){
			out.print("response josn is null");
			out.close();
			return;
		}
		out.print(responseJson.toString());
		out.close();
		
		/* for test */
//		JSONObject json = JsonUtil.mapToJson(statusMap);
//		try {
//			json.put("mapSize", statusMap.size());
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		out.print(json.toString());
//		out.flush();
//		out.close();
		/* for test */
				
	}

	private JSONObject processRequest(int requestType, JSONObject reqJson) throws JSONException {
		JSONObject responseJson = null;
		switch (requestType) {
		case TYPE_FULL:
			responseJson = JsonUtil.mapToJson(statusMap, System.currentTimeMillis());
			break;
		case TYPE_CUSTOM:
			long nowTime = System.currentTimeMillis();
			JSONArray array = reqJson.getJSONArray(PickerConstant.REQ_ARRAY_KEY);
			responseJson = new JSONObject();
			for (int i = 0; i < array.length(); i++) {
				String macKey = array.getString(i);
				JSONObject temp = JsonUtil.getJsonFromMap(statusMap, macKey, nowTime);
				if(temp != null){
					responseJson.put(macKey, temp);		
				}else{
					//do nothing
				}
			}
			break;
		case TYPE_STATUS:
			
			break;
		default:
			break;
		}
		return responseJson;
	}

	private String readStr(HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		try {
			BufferedReader reader = request.getReader();
			String line = null;
			while((line = reader.readLine()) != null){
				str.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString().toUpperCase();
	}

	@Override
	public void init() throws ServletException {		
//		System.out.println("init()...");
		
		/* initial map */
		statusMap = new HashMap<String, Map<Integer,Status>>();
		/* set timer */
		timer = new Timer();
		timer.schedule(new PickerTask(this, statusMap), 0, 3000);
	}

	@Override
	public void destroy() {
		super.destroy();
		timer.cancel();
	}
	
}
