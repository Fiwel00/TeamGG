package teamgg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import common.filehelper.ReadFile;
import errorhandling.ConsoleHelper;
import errorhandling.TeamGGException;
import errorhandling.customexception.FileNotFoundException;
import errorhandling.customexception.FilePathIsEmptyException;
import errorhandling.customexception.PropertyValueNotDefinedInConfigFileException;

public class ConfigFile {
	
	private static Integer width;
	private static Integer height;
	private static String hostDB;
	private static Properties properties;
	private static String dataBase;
	private static String apiKey;
	
	private ConfigFile() {}
	
	
	public static void setFilePath(String filePath) throws TeamGGException {
		
		InputStream inputStream = ReadFile.getFileInputStream(filePath);
		
		properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new FilePathIsEmptyException();
		}
		
	}


	/**
	 * 
	 * @return width property
	 * @throws TeamGGException 
	 */
	public static Integer getWidth() throws TeamGGException {
		if(width == null) {
			String propertyWidth = getProperty(PropertyKey.WIDTH);
			width = Integer.valueOf(propertyWidth);
		}
		return width;
	}

	/**
	 * 
	 * @return height property
	 * @throws TeamGGException 
	 */
	public static Integer getHeight() throws TeamGGException {
		if(height == null) {
			String propertyHeight = getProperty(PropertyKey.HEIGHT);
			height = Integer.valueOf(propertyHeight);
		}
		return height;
	}
	
	
	/**
	 * 
	 * @return height property
	 * @throws TeamGGException 
	 */
	public static String getHostDB() throws TeamGGException {
		if(hostDB == null) {
			hostDB = getProperty(PropertyKey.HOST_DB);
		}
		return hostDB;
	}

	/**
	 * 
	 * @param property
	 * @return specific property in
	 * @throws TeamGGException 
	 */
	private static String getProperty(PropertyKey property) throws TeamGGException {
		String propertyValue = properties.getProperty(property.toString().toLowerCase());

		checkPropertyValueIsDefined(propertyValue);
		
		ConsoleHelper.info(String.format("Getting property %s, value: %s", property, propertyValue));
		
		return propertyValue; 
	}

	/**
	 * 
	 * @param propertyValue
	 * @throws PropertyValueNotDefinedInConfigFileException
	 */
	private static void checkPropertyValueIsDefined(String propertyValue) throws PropertyValueNotDefinedInConfigFileException {
		if(propertyValue == null) {
			throw new PropertyValueNotDefinedInConfigFileException();
		}
	}

	public static String getDataBase() throws TeamGGException {
		if(dataBase == null) {
			dataBase = getProperty(PropertyKey.DATA_BASE);
		}
		return dataBase;
	}

	public static String getApiKey() throws TeamGGException {
		if(apiKey == null) {
			apiKey = getProperty(PropertyKey.API_KEY);
		}
		return apiKey;
	}

}
