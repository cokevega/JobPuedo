package jobpuedo.api.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String DESCRIPTION = "Email registrado";

	public EmailAlreadyRegisteredException(String details) {
		super(DESCRIPTION + ". " + details);
	}

}
