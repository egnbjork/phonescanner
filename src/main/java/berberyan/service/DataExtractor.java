package berberyan.service;

import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Service;

import berberyan.exceptions.DataExtractionException;

@Service
@FunctionalInterface
public interface DataExtractor {
	public List<String> parseFiles(List<Path> filePaths, Parser parser) throws DataExtractionException;
}
