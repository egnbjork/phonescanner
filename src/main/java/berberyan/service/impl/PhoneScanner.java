package berberyan.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import berberyan.config.ConfigLoader;
import berberyan.service.Parser;

@Component
public class PhoneScanner implements Parser {
	private static final String REGEX = "^\\+?(\\d)?\\s?\\-?\\(?(\\d{3})?\\)?\\s?\\-?(\\d{3})\\-?(\\d{2}\\-?\\d{2})$";
	private static final Pattern pattern = Pattern.compile(REGEX);
	
	//extracts possible phone entries from the string
	@Override
	public List<String> extractData(String entry) {
		String[] phones = entry.split("[^\\d\\s\\(\\)\\-]+");

		return Arrays.asList(phones).stream()
				.map(PhoneScanner::getPhone)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	//checks if phone entry is valid
	//if it is, returns well-formed phone
	public static String getPhone(String entry) {

		String cleanedEntry = entry.replaceAll("[^\\d\\+\\(\\)\\s]", "").trim();
		Matcher m = pattern.matcher(cleanedEntry);
		
		String defaultCountryCode = ConfigLoader.getCountryCode();
		String defaultCityCode = ConfigLoader.getCityCode();

		if(m.find()) {
			String country = (m.group(1) == null) ? defaultCountryCode : m.group(1).trim();
			String city = (m.group(2) == null) ? defaultCityCode : m.group(2).trim();
			String firstPart = m.group(3).trim();
			String lastPart = m.group(4).trim();
			//		+7 (812) 123-4567
			return "+" + country + " (" + city + ") " + firstPart + "-" + lastPart;
		}
		
		return null;
	}

	//checks if regex is working
	//mainly for testing
	public boolean isGood(String entry) {
		return Pattern.matches(REGEX, entry);
	}
}
