package me.berberyan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
	private static final Logger LOGGER = LogManager.getLogger(App.class); 
	private App(){}
	
    public static void main( String[] args ) throws IOException {

    		LOGGER.debug("main() invoked");
    		
    		List<Path> testFiles = Files.walk(Paths.get("."))
    				.filter(p -> p.toString().endsWith(".java"))
    				.collect(Collectors.toList());
    		
    		for(Path path : testFiles) {
    			try (Stream<String> stream = Files.lines(path)) {
    				stream.forEach(System.out::println);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    }
}
