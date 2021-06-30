package jobpuedo.api.request;

//Filter worker user model
public class WorkerSearchedRequest {
	private String experiences;
	private String education;

	public String getExperiences() {
		return experiences;
	}

	public void setExperiences(String experiences) {
		this.experiences = experiences;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Override
	public String toString() {
		return "WorkerSearchedRequest [experiences=" + experiences + ", education=" + education + "]";
	}

}
