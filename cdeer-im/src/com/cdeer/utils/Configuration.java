package com.cdeer.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置方法
 * 
 * @author jacklin
 * 
 */
public class Configuration {
	private Properties propertie;
	private FileInputStream inputFile;
	private FileOutputStream outputFile;

	/**
	 * 初始化Configuration
	 */
	public Configuration() {
		propertie = new Properties();
	}

	/**
	 * 初始化Configuration
	 * 
	 * @param filePath
	 * 
	 */
	public Configuration(String filePath) {
		propertie = new Properties();
		try {
			inputFile = new FileInputStream(filePath);
			propertie.load(inputFile);
			inputFile.close();
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {
		}
	}

	/**
	 * 
	 * 
	 * @param key
	 * 
	 * @return key
	 */
	public String getValue(String key) {
		if (propertie.containsKey(key)) {
			String value = propertie.getProperty(key);//
			return value;
		} else
			return "";
	}

	/**
	 * 
	 * 
	 * @param fileName
	 * 
	 * @param key
	 * 
	 * @return
	 */
	public String getValue(String fileName, String key) {
		try {
			String value = "";
			inputFile = new FileInputStream(fileName);
			propertie.load(inputFile);
			inputFile.close();
			if (propertie.containsKey(key)) {
				value = propertie.getProperty(key);
				return value;
			} else
				return value;
		} catch (FileNotFoundException e) {
			return "";
		} catch (IOException e) {
			return "";
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 
	 */
	public void clear() {
		propertie.clear();
	}

	/**
	 * 
	 * 
	 * @param key
	 * 
	 * @param value
	 * 
	 */
	public void setValue(String key, String value) {
		propertie.setProperty(key, value);
	}

	/**
	 * 
	 * 
	 * @param fileName
	 *            文件路径+文件名称
	 * @param description
	 * 
	 */
	public void saveFile(String fileName, String description) {
		try {
			outputFile = new FileOutputStream(fileName);
			propertie.store(outputFile, description);
			outputFile.close();
		} catch (FileNotFoundException e) {

		} catch (IOException ioe) {
		}
	}
}
