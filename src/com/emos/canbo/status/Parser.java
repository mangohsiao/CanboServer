package com.emos.canbo.status;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Parser {

//	/********************** FOR TEST *************************/
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Map<String, Map<Integer, Integer>> map = new HashMap<String, Map<Integer,Integer>>();
//		String str = "0705AA010705CCDD0705EEFF";
//		String str2 = "0705AA000705CC060705EE02";
//		String str3 = "0701AA000701CC060706EE02";
//		try {
//			strProcess(str, 8, map);
//			strProcess(str2, 8, map);
//			strProcess(str3, 8, map);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(map.get("0705").size());
//		
//		printMapMap(System.out, map);
//	}
//	
//	public static void printMapMap(PrintStream out, Map<String, Map<Integer, Integer>> map) {
//		Map<Integer, Integer> mm = null;
//		Iterator it0 = map.keySet().iterator();
//		while(it0.hasNext()){
//			String mac = (String)it0.next();
//			out.println(mac);
//			mm = map.get(mac);
//			Iterator it = map.get(mac).keySet().iterator();
//			while(it.hasNext()){
//				int key = (int)it.next();
//				out.println("key=" + Integer.toHexString(key) + " value=" + mm.get(key));
//			}
//		}
//	}
//	/********************** FOR TEST *************************/
	

	/**
	 * @param out
	 * @param map
	 */
	public static void printMapMap(PrintWriter out, Map<String, Map<Integer, Integer>> map) {
		Map<Integer, Integer> mm = null;
		Iterator it0 = map.keySet().iterator();
		while(it0.hasNext()){
			String mac = (String)it0.next();
			out.println(mac);
			mm = map.get(mac);
			Iterator it = map.get(mac).keySet().iterator();
			while(it.hasNext()){
				int key = (int)it.next();
				out.println("key=" + Integer.toHexString(key) + " value=" + mm.get(key));
			}
		}
	}
	
	/**
	 * @param out
	 * @param map
	 */
	public static void printStatusMap(PrintWriter out, Map<String, Map<Integer, Integer>> map) {
		Map<Integer, Integer> mm = null;
		Iterator it0 = map.keySet().iterator();
		while(it0.hasNext()){
			String mac = (String)it0.next();
			out.println(mac);
			mm = map.get(mac);
			Iterator it = map.get(mac).keySet().iterator();
			while(it.hasNext()){
				int key = (int)it.next();
				out.println("key=" + Integer.toHexString(key) + " value=" + mm.get(key));
			}
		}
	}
	
	/**
	 * @param inStr
	 * @param subLen
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static boolean strProcess(String inStr, int subLen, Map<String, Map<Integer, Status>> map) throws Exception {
		/* length not match */
		if(inStr.length()%subLen != 0){
			throw new Exception("length error.");
		}
		
		for (int i = 0, index = 0; i < inStr.length() / subLen; i++, index += subLen) {
			/* processing the substring here*/
//			System.out.println(inStr.substring(index, index + subLen));
			
			String macAndType = inStr.substring(index, index + 4);
			int no = Integer.parseInt(inStr.substring(index + 4, index + 6), 16);
			int status = Integer.parseInt(inStr.substring(index + 6, index + 8), 16);
			
			Map<Integer, Status> noAndStatusMap = map.get(macAndType);
			if(noAndStatusMap == null){
				noAndStatusMap = new HashMap<Integer, Status>();
				map.put(macAndType, noAndStatusMap);
			}
			noAndStatusMap.put(no, new Status(status, System.currentTimeMillis()));
		}
		return true;
	}
}
