package jobpuedo.api.exception;

public class NoAuthenticatedException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String DESCRIPTION="No autenticado";
	
	public NoAuthenticatedException(String details) {
		super(DESCRIPTION+". "+details);
	}
	
}
