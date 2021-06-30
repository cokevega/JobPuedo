package jobpuedo.api.response;

import java.util.Date;

import jobpuedo.api.entity.Offer;
import jobpuedo.api.entity.User;
import jobpuedo.api.enumeration.ApplicationStatus;

//Send application data including the fields 'offer' and 'user'
public class ApplicationResponse {
	private Integer id;
	private Date date;
	private String comments;
	private ApplicationStatus status;
	private Offer offer;
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

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
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

	public ApplicationResponse(Integer id, Date date, String comments, ApplicationStatus status, Offer offer,
			User user) {
		this.id = id;
		this.date = date;
		this.comments = comments;
		this.status = status;
		this.offer = offer;
		this.user = user;
	}

}
