package me.berberyan.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhoneScanner {
	private static final Logger LOGGER = LogManager.getLogger(PhoneScanner.class); 
	private static final String regex = "^\\+?(\\d)?\\s?\\-?\\(?(\\d{3})?\\)?\\s?\\-?(\\d{3})\\-?(\\d{2}\\-?\\d{2})$";
	private static final Pattern pattern = Pattern.compile(regex);
	private final String defaultCountryCode = "7";
	private final String defaultCityCode = "812";

	public boolean isGood(String entry) {
		return Pattern.matches(regex, entry);
	}

	public String extractPhone(String entry) {
		LOGGER.debug("raw entry is " + entry);
		entry = entry.replaceAll("[^\\d\\+\\(\\)\\s]", "").trim();
		LOGGER.debug("clean entry is " + entry);
		Matcher m = pattern.matcher(entry);
		String country;
		String city;
		String firstPart;
		String lastPart;
		if(m.find()) {
			country = (m.group(1) == null) ? defaultCountryCode : m.group(1).trim();
			city = (m.group(2) == null) ? defaultCityCode : m.group(2).trim();
			firstPart = m.group(3).trim();
			lastPart = m.group(4).trim();
			LOGGER.debug(m.group(1) + " raw country code");
			LOGGER.debug	(country + " country code");
			LOGGER.debug(m.group(2) + " raw city code");
			LOGGER.debug(city + " city code");
			LOGGER.debug(m.group(3) + " raw first part");
			LOGGER.debug(firstPart + " first part");
			LOGGER.debug(lastPart + " raw last part");
			//		+7 (812) 123-4567
			LOGGER.debug("+" + country + " (" + city + ") " + firstPart + "-" + lastPart);
			return ("+" + country + " (" + city + ") " + firstPart + "-" + lastPart);
		}

		return null;
	}
}
