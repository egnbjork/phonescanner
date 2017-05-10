package berberyan.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import berberyan.service.impl.PhoneScanner;

public class PhoneScannerTest {
	private static final Logger LOGGER = LogManager.getLogger(PhoneScannerTest.class); 
	PhoneScanner ps;
	String expected;

	@Before
	public void setUp() {
		ps = new PhoneScanner();
		expected = "+7 (812) 123-4567";
	}

	//	+7 812 номер
	//	123-4567
	@Test
	public void findPhone_clean_spacesDashes_true() {
		String phone = "+7 812 111-4567";

		boolean actual = ps.isGood(phone);

		assertTrue(actual);
	}

	//	+7 812 номер
	//	123-45-67
	@Test
	public void findPhone_clean_spacesManyDashes_true() {
		String phone = "+7 812 987-65-43";

		boolean actual = ps.isGood(phone);

		assertTrue(actual);
	}
	
	//	+7 812 номер
	//	1234567
	@Test
	public void findPhone_clean_spacesNoDashes_true() {
		String phone = "+7 812 1234567";

		boolean actual = ps.isGood(phone);

		assertTrue(actual);
	}

	//	+7 (495) номер
	//	1234567
	@Test
	public void findPhone_clean_bracesNoDashes_true() {
		String phone = "+7 (812) 1234567";

		boolean actual = ps.isGood(phone);

		assertTrue(actual);
	}

	//	+7812номер
	@Test
	public void findPhone_clean_noBracesNoSpaces_true() {
		String phone = "+7(812)1234567";

		boolean actual = ps.isGood(phone);

		assertTrue(actual);
	}

	//	+7812 номер
	@Test
	public void findPhone_clean_spaceBeforeNumber_true() {
		String phone = "+7812 1234567";

		boolean actual = ps.isGood(phone);

		assertTrue(actual);
	}

	//	7-812-номер
	@Test
	public void findPhone_clean_dashesBeforeNumberNoPlusSign_true() {
		String phone = "7-812-1234567";

		boolean actual = ps.isGood(phone);

		assertTrue(actual);
	}

	//	(812) номер
	@Test
	public void findPhone_clean_cityNumberBraces_true() {
		String phone = "(812)1234567";

		boolean actual = ps.isGood(phone);

		assertTrue(actual);
	}

	//	812номер
	@Test
	public void findPhone_clean_cityNumberWithNothing_true() {
		String phone = "8121234567";

		boolean actual = ps.isGood(phone);

		assertTrue(actual);
	}

	//	812 номер
	@Test
	public void findPhone_clean_cityNumberWithSpace_true() {
		String phone = "812 1234567";

		boolean actual = ps.isGood(phone);

		assertTrue(actual);
	}

	//	095-номер
	@Test
	public void findPhone_clean_cityNumberWithDash_true() {
		String phone = "812-1234567";

		boolean actual = ps.isGood(phone);

		assertTrue(actual);
	}

	//	123-4567
	@Test
	public void findPhone_clean_shortNumberWithDash_true() {
		String phone = "123-4567";

		boolean actual = ps.isGood(phone);

		assertTrue(actual);
	}

	//	123-45-67
	@Test
	public void findPhone_clean_shortNumberWithMultipleDashes_true() {
		String phone = "123-45-67";

		boolean actual = ps.isGood(phone);

		assertTrue(actual);
	}

	//	1234567
	@Test
	public void findPhone_clean_shortNumberWithNothingMore_true() {
		String phone = "1234567";

		boolean actual = ps.isGood(phone);

		assertTrue(actual);
	}

	//	+7 812 номер
	//	123-45-67
	@Test
	public void findPhone_dirty_spacesManyDashes_false() {
		String phone = "87+7 812 123-45-67!";

		boolean actual = ps.isGood(phone);

		assertFalse(actual);
	}

	//	+7 812 номер
	//	1234567
	@Test
	public void findPhone_dirty_spacesNoDashes_false() {
		String phone = ",+7 812 1234567mmm";

		boolean actual = ps.isGood(phone);

		assertFalse(actual);
	}

	//	+7 (495) номер
	//	1234567
	@Test
	public void findPhone_dirty_bracesNoDashes_false() {
		String phone = "0+7 (812) 1234567w";

		boolean actual = ps.isGood(phone);

		assertFalse(actual);
	}

	//	+7812номер
	@Test
	public void findPhone_dirty_noBracesNoSpaces_false() {
		String phone = "+7(812)123456732jkj";

		boolean actual = ps.isGood(phone);

		assertFalse(actual);
	}

	//	+7812 номер
	@Test
	public void findPhone_dirty_spaceBeforeNumber_false() {
		String phone = "$$+7812 1304567";

		boolean actual = ps.isGood(phone);

		assertFalse(actual);
	}
	@Test
	public void findPhone_clean_spacesDashesBraces_true() {
		String phone = "+7 (555) 123-4567";

		List<String> getPhoneListExpected = new ArrayList<>();
		getPhoneListExpected.add(phone);
		
		String phoneTwo = "boo is+7 (555) 123-4567sss";
		List<String> getPhoneListActual = ps.extractData(phoneTwo);
		assertEquals(getPhoneListExpected, getPhoneListActual);
	}

