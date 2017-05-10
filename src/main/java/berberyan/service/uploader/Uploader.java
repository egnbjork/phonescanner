package berberyan.service.uploader;

import java.io.File;
import java.nio.file.Path;

import javax.servlet.http.HttpServletRequest;

import berberyan.exceptions.ApacheUploadException;

public interface Uploader {

	public void upload(HttpServletRequest request) throws ApacheUploadException;
	
	public File getFile();

	public boolean deleteFile();
	
	public boolean doesExist();
	
	public long getFileSize();
	
	public Path getFilePath();
}
