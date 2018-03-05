package com.dm.tool;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块：系统监听
 */
public class PropUtil {
	/**
	 * 系统全局配置信息Properties
	 */
	private static Properties propSysGlobalCfg = new Properties();

	/**
	 * 系统消息Properties
	 */
	private static Properties propSysMessage = new Properties();

	/**
	 * 读取系统全局配置信息
	 */
	public void readPropFile(Properties prop, String propFilePath)
			throws URISyntaxException, IOException {
		FileInputStream fis = null;
		try {
			String path = (getClass().getClassLoader().getResource("").toURI())
					.getPath();
			fis = new FileInputStream(path + propFilePath);
			prop.load(fis);
		} finally {
			if (fis != null) {
				fis.close();
				fis = null;
			}
		}
	}

	/**
	 * 根据属性key，读取value
	 *
	 * @param prop
	 * @param propKey
	 * @return
	 */
	public static String getPropVal(Properties prop, String propKey) {
		return prop.getProperty(propKey);
	}

	public static Properties getPropSysGlobalCfg() {
		return propSysGlobalCfg;
	}

	public static Properties getPropSysMessage() {
		return propSysMessage;
	}

}
