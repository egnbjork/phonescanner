package berberyan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import berberyan.service.FileScanner;
import berberyan.service.PhoneScanner;

public class App {
	private static final Logger LOGGER = LogManager.getLogger(App.class); 
	private App(){}
	
    public static void main( String[] args ) throws IOException {

    		LOGGER.debug("main() invoked");
    		
    		FileScanner sf = new FileScanner();
    		List<Path> testFiles = sf.getFileList(".", ".java");
    		
//    		List<String> phoneNumbers = new ArrayList();

    		for(Path path : testFiles) {
    			try (Stream<String> stream = Files.lines(path)) {
    				List<String> phones = stream.map(PhoneScanner::extractPhones)
    				.flatMap(n -> n.stream())
    				.distinct()
    				.sorted()
    				.collect(Collectors.toList());
    				
    				System.out.println(Arrays.toString(phones.toArray()));
//    				phoneNumbers = stream
//    						.flatmap(PhoneScanner::extractPhones)
//    						.collect(Collectors.toList());
//    				stream.forEach(System.out::println);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    }
}
