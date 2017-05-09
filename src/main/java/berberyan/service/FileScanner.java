package berberyan.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileScanner {

	public List<Path> getFileList(String dir, String extention) throws IOException {
		return Files.walk(Paths.get(dir))
				.filter(p -> p.toString().endsWith(extention))
				.collect(Collectors.toList());
	}
}
