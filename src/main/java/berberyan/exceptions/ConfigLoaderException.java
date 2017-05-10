package berberyan.exceptions;

import lombok.Getter;

public class ConfigLoaderException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final String ERROR_MSG = "Unable to load config data from file";

	@Getter
	private final Exception innerException;
	
	public ConfigLoaderException(Exception e) {
		super(ERROR_MSG, e);
		innerException = e;
	}
}
