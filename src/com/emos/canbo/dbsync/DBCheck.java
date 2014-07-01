package com.emos.canbo.dbsync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class DBCheck
 */
//@WebServlet("/DBCheck")
public class DBCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int NO_NEED_SYNC = 202;
	private static final int NEED_TO_SYNC = 200;

	public static final int TYPE_CHECK = 101;
	public static final int TYPE_DOWN = 911;
	
	private String db_path = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBCheck() {
        super();   
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

		//parsing json.
		String nowMD5 = null;
		int TYPE;
		String reqStr = readStrToUpper(request);
		System.out.println(reqStr);
		try {
			JSONObject reqJson = new JSONObject(reqStr);
			TYPE = reqJson.getInt("TYPE");
			switch (TYPE) {
			case TYPE_CHECK:
				nowMD5 = reqJson.getString("NOWMD5");	
				handleCheck(request,response, nowMD5);
				break;
				
			case TYPE_DOWN:
				handleDownload(request,response);
				break;

			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}

	private void handleDownload(HttpServletRequest request,
			HttpServletResponse response) {

		//check file
		File file = new File(db_path);
		if(!file.exists()){
			System.out.println("file not exists - " + file.getPath());
			return;
		}
		try {
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment;filename=\""
		              + file.getName() + "\"");
			int file_len = (int)file.length();
			response.setContentLength(file_len);

			if(file_len != 0){
				InputStream in = new FileInputStream(file);
				byte[] buf = new byte[4096];
				ServletOutputStream servOs = response.getOutputStream();
				int readLength;
				while((readLength = in.read(buf)) != -1){
					servOs.write(buf, 0, readLength);
				}
				in.close();
				servOs.flush();
				servOs.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleCheck(HttpServletRequest request,
			HttpServletResponse response, String nowMD5) throws IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();	
		JSONObject resJson = new JSONObject();
	
		//check file
		File file = new File(db_path);
		if(!file.exists()){
			System.out.println("file not exists - " + file.getPath());
			return;
		}
		String fileSum = MD5Util.getFileMD5(file);
		if(nowMD5 != null && nowMD5.toUpperCase(Locale.US).equals(fileSum.toUpperCase(Locale.US))){
			/* no need update */
			try {
				resJson.put("RES", NO_NEED_SYNC);
				out.println(resJson.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			/* need to update */
			try {
				resJson.put("RES", NEED_TO_SYNC);
				resJson.put("MD5", fileSum.toUpperCase(Locale.US));
				resJson.put("SIZE", file.length());			
				out.println(resJson.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		out.flush();
		out.close();
		return;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		System.out.println("dbcheck init.");
		db_path = config.getInitParameter("DB_PATH");
		System.out.println(db_path);
	}

	private String readStrToUpper(HttpServletRequest request) {
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
}
