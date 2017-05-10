package berberyan.service.uploader;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import berberyan.exceptions.ApacheUploadException;

@Service
public class ApacheFileUploader implements Uploader {
	private static final Logger LOGGER = LogManager.getLogger(ApacheFileUploader.class); 

	// location to store file uploaded
	private static final String UPLOAD_DIRECTORY = "upload";

	private String filePath;
	private File file;

	@Override
	public void upload(HttpServletRequest request) throws ApacheUploadException {
		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// sets temporary location to store files
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload sfu = new ServletFileUpload(factory);
		String appPath = request.getSession().getServletContext().getRealPath("");
		// this path is relative to application's directory
		String uploadPath = appPath + UPLOAD_DIRECTORY;
		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		LOGGER.debug("start file uploading");
		List<FileItem> formItems;
		try {
			formItems = sfu.parseRequest(request);

			if (formItems == null || formItems.isEmpty()) {
				LOGGER.warn("error while uploading files: formItems are null or empty");
				throw new ApacheUploadException("cannot upload file");
			}

			// iterates over form's fields
			for (FileItem item : formItems) {
				// processes only fields that are not form fields
				if (!item.isFormField()) {
					String fileName = new File(item.getName()).getName();
					filePath = uploadPath + File.separator + fileName;
					LOGGER.debug("filepath is " + filePath);
					file = new File(filePath);
					// saves the file on disk
					item.write(file);
					LOGGER.info("saved in " + file.getAbsolutePath());
				}
			}
		} catch (FileUploadException e) {
			LOGGER.error("cannot parse request", e);
			throw new ApacheUploadException("cannot parse request", e);
		} catch (Exception e) {
			LOGGER.debug("cannot write to the file", e);
			throw new ApacheUploadException("cannot write to the file", e);
		}
		LOGGER.debug("file uploading");
	}

	@Override
	public Path getFilePath() {
		return Paths.get(filePath);
	}

	@Override
	public boolean deleteFile() {
		LOGGER.info("file deleted");
		return file.delete();
	}

	@Override
	public boolean doesExist() {
		return file.exists();
	}

	@Override
	public long getFileSize() {
		return file.getTotalSpace();
	}

	@Override
	public File getFile() {
		LOGGER.info("file returned");
		return file;
	}
}