	@Test
	public void getPhone_spacesDashes() {
		String rawNumber = "С трехзначным кодом города+7 812 123-4567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);

		assertEquals(expected, actual);
	}

	@Test
	public void extractPhone_spacesDashes_bad() {
		String rawNumber = "С трехзначным кодом города+7 12 123-4567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);
		expected = null;

		assertEquals(expected, actual);
	}

	@Test
	public void findPhone_clean_spacesManyDashesQuotes() {
		String phone = "\"+7 812 987-65-43\"";

		String actual = PhoneScanner.getPhone(phone);
		expected = "+7 (812) 987-6543";
		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_spacesManyDashes() {
		String rawNumber = "С трехзначным кодом города+7 812 987-65-43С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);
		expected = "+7 (812) 987-6543";
		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_spacesManyDashes_bad() {
		String rawNumber = "С трехзначным кодом города+7 812 12A45-67С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);
		expected = null;

		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_spacesNoDashes() {
		String rawNumber = "С трехзначным кодом города+7 812 1234567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);

		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_spacesNoDashes_tooManyNumbers_bad() {
		String rawNumber = "С трехзначным кодом города+7 33812 1234567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);
		expected = null;
		LOGGER.debug(actual + " expected null");
		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_bracesNoDashes() {
		String rawNumber = "С трехзначным кодом города+7 (812) 1234567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);

		assertEquals(expected, actual);
	}

	@Test
	public void extractPhone_bracesNoDashes_bad() {
		String rawNumber = "С трехзначным кодом города+7 82) 1234567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);
		expected = null;

		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_noBracesNoSpaces() {
		String rawNumber = "С трехзначным кодом города+7(812)1234567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);

		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_noBracesNoSpaces_bad() {
		String rawNumber = "С трехзначным кодом города+7(812)123454367С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);
		expected = null;

		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_spaceBeforeNumber() {
		String rawNumber = "С трехзначным кодом города+7812 1234567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);

		assertEquals(expected, actual);
	}

	@Test
	public void extractPhone_spaceBeforeNumber_symbolInsideNumber_good() {
		String rawNumber = "С трехзначным кодом города+7812 c1234567С трехзначным кодом города";

		List<String> actualList = ps.extractData(rawNumber);
		List<String> expectedList = Arrays.asList(expected);

		assertEquals(expectedList, actualList);
	}
	
	@Test
	public void extractPhone_spaceBeforeNumber_symbolInsideNumber_TwoNumbersOneString_good() {
		String rawNumber = "С c8888888Стрехзначным кодом города+7812 c1234567С трехзначным кодом города";

		List<String> actualList = ps.extractData(rawNumber);
		List<String> expectedList = Arrays.asList("+7 (812) 888-8888", expected);

		assertEquals(expectedList, actualList);
	}

	@Test
	public void extractPhone_spaceBeforeNumber_manySymbolsInsideNumber_good() {
		String rawNumber = "С трехзначным кодом города+7812 casd1234567С трехзначным кодом города";

		List<String> actualList = ps.extractData(rawNumber);
		List<String> expectedList = Arrays.asList(expected);

		assertEquals(expectedList, actualList);
	}

	@Test
	public void getPhone_dashesBeforeNumberNoPlusSign() {
		String rawNumber = "С трехзначным кодом города7-812-1234567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);

		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_dashesBeforeNumberNoPlusSign_bad() {
		String rawNumber = "С трехзначным кодом города7-812-1232324567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);
		expected = null;

		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_cityNumberBraces() {
		String rawNumber = "С трехзначным кодом города(812)1234567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);
		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_cityNumberWithNothing() {
		String rawNumber = "С трехзначным кодом города8121234567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);

		assertEquals(expected, actual);
	}

	@Test
	public void extractPhone_cityNumberWithSpace() {
		String rawNumber = "С трехзначным кодом города812 1234567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);

		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_cityNumberWithDash() {
		String rawNumber = "С трехзначным кодом города812-1234567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);

		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_shortNumberWithDash() {
		String rawNumber = "С трехзначным кодом города123-4567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);

		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_shortNumberWithMultipleDashes() {
		String rawNumber = "С трехзначным кодом города123-45-67С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);

		assertEquals(expected, actual);
	}

	@Test
	public void getPhone_shortNumberWithNothingMore() {
		String rawNumber = "С трехзначным кодом города1234567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);

		assertEquals(expected, actual);
	}

	@Test
	public void findPhone_dirty_spacesDashes_true() {
		String phone = "aa+7 812 123-4567&&&";

		boolean actual = ps.isGood(phone);

		assertFalse(actual);
	}

	@Test
	public void getPhone_shortNumberWithNothingMore_bad() {
		String rawNumber = "С трехзначным кодом города234567С трехзначным кодом города";

		String actual = PhoneScanner.getPhone(rawNumber);
		String expected = null;

		assertEquals(expected, actual);
	}

	@Test
	public void findPhone_cleanNumberMissing_false() {
		String phone = "+7 812 123-467";

		boolean actual = ps.isGood(phone);

		assertFalse(actual);
	}

	@Test
	public void findPhone_dirtyNumberMissing_false() {
		String phone = "aa+7 812 123-4567&&&";

		boolean actual = ps.isGood(phone);

		assertFalse(actual);
	}
}
