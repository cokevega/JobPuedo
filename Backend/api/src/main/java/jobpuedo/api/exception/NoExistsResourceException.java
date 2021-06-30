package jobpuedo.api.exception;

public class NoExistsResourceException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String DESCRIPTION = "Contenido no existente";

	public NoExistsResourceException(String details) {
		super(DESCRIPTION + ". " + details);
	}

}
