package jobpuedo.api.exception;

public class FileHandlingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String DESCRIPTION = "Error cargando archivo";

	public FileHandlingException(String details) {
		super(DESCRIPTION + ". " + details);
	}

}
