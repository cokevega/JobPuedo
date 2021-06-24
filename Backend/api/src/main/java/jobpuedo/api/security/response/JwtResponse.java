package jobpuedo.api.security.response;

import java.util.List;

//Modelo para envío de datos del usuario autenticado
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String name;
	private int id;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, int id, String name, String email, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.name=name;
		this.email = email;
		this.roles = roles;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}