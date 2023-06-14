package com.tc.crm.model.home.dashboard;

import com.google.gson.annotations.SerializedName;

public class AppMenu{

	@SerializedName("Campus")
	public boolean campus;

	@SerializedName("Countries")
	public boolean countries;

	@SerializedName("AdditionalCost")
	public boolean additionalCost;

	@SerializedName("LettingsSearch")
	public boolean lettingsSearch;

	@SerializedName("Driver")
	public boolean driver;

	@SerializedName("Reports")
	public boolean reports;

	@SerializedName("ActivityLog")
	public boolean activityLog;

	@SerializedName("masters")
	public boolean masters;

	@SerializedName("ClientMenu")
	public boolean clientMenu;

	@SerializedName("Source")
	public boolean source;

	@SerializedName("Universities")
	public boolean universities;

	@SerializedName("CallList")
	public boolean callList;

	@SerializedName("Payment")
	public boolean payment;

	@SerializedName("Refund")
	public boolean refund;

	@SerializedName("AdvancedSearch")
	public boolean advancedSearch;

	@SerializedName("Team")
	public boolean team;

	@SerializedName("Complaint")
	public boolean complaint;

	@SerializedName("Lettings")
	public boolean lettings;

	@SerializedName("DriverSearch")
	public boolean driverSearch;
}