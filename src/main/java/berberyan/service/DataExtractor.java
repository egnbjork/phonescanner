package berberyan.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import berberyan.exceptions.DataExtractionException;

public class DataExtractor {
	private static final Logger LOGGER = LogManager.getLogger(DataExtractor.class); 
	
	//returns list of strings of defined filePath with the help of defined parser
	public List<String> parseFiles(List<Path> filePaths, Parser parser) throws DataExtractionException {

		List <String> data = new ArrayList<>();
		for(Path path : filePaths) {
			try (Stream<String> stream = Files.lines(path)) {
				stream.map(parser::extractData)
				.flatMap(DataExtractor::flatten)
				.distinct()
				.sorted()
				.collect(Collectors.toCollection(() -> data));
			} catch (IOException e) {
				LOGGER.error("Error in DataExctractor", e);
				throw new DataExtractionException(e);
			}
		}
		return data;	
	}
	
	private static Stream<String> flatten(List<String> listStream) {
		return listStream.stream();
	}
}
