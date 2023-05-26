package com.tc.crm.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("Results")
	public Results results;

	@SerializedName("UserData")
	public UserData userData;

}