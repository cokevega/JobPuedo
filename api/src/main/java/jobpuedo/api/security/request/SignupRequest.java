package jobpuedo.api.security.request;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

//Sign up petition model
public class SignupRequest {
	@NotBlank
	private String name;
	private String last_name;
	private Date born;
	private String phone;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String password;
	private String contact_name;
	private String contact_last_name;
	private String cif;
	private String description;
	private boolean enterprise;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Date getBorn() {
		return born;
	}

	public void setBorn(Date born) {
		this.born = born;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_last_name() {
		return contact_last_name;
	}

	public void setContact_last_name(String contact_last_name) {
		this.contact_last_name = contact_last_name;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnterprise() {
		return enterprise;
	}

	public void setEnterprise(boolean enterprise) {
		this.enterprise = enterprise;
	}

	@Override
	public String toString() {
		return "SignupRequest [name=" + name + ", last_name=" + last_name + ", born=" + born + ", phone=" + phone
				+ ", email=" + email + ", password=" + password + ", contact_name=" + contact_name
				+ ", contact_last_name=" + contact_last_name + ", cif=" + cif + ", description=" + description
				+ ", enterprise=" + enterprise + "]";
	}

}
