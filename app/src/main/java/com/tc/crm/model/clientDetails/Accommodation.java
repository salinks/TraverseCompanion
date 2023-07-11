package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class Accommodation {

	@SerializedName("lName")
	public String lName;

	@SerializedName("clientId")
	public int clientId;

	@SerializedName("lAddress")
	public String lAddress;

	@SerializedName("checkInTime")
	public String checkInTime;

	@SerializedName("userId")
	public int userId;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("noOfDays")
	public int noOfDays;

	@SerializedName("lettingId")
	public int lettingId;

	@SerializedName("createdBy")
	public String createdBy;

	@SerializedName("estCheckInTime")
	public String estCheckInTime;

	@SerializedName("additionalInfo")
	public String additionalInfo;

	@SerializedName("lContact")
	public String lContact;

	@SerializedName("isFelxible")
	public int isFelxible;

	@SerializedName("checkoutTime")
	public String checkoutTime;

	@SerializedName("recId")
	public int recId;
}