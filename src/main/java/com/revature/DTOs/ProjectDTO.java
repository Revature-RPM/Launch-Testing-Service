package com.revature.DTOs;

public class ProjectDTO {

	private String language;
	private String dBLanguage;
	private String version;
	private String InstanceId;
	
	public ProjectDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProjectDTO(String language, String dBLanguage, String version, String instanceId) {
		super();
		this.language = language;
		this.dBLanguage = dBLanguage;
		this.version = version;
		InstanceId = instanceId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getdBLanguage() {
		return dBLanguage;
	}

	public void setdBLanguage(String dBLanguage) {
		this.dBLanguage = dBLanguage;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getInstanceId() {
		return InstanceId;
	}

	public void setInstanceId(String instanceId) {
		InstanceId = instanceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((InstanceId == null) ? 0 : InstanceId.hashCode());
		result = prime * result + ((dBLanguage == null) ? 0 : dBLanguage.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectDTO other = (ProjectDTO) obj;
		if (InstanceId == null) {
			if (other.InstanceId != null)
				return false;
		} else if (!InstanceId.equals(other.InstanceId))
			return false;
		if (dBLanguage == null) {
			if (other.dBLanguage != null)
				return false;
		} else if (!dBLanguage.equals(other.dBLanguage))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProjectDTO [language=" + language + ", dBLanguage=" + dBLanguage + ", version=" + version
				+ ", InstanceId=" + InstanceId + "]";
	}
	
	
	
	
}
