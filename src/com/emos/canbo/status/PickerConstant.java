package com.emos.canbo.status;

public class PickerConstant {
//	public final static String URL_FOR_STATUS = "/test";
	public final static String URL_FOR_STATUS = "/response";

	/* for status request */
	public final static boolean IN_SAME_CONTEXT_PATH = true;	//when set 'true', HOST=127.0.0.1 && contentPath is this
	
	//external request
	public final static String HOST = "125.216.243.211";	//available when IN_SAME_CONTEXT_PATH = true
	public final static String CONTEXT_PATH = "/huabo";	//available when IN_SAME_CONTEXT_PATH = true	
	public final static int PORT = 8080;
	
	public final static int TIME_HOLDING = 20 * 1000;	//s

	public final static String REQ_ARRAY_KEY = "MAC_DEV";	
	public final static String REQ_TYPE_KEY = "TYPE";
}
