package berberyan.controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import berberyan.config.ConfigLoader;
import berberyan.exceptions.ApacheUploadException;
import berberyan.exceptions.DataExtractionException;
import berberyan.exceptions.FileScannerException;
import berberyan.service.DataExtractor;
import berberyan.service.FileScanner;
import berberyan.service.Parser;
import berberyan.service.impl.PhoneScanner;
import berberyan.service.uploader.Uploader;

@Controller
public class IndexController {
	private static final Logger LOGGER = LogManager.getLogger(IndexController.class); 
	private static final String ERROR_ATTR = "error";

	@Autowired
	Uploader uploader;

	@Autowired
	Parser parser;

	@Autowired
	DataExtractor dataExtractor;

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		return "index";
	}

	@GetMapping("/default")
	public String getAppPhones(Model model, HttpSession session) {
		String ext = ConfigLoader.getExtension();
		String dir = ConfigLoader.getDirectory();
		List<Path> fileList;
		try {
			fileList = new FileScanner().getFileList(dir, ext);
			
			List<String> phones = dataExtractor.parseFiles(fileList, new PhoneScanner());
			model.addAttribute("phones", phones);
		} catch (FileScannerException e) {
			LOGGER.error("error while scanning filesystem", e);
			model.addAttribute(ERROR_ATTR, "error while scanning filesystem");
		} catch (DataExtractionException e) {
			LOGGER.error("files cannot be processed", e);
			model.addAttribute(ERROR_ATTR, "files cannot be processed");
		}

		return "index";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String loadFile(HttpServletRequest request, Model model, HttpSession session) {
		LOGGER.debug("loadFile() invoked");
		try {
			uploader.upload(request);
			LOGGER.debug("file uploaded");
			List<Path> filePaths = new ArrayList<>();
			filePaths.add(uploader.getFilePath());
			List<String> phones = dataExtractor.parseFiles(filePaths, parser);
			model.addAttribute("phones", phones);
			uploader.deleteFile();
		} catch (ApacheUploadException e) {
			LOGGER.error("file cannot be uploaded", e);
			model.addAttribute(ERROR_ATTR, "file cannot be uploaded");
		} catch (DataExtractionException e) {
			LOGGER.error("file cannot be processed", e);
			model.addAttribute(ERROR_ATTR, "file cannot be processed");
		}
		return "index";
	}


}
