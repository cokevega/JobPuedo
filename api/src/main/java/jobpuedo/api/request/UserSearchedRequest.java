package jobpuedo.api.request;
//Filter user model
public class UserSearchedRequest {
	private String role;
	private String name;
	private String email;
	private String status;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserSearchedRequest [role=" + role + ", name=" + name + ", email=" + email + ", status=" + status + "]";
	}

}
