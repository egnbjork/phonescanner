package berberyan.controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import berberyan.exceptions.DataExtractionException;
import berberyan.exceptions.FileScannerException;
import berberyan.service.DataExtractor;
import berberyan.service.FileScanner;
import berberyan.service.PhoneScanner;

public class Cli {
	private static final Logger LOGGER = LogManager.getLogger(Cli.class); 
	private String[] args = null;
	private Options options = new Options();
	private final String country;
	private final String city;
	private final String directory;
	private final String extension;
	private final String help;

	public Cli(String[] args) {
		country = "country";
		city = "city";
		directory = "dir";
		extension = "ext";
		help = "help";
		
		this.args = args;
		options.addOption(help, "help", false, "show help.");
		options.addOption(extension, true, "Change default \".txt\" extension");
		options.addOption(directory, true, "Change default directory");
		options.addOption(country, true, "Change default country code");
		options.addOption(city, true, "Change default city code");
		
	}

	public void start() {
		CommandLineParser parser = new BasicParser();

		try {
			CommandLine cmd = parser.parse(options, args);

			if (cmd.hasOption(help)) {
				help();
			} 

			if (cmd.hasOption(country)) {
				PhoneScanner.setDefaultCountryCode(cmd.getOptionValue(country));
			} 

			if (cmd.hasOption(city)) {
				PhoneScanner.setDefaultCityCode(cmd.getOptionValue(city));
			}

			if (cmd.hasOption(directory)) {
				
			}

			if(cmd.hasOption(extension)) {

			}

			runApp();
		} catch (ParseException e) {
			LOGGER.error("Failed to parse command line properties", e);
			return;
		}
	}

	//starts the app
	private void runApp() {
		List<String> phones = new ArrayList<>();

		try {
			List<Path> fileList = new FileScanner().getFileList(".", ".java");

			phones = new DataExtractor().parseFiles(fileList, new PhoneScanner());

		} catch (FileScannerException e) {
			LOGGER.error("Error while scanning directory", e);
		} catch (DataExtractionException e) {
			LOGGER.error("Error while extracting data", e);
		}	

		phones.stream().forEach(LOGGER::info);
	}

	//prints some help
	private void help() {
		HelpFormatter formater = new HelpFormatter();
		LOGGER.info("This is help");
		formater.printHelp("Main", options);
		return;
	}
}
