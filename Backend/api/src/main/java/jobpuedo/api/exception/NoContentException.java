package jobpuedo.api.exception;

public class NoContentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String DESCRIPTION = "Contenido no existente";

	public NoContentException(String details) {
		super(DESCRIPTION + ". " + details);
	}

}
