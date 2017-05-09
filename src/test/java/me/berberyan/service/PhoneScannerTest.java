package me.berberyan.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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
		String phone = "+7 812 123-4567";
		
		boolean actual = ps.isGood(phone);
		
		assertTrue(actual);
	}
	
	@Test
	public void extractpPhone_spacesDashes() {
		String rawNumber = "С трехзначным кодом города+7 812 123-4567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void extractPhone_spacesDashes_bad() {
		String rawNumber = "С трехзначным кодом города+7 12 123-4567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		expected = null;
		
		assertEquals(expected, actual);
	}
	
//	+7 812 номер
//	123-45-67
	@Test
	public void findPhone_clean_spacesManyDashes_true() {
		String phone = "+7 812 123-45-67";
		
		boolean actual = ps.isGood(phone);
		
		assertTrue(actual);
	}
	
	@Test
	public void extractpPhone_spacesManyDashes() {
		String rawNumber = "С трехзначным кодом города+7 812 123-45-67С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void extractpPhone_spacesManyDashes_bad() {
		String rawNumber = "С трехзначным кодом города+7 812 12A45-67С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		expected = null;
		
		assertEquals(expected, actual);
	}
	
//	+7 812 номер
//	1234567
	@Test
	public void findPhone_clean_spacesNoDashes_true() {
		String phone = "+7 812 1234567";
		
		boolean actual = ps.isGood(phone);
		
		assertTrue(actual);
	}
	
	@Test
	public void extractpPhone_spacesNoDashes() {
		String rawNumber = "С трехзначным кодом города+7 812 1234567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void extractpPhone_spacesNoDashes_tooManyNumbers_bad() {
		String rawNumber = "С трехзначным кодом города+7 33812 1234567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		expected = null;
		LOGGER.debug(actual + " expected null");
		assertEquals(expected, actual);
	}
	
//	+7 (495) номер
//	1234567
	@Test
	public void findPhone_clean_bracesNoDashes_true() {
		String phone = "+7 (812) 1234567";
		
		boolean actual = ps.isGood(phone);
		
		assertTrue(actual);
	}
	
	@Test
	public void extractpPhone_bracesNoDashes() {
		String rawNumber = "С трехзначным кодом города+7 (812) 1234567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void extractPhone_bracesNoDashes_bad() {
		String rawNumber = "С трехзначным кодом города+7 82) 1234567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		expected = null;

		assertEquals(expected, actual);
	}
	
//	+7812номер
	@Test
	public void findPhone_clean_noBracesNoSpaces_true() {
		String phone = "+7(812)1234567";
		
		boolean actual = ps.isGood(phone);
		
		assertTrue(actual);
	}
	
	@Test
	public void extractpPhone_noBracesNoSpaces() {
		String rawNumber = "С трехзначным кодом города+7(812)1234567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void extractpPhone_noBracesNoSpaces_bad() {
		String rawNumber = "С трехзначным кодом города+7(812)123454367С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		expected = null;

		assertEquals(expected, actual);
	}
	
//	+7812 номер
	@Test
	public void findPhone_clean_spaceBeforeNumber_true() {
		String phone = "+7812 1234567";
		
		boolean actual = ps.isGood(phone);
		
		assertTrue(actual);
	}
	
	@Test
	public void extractpPhone_spaceBeforeNumber() {
		String rawNumber = "С трехзначным кодом города+7812 1234567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		
		assertEquals(expected, actual);
	}
	
	@Ignore
	@Test
	public void extractpPhone_spaceBeforeNumber_bad() {
		String rawNumber = "С трехзначным кодом города+7812 c1234567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		expected = null;

		assertEquals(expected, actual);
	}
	
//	7-812-номер
	@Test
	public void findPhone_clean_dashesBeforeNumberNoPlusSign_true() {
		String phone = "7-812-1234567";
		
		boolean actual = ps.isGood(phone);
		
		assertTrue(actual);
	}
	
	@Test
	public void extractpPhone_dashesBeforeNumberNoPlusSign() {
		String rawNumber = "С трехзначным кодом города7-812-1234567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void extractpPhone_dashesBeforeNumberNoPlusSign_bad() {
		String rawNumber = "С трехзначным кодом города7-812-1232324567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		expected = null;
		
		assertEquals(expected, actual);
	}
	
//	(812) номер
	@Test
	public void findPhone_clean_cityNumberBraces_true() {
		String phone = "(812)1234567";
		
		boolean actual = ps.isGood(phone);
		
		assertTrue(actual);
	}
	
	@Test
	public void extractpPhone_cityNumberBraces() {
		String rawNumber = "С трехзначным кодом города(812)1234567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		assertEquals(expected, actual);
	}
	
//	812номер
	@Test
	public void findPhone_clean_cityNumberWithNothing_true() {
		String phone = "8121234567";
		
		boolean actual = ps.isGood(phone);
		
		assertTrue(actual);
	}
	
	@Test
	public void extractpPhone_cityNumberWithNothing() {
		String rawNumber = "С трехзначным кодом города8121234567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		
		assertEquals(expected, actual);
	}
	
//	812 номер
	@Test
	public void findPhone_clean_cityNumberWithSpace_true() {
		String phone = "812 1234567";
		
		boolean actual = ps.isGood(phone);
		
		assertTrue(actual);
	}
	
	@Test
	public void extractPhone_cityNumberWithSpace() {
		String rawNumber = "С трехзначным кодом города812 1234567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		
		assertEquals(expected, actual);
	}
	
//	095-номер
	@Test
	public void findPhone_clean_cityNumberWithDash_true() {
		String phone = "812-1234567";
		
		boolean actual = ps.isGood(phone);
		
		assertTrue(actual);
	}
	
	@Test
	public void extractpPhone_cityNumberWithDash() {
		String rawNumber = "С трехзначным кодом города812-1234567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		
		assertEquals(expected, actual);
	}
	
//	123-4567
	@Test
	public void findPhone_clean_shortNumberWithDash_true() {
		String phone = "123-4567";
		
		boolean actual = ps.isGood(phone);
		
		assertTrue(actual);
	}

	@Test
	public void extractpPhone_shortNumberWithDash() {
		String rawNumber = "С трехзначным кодом города123-4567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		
		assertEquals(expected, actual);
	}
	
//	123-45-67
	@Test
	public void findPhone_clean_shortNumberWithMultipleDashes_true() {
		String phone = "123-45-67";
		
		boolean actual = ps.isGood(phone);
		
		assertTrue(actual);
	}
	
	@Test
	public void extractpPhone_shortNumberWithMultipleDashes() {
		String rawNumber = "С трехзначным кодом города123-45-67С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		
		assertEquals(expected, actual);
	}
	
//	1234567
	@Test
	public void findPhone_clean_shortNumberWithNothingMore_true() {
		String phone = "1234567";
		
		boolean actual = ps.isGood(phone);
		
		assertTrue(actual);
	}
	
	@Test
	public void extractpPhone_shortNumberWithNothingMore() {
		String rawNumber = "С трехзначным кодом города1234567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void findPhone_dirty_spacesDashes_true() {
		String phone = "aa+7 812 123-4567&&&";
		
		boolean actual = ps.isGood(phone);
		
		assertFalse(actual);
	}
	
	@Test
	public void extractpPhone_shortNumberWithNothingMore_bad() {
		String rawNumber = "С трехзначным кодом города234567С трехзначным кодом города";
		
		String actual = ps.extractPhone(rawNumber);
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
		String phone = "$$+7812 1234567";
		
		boolean actual = ps.isGood(phone);
		
		assertFalse(actual);
	}
}
