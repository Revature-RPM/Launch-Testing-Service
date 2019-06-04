package com.revature.models;

public class Dependancy {
	private String groupID;
	private String artifactID;
	private String version;
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getArtifactID() {
		return artifactID;
	}
	public void setArtifactID(String artifactID) {
		this.artifactID = artifactID;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artifactID == null) ? 0 : artifactID.hashCode());
		result = prime * result + ((groupID == null) ? 0 : groupID.hashCode());
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
		Dependancy other = (Dependancy) obj;
		if (artifactID == null) {
			if (other.artifactID != null)
				return false;
		} else if (!artifactID.equals(other.artifactID))
			return false;
		if (groupID == null) {
			if (other.groupID != null)
				return false;
		} else if (!groupID.equals(other.groupID))
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
		return "Dependancy [groupID=" + groupID + ", artifactID=" + artifactID + ", version=" + version + "]";
	}
	public Dependancy(String groupID, String artifactID, String version) {
		super();
		this.groupID = groupID;
		this.artifactID = artifactID;
		this.version = version;
	}
	public Dependancy() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
