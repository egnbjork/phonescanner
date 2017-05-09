package berberyan;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import berberyan.exceptions.DataExtractionException;
import berberyan.exceptions.FileScannerException;
import berberyan.service.DataExtractor;
import berberyan.service.FileScanner;
import berberyan.service.Parser;
import berberyan.service.PhoneScanner;

public class App {
	private static final Logger LOGGER = LogManager.getLogger(App.class); 
	private App(){}
	
    public static void main( String[] args ) throws FileScannerException, DataExtractionException {

    		FileScanner sf = new FileScanner();
    		List<Path> testFiles = sf.getFileList(".", ".java");
    		Parser parser = new PhoneScanner();
    		
    		DataExtractor de = new DataExtractor();
    		
    		List<String> phones = de.parseFiles(testFiles, parser);
    		
    		phones.stream().forEach(System.out::println);
    }
}
