package berberyan.exceptions;

import lombok.Getter;

public class DataExtractionException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final String ERROR_MSG = "Unable to extract data from file";

	@Getter
	private final Exception innerException;
	
	public DataExtractionException(Exception e) {
		super(ERROR_MSG, e);
		innerException = e;
	}
}
