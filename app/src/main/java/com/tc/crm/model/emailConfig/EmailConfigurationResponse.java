package com.tc.crm.model.emailConfig;

import com.google.gson.annotations.SerializedName;

public class EmailConfigurationResponse{

	@SerializedName("Results")
	public Results results;

	@SerializedName("EmailConfiguration")
	public EmailConfiguration emailConfiguration;

	@SerializedName("isSuperAdmin")
	public boolean isSuperAdmin;
}