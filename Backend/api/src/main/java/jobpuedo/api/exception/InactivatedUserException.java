package jobpuedo.api.exception;

public class InactivatedUserException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String DESCRIPTION = "Usuario inactivo.";
	private int id;

	public InactivatedUserException(String details,int id) {
		super(DESCRIPTION + ". " + details);
		this.setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
