package com.tc.crm.model.emailConfig;

import com.google.gson.annotations.SerializedName;

public class EmailConfigurationRequest{

	@SerializedName("emailAddress")
	public String emailAddress;

	@SerializedName("emailPassword")
	public String emailPassword;

	@SerializedName("fromName")
	public String fromName;

	@SerializedName("emailHost")
	public String emailHost;

	@SerializedName("bccAddress")
	public String bccAddress;

	@SerializedName("userId")
	public String userId;

	@SerializedName("portNumber")
	public String portNumber;
}