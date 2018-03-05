package com.dm.tool;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块：message
 */
public class LogFormater {

	public static String start(String message) {
		return String.format("START - %s", message);
	}

	public static String end(String message) {
		return String.format("END - %s", message);
	}

	public static String error(String message) {
		return String.format("ERROR - %s", message);
	}
}
