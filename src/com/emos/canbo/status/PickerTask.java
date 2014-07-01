package com.emos.canbo.status;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.TimerTask;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;


public class PickerTask extends TimerTask {

//	public final static String url = "http://125.216.243.211:8080/huabo/response";
	
	private static String url = null;
	
	private Servlet servlet = null;
	
	/* statusMap */
	private Map<String, Map<Integer, Status>> statusMap = null;
	
	public PickerTask(Servlet servlet, Map<String, Map<Integer, Status>> statusMap) {
		this.statusMap = statusMap;
		this.servlet = servlet;
		String picker_port = servlet.getServletConfig().getInitParameter("picker_port");
		String picker_host = servlet.getServletConfig().getInitParameter("picker_host");
		String picker_path = servlet.getServletConfig().getInitParameter("picker_path");
		String picker_url = servlet.getServletConfig().getInitParameter("picker_url");
		if(servlet.getServletConfig().getInitParameter("this_host").equals("1")){
			url = "http://127.0.0.1:" 
					+ (picker_port==null?"8080":picker_port)
					+ servlet.getServletConfig().getServletContext().getContextPath()
					+ (picker_url==null?PickerConstant.URL_FOR_STATUS:picker_url);
			System.out.println(url);
		}else{
			url = "http://" 
					+ (picker_host==null?PickerConstant.HOST:picker_host) 
					+ ":" 
					+ (picker_port==null?PickerConstant.PORT:picker_port) 
					+ (picker_path==null?PickerConstant.CONTEXT_PATH:picker_path)
					+ (picker_url==null?PickerConstant.URL_FOR_STATUS:picker_url);
			System.out.println(url);
		}
	}
	
	/* 
	 * get the responser from hongjie and parse the result
	 */
	@Override
	public void run() {
		// data
		String resStr = null;
		
		/* get return string from Hongjie */
		//TODO
		try {
			resStr = HttpUtil.doPost(null, url);
			if(resStr == null){
				return;
			}else {
				resStr = resStr.trim().toUpperCase();
//				System.out.println(resStr);
			}		
			/* parse string and write into the statusMap */
			Parser.strProcess(resStr, 8, statusMap);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

}
