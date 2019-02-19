package com.licensesservice.model;

import java.io.Serializable;

public class Organization implements Serializable {

	public Integer orgId;
	
	public String orgName;

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}
