package com.tc.crm.model.home.dashboard;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DashboardResponse{

	@SerializedName("systemMenu")
	public SystemMenu systemMenu;

	@SerializedName("databases")
	public List<DatabasesItem> databases;

	@SerializedName("Results")
	public Results results;

	@SerializedName("UserData")
	public UserData userData;

	@SerializedName("monthlyCount")
	public List<MonthlyCountItem> monthlyCount;

	@SerializedName("Unassigned")
	public Unassigned unassigned;

	@SerializedName("appMenu")
	public AppMenu appMenu;

	@SerializedName("monthlyRejectedCount")
	public List<MonthlyRejectedCountItem> monthlyRejectedCount;

	@SerializedName("monthlyCompletedCount")
	public List<MonthlyCompletedCountItem> monthlyCompletedCount;

	@SerializedName("clientCount")
	public ClientCount clientCount;

	@SerializedName("users")
	public List<UsersItem> users;
}