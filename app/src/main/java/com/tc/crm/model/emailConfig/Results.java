package com.tc.crm.model.emailConfig;

import com.google.gson.annotations.SerializedName;

public class Results{

	@SerializedName("message")
	public String message;

	@SerializedName("status")
	public boolean status;
}