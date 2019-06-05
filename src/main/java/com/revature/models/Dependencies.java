package com.revature.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dependencies {
	private List<Dependency> dependencies;

	public List<Dependency> getDependancies() {
		return dependencies;
	}

	public void setDependancies(List<Dependency> dependancies) {
		this.dependencies = dependancies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dependencies == null) ? 0 : dependencies.hashCode());
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
		Dependencies other = (Dependencies) obj;
		if (dependencies == null) {
			if (other.dependencies != null)
				return false;
		} else if (!dependencies.equals(other.dependencies))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Dependancies [dependancies=" + dependencies + "]";
	}

	public Dependencies(List<Dependency> dependancies) {
		super();
		this.dependencies = dependancies;
	}

	public Dependencies() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
