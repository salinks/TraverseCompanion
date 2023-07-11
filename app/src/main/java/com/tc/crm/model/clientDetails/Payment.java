package com.tc.crm.model.clientDetails;

import com.google.gson.annotations.SerializedName;

public class Payment{

	@SerializedName("advanceAmount")
	public int advanceAmount;

	@SerializedName("advApprovedName")
	public String advApprovedName;

	@SerializedName("fpRemarks")
	public String fpRemarks;

	@SerializedName("fpPaidOn")
	public String fpPaidOn;

	@SerializedName("createdOn")
	public String createdOn;

	@SerializedName("paymentType")
	public int paymentType;

	@SerializedName("quotedAmount")
	public int quotedAmount;

	@SerializedName("advPaidOn")
	public String advPaidOn;

	@SerializedName("ost")
	public String ost;

	@SerializedName("fpApprovedId")
	public int fpApprovedId;

	@SerializedName("isSinglePay")
	public String isSinglePay;

	@SerializedName("paymentStatus")
	public int paymentStatus;

	@SerializedName("depositAmount")
	public int depositAmount;

	@SerializedName("advRemarks")
	public String advRemarks;

	@SerializedName("oldbalance")
	public int oldbalance;

	@SerializedName("clientId")
	public int clientId;

	@SerializedName("advApprovedOn")
	public String advApprovedOn;

	@SerializedName("advApprovedId")
	public int advApprovedId;

	@SerializedName("balanceAmount")
	public int balanceAmount;

	@SerializedName("userId")
	public int userId;

	@SerializedName("fpApprovedOn")
	public String fpApprovedOn;

	@SerializedName("totalAmount")
	public int totalAmount;

	@SerializedName("propertyCost")
	public int propertyCost;

	@SerializedName("fpApprovedName")
	public String fpApprovedName;

	@SerializedName("refId")
	public String refId;

	@SerializedName("recId")
	public int recId;
}