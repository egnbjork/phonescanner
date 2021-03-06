package berberyan.exceptions;

import lombok.Getter;

public class FileScannerException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final String ERROR_MSG = "Unable to get file list";

	@Getter
	private final Exception innerException;
	
	public FileScannerException(Exception e) {
		super(ERROR_MSG, e);
		innerException = e;
	}
}
