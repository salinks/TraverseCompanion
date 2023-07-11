package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class Documents{

	@SerializedName("passportNumber")
	public String passportNumber;

	@SerializedName("ticketNumber")
	public String ticketNumber;

	@SerializedName("clientId")
	public int clientId;

	@SerializedName("visaNumber")
	public String visaNumber;

	@SerializedName("visaPic")
	public String visaPic;

	@SerializedName("casPic")
	public String casPic;

	@SerializedName("casNumber")
	public String casNumber;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("brpNumber")
	public String brpNumber;

	@SerializedName("ticketPic")
	public String ticketPic;

	@SerializedName("recId")
	public int recId;

	@SerializedName("clientPic")
	public String clientPic;

	@SerializedName("passportPic")
	public String passportPic;

	@SerializedName("brpPic")
	public String brpPic;
}