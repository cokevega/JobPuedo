package jobpuedo.api.request;
//Filter offers model
public class OfferSearchedRequest {

	private String name;
	private String description;
	private String status;
	private String category;
	private String enterprise;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}

	@Override
	public String toString() {
		return "OfferSearchedRequest [name=" + name + ", description=" + description + ", status=" + status
				+ ", category=" + category + ", enterprise=" + enterprise + "]";
	}

}
