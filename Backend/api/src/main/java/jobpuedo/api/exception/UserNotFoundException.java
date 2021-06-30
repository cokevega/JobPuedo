package jobpuedo.api.exception;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String DESCRIPTION = "Usuario no encontrado";

	public UserNotFoundException(String details) {
		super(DESCRIPTION + ". " + details);
	}

}
