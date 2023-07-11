package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class GroupMembersItem{

	@SerializedName("passportNumber")
	public String passportNumber;

	@SerializedName("clientId")
	public int clientId;

	@SerializedName("surname")
	public String surname;

	@SerializedName("givenName")
	public String givenName;

	@SerializedName("visaNumber")
	public String visaNumber;

	@SerializedName("leadGuest")
	public int leadGuest;
}