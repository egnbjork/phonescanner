package berberyan.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import berberyan.exceptions.DataExtractionException;
import berberyan.service.DataExtractor;
import berberyan.service.Parser;

@Service
public class PhoneExtractor implements DataExtractor{
	private static final Logger LOGGER = LogManager.getLogger(PhoneExtractor.class); 
	
	//returns list of phones in defined filePath with the help of defined parser
	public List<String> parseFiles(List<Path> filePaths, Parser parser) throws DataExtractionException {

		List <String> data = new ArrayList<>();
		for(Path path : filePaths) {
			try (Stream<String> stream = Files.lines(path)) {
				stream.map(parser::extractData)
				.flatMap(PhoneExtractor::flatten)
				.distinct()
				.collect(Collectors.toCollection(() -> data));
			} catch (IOException e) {
				LOGGER.error("Error in DataExctractor", e);
				throw new DataExtractionException(e);
			}
		}
		return data.stream().distinct().sorted().collect(Collectors.toList());	
	}
	
	private static Stream<String> flatten(List<String> listStream) {
		return listStream.stream();
	}
}
