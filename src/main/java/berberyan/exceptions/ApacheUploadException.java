package berberyan.exceptions;

import lombok.Getter;

public class ApacheUploadException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String ERROR_MSG = "Cannot upload file";
	@Getter
	private final Exception innerException;
	
	public ApacheUploadException(String message, Exception e) {
		super(ERROR_MSG + ": " + message, e);
		innerException = e;
	}
	
	public ApacheUploadException(String message) {
		super(ERROR_MSG + ": " + message);
		innerException = null;
	}
}
