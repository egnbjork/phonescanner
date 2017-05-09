package berberyan;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	                          
	@Before
	public void setUp() {
		System.setOut(new PrintStream(outContent)); 
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	}
	
	@Test
	public void mainTest_noArgs() {
		App.main(null);
		String expected = "+7 (812) 123-4567\n+7 (555) 123-4567\n+7 (812) 111-4567\n+7 (812) 123-4567\n+7 (812) 130-4567\n+7 (812) 888-8888\n+7 (812) 987-6543\n+7 (555) 123-4567\n+7 (812) 111-4567\n+7 (812) 123-4567\n+7 (812) 130-4567\n+7 (812) 888-8888\n+7 (812) 987-6543\n"; 
	    assertEquals(expected, outContent.toString());
	}
}