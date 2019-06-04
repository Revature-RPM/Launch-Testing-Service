package com.revature.models;

import java.util.List;

public class Pom {
	private List<Property> properties;
	private String groupID;
	private String artifactID;
	private String version;
	private List<Dependancy> dependancies;
	public List<Property> getProperties() {
		return properties;
	}
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
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
	public List<Dependancy> getDependancies() {
		return dependancies;
	}
	public void setDependancies(List<Dependancy> dependancies) {
		this.dependancies = dependancies;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artifactID == null) ? 0 : artifactID.hashCode());
		result = prime * result + ((dependancies == null) ? 0 : dependancies.hashCode());
		result = prime * result + ((groupID == null) ? 0 : groupID.hashCode());
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
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
		Pom other = (Pom) obj;
		if (artifactID == null) {
			if (other.artifactID != null)
				return false;
		} else if (!artifactID.equals(other.artifactID))
			return false;
		if (dependancies == null) {
			if (other.dependancies != null)
				return false;
		} else if (!dependancies.equals(other.dependancies))
			return false;
		if (groupID == null) {
			if (other.groupID != null)
				return false;
		} else if (!groupID.equals(other.groupID))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
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
		return "Pom [properties=" + properties + ", groupID=" + groupID + ", artifactID=" + artifactID + ", version="
				+ version + ", dependancies=" + dependancies + "]";
	}
	public Pom(List<Property> properties, String groupID, String artifactID, String version,
			List<Dependancy> dependancies) {
		super();
		this.properties = properties;
		this.groupID = groupID;
		this.artifactID = artifactID;
		this.version = version;
		this.dependancies = dependancies;
	}
	public Pom() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
