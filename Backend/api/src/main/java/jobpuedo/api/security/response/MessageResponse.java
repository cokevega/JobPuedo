package jobpuedo.api.security.response;

public class MessageResponse {
	private String message;
	private boolean ok;

	public MessageResponse(String message,boolean ok) {
		this.message = message;
		this.setOk(ok);	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}
	
}
