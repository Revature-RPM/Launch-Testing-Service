package com.revature.DTOs;

public class RdsDTO {
private String instanceURL;
private String username;
private String password;
private String securityGroup;

public RdsDTO() {
	super();
	// TODO Auto-generated constructor stub
}

public RdsDTO(String instanceURL, String username, String password, String securityGroup) {
	super();
	this.instanceURL = instanceURL;
	this.username = username;
	this.password = password;
	this.securityGroup = securityGroup;
}

public String getInstanceURL() {
	return instanceURL;
}

public void setInstanceURL(String instanceURL) {
	this.instanceURL = instanceURL;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getSecurityGroup() {
	return securityGroup;
}

public void setSecurityGroup(String securityGroup) {
	this.securityGroup = securityGroup;
}

@Override
public String toString() {
	return "RdsDTO [instanceURL=" + instanceURL + ", username=" + username + ", password=" + password
			+ ", securityGroup=" + securityGroup + "]";
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((instanceURL == null) ? 0 : instanceURL.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((securityGroup == null) ? 0 : securityGroup.hashCode());
	result = prime * result + ((username == null) ? 0 : username.hashCode());
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
	RdsDTO other = (RdsDTO) obj;
	if (instanceURL == null) {
		if (other.instanceURL != null)
			return false;
	} else if (!instanceURL.equals(other.instanceURL))
		return false;
	if (password == null) {
		if (other.password != null)
			return false;
	} else if (!password.equals(other.password))
		return false;
	if (securityGroup == null) {
		if (other.securityGroup != null)
			return false;
	} else if (!securityGroup.equals(other.securityGroup))
		return false;
	if (username == null) {
		if (other.username != null)
			return false;
	} else if (!username.equals(other.username))
		return false;
	return true;
}



}
