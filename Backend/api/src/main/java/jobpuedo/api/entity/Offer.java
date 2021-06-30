package jobpuedo.api.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jobpuedo.api.enumeration.OfferStatus;

@Entity
@Table(name = "offers")
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	private String name;
	@NotBlank
	@Length(min = 50)
	private String description;
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date date;
	@NotNull
	@Min(value = 0)
	private Double salary;
	@NotNull
	@Enumerated(EnumType.STRING)
	private OfferStatus status;
	@NotBlank
	@Length(min = 100)
	private String details;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "enterprise_id")
	private User enterprise;
	@JsonManagedReference(value="applications-offers")
	@OneToMany()
	@JoinColumn(name = "offer_id")
	private List<Application> applications;

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public OfferStatus getStatus() {
		return status;
	}

	public void setStatus(OfferStatus status) {
		this.status = status;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(User enterprise) {
		this.enterprise = enterprise;
	}

	@Override
	public String toString() {
		return "Offer [id=" + id + ", name=" + name + ", description=" + description + ", date=" + date + ", salary="
				+ salary + ", status=" + status + ", details=" + details + ", category=" + category + ", enterprise="
				+ enterprise + "]";
	}

}
