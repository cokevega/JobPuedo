package jobpuedo.api.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jobpuedo.api.enumeration.ApplicationStatus;

@Entity
@Table(name = "applications")
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date date;
	private String comments;
	@NotNull
	@Enumerated(EnumType.STRING)
	private ApplicationStatus status;
	@NotNull
	@JsonBackReference(value="applications-offers")
	@ManyToOne
	@JoinColumn(name = "offer_id")
	private Offer offer;
	@NotNull
	@JsonBackReference(value="applications-users")
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Application [id=" + id + ", date=" + date + ", comments=" + comments + ", offer="
				+ offer + ", user=" + user + "]";
	}

}
