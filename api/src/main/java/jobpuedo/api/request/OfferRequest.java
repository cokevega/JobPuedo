package jobpuedo.api.request;
//Create offer model
public class OfferRequest {
	private String name;
	private int category;
	private boolean status;
	private String description;
	private String details;
	private double salary;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OfferRequest [name=" + name + ", category=" + category + ", status=" + status + ", description="
				+ description + ", details=" + details + ", salary=" + salary + "]";
	}
	

}
