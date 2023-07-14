package com.tc.crm.model.clientDetails.req;

import com.google.gson.annotations.SerializedName;

public class ContactUpdateRequest{

	@SerializedName("pPhoneNumber")
	public String pPhoneNumber;

	@SerializedName("cityState")
	public String cityState;

	@SerializedName("emailAddress")
	public String emailAddress;

	@SerializedName("clientId")
	public String clientId;

	@SerializedName("cPhoneCode")
	public String cPhoneCode;

	@SerializedName("cPhoneNumber")
	public String cPhoneNumber;

	@SerializedName("addressTwo")
	public String addressTwo;

	@SerializedName("pinCode")
	public String pinCode;

	@SerializedName("aPhoneNumber")
	public String aPhoneNumber;

	@SerializedName("addressOne")
	public String addressOne;

	@SerializedName("userId")
	public String userId;

	@SerializedName("pPhoneCode")
	public String pPhoneCode;
}