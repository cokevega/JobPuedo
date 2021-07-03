package jobpuedo.api.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class User {
	
	//Common fields (worker user and enterprise user)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	private String name;
	private String last_name;
	@Temporal(TemporalType.DATE)
	private Date born;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@Pattern(regexp = "^[\\d\\s]{9,}$")
	@Nullable
	private String phone;
	@NotNull
	private Integer status;
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date registered_at;
	@OneToOne()
	@JoinColumn(name = "image_id")
	private FileDB image;
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.PERSIST })
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;
	//Is a worker
	@JsonManagedReference(value = "experiences")
	@OneToMany()
	@JoinColumn(name = "user_id")
	private List<Experience> experiences;
	@JsonManagedReference(value = "languages")
	@OneToMany()
	@JoinColumn(name = "user_id")
	private List<Language> languages;
	@JsonManagedReference(value = "education")
	@OneToMany()
	@JoinColumn(name = "user_id")
	private List<Education> education;
	@JsonManagedReference(value = "skills")
	@OneToMany()
	@JoinColumn(name = "user_id")
	private List<Skill> skills;
	@JsonManagedReference(value = "applications-users")
	@OneToMany()
	@JoinColumn(name = "user_id")
	private List<Application> applications;
	//Is an enterprise
	@NotNull
	private Boolean enterprise;
	private String contact_name;
	private String contact_last_name;
	@Pattern(regexp = "^[A-Z]\\d{8}$")
	@Nullable
	private String cif;
	@Length(min = 50)
	@Nullable
	private String description;
	@JsonIgnore
	@OneToMany(mappedBy = "enterprise")
	private List<Offer> offers;

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public void addRole(Role role) {
		if (this.roles == null)
			this.roles = new ArrayList<Role>();
		this.roles.add(role);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getRegistered_at() {
		return registered_at;
	}

	public void setRegistered_at(Date registered_at) {
		this.registered_at = registered_at;
	}

	public FileDB getImage() {
		return image;
	}

	public void setImage(FileDB image) {
		this.image = image;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(List<Experience> experiences) {
		this.experiences = experiences;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public Boolean isEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Boolean enterprise) {
		this.enterprise = enterprise;
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

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	public List<Education> getEducation() {
		return education;
	}

	public void setEducation(List<Education> education) {
		this.education = education;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", last_name=" + last_name + ", born=" + born + ", email=" + email
				+ ", password=" + password + ", phone=" + phone + ", status=" + status + ", registered_at="
				+ registered_at + ", image=" + image + ", roles=" + roles + ", experiences=" + experiences
				+ ", languages=" + languages + ", education=" + education + ", skills=" + skills + ", applications="
				+ applications + ", enterprise=" + enterprise + ", contact_name=" + contact_name
				+ ", contact_last_name=" + contact_last_name + ", cif=" + cif + ", description=" + description
				+ ", offers=" + offers + "]";
	}

}
