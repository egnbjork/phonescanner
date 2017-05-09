package berberyan.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Setter;

public class ConfigLoader {
	private static final Logger LOGGER = LogManager.getLogger(ConfigLoader.class); 

	private static Properties properties = new Properties();
	private static final String COUNTRY_FILE = "countrycode";
	private static final String CITY_FILE = "citycode";
	private static final String DIRECTORY_FILE = "directory";
	private static final String EXTENSION_FILE = "fileextension";

	@Setter
	private static String countryCode;
	@Setter
	private static String cityCode;
	@Setter
	private static String directory;
	@Setter
	private static String extension;

	private ConfigLoader(){}

	public static String getCountryCode() {
		if (countryCode == null) {
			loadSettings();
		}
		return countryCode;
	}

	public static String getCityCode() {
		if(cityCode == null) {
			loadSettings();
		}
		return cityCode;
	}

	public static String getDirectory () {
		if(directory == null) {
			loadSettings();
		}
		return directory;
	}

	public static String getExtension () {
		if(extension == null) {
			loadSettings();
		}
		return extension;
	}

	public static void loadSettings() {
		try (InputStream input = new FileInputStream("config.properties");){
			properties.load(input);
			countryCode = properties.getProperty(COUNTRY_FILE);
			cityCode = properties.getProperty(CITY_FILE);
			directory = properties.getProperty(DIRECTORY_FILE);
			extension = properties.getProperty(EXTENSION_FILE);
		} catch (FileNotFoundException e) {
			LOGGER.warn("config file not found, new copy will be created", e);
			createNewConfig();
		} catch (IOException e) {
			LOGGER.error("cannot read config file", e);
			LOGGER.warn("default settings will be loaded");
			loadDefaultValues();
		}
	}

	private static void createNewConfig() {
		
		try (OutputStream output = new FileOutputStream("config.properties")) {
			loadDefaultValues();
			properties.setProperty(COUNTRY_FILE, countryCode);
			properties.setProperty(CITY_FILE, cityCode);
			properties.setProperty(DIRECTORY_FILE, directory);
			properties.setProperty(EXTENSION_FILE, extension);

			// save properties to project root folder
			properties.store(output, null);
		} catch (IOException e) {
			LOGGER.error("cannot create new config file", e);
		}
	}
	
	private static void loadDefaultValues() {
		countryCode = "7";
		cityCode = "812";
		directory = ".";
		extension = ".java";
	}
}
