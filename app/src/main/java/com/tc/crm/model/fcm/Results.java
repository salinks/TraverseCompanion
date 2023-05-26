package com.tc.crm.model.fcm;

import com.google.gson.annotations.SerializedName;

public class Results{

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;
}