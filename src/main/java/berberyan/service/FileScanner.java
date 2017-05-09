package berberyan.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import berberyan.exceptions.FileScannerException;

public class FileScanner {
	private static final Logger LOGGER = LogManager.getLogger(FileScanner.class); 

	//takes directory name and file extension, returns list of file paths
	public List<Path> getFileList(String dir, String extension) throws FileScannerException {
		try {
			return Files.walk(Paths.get(dir))
					.filter(p -> p.toString().endsWith(extension))
					.collect(Collectors.toList());
		} catch (IOException e) {
			LOGGER.error("cannot get file list", e);
			throw new FileScannerException(e);
		}
	}
}
