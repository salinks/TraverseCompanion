package com.tc.crm.model.home.dashboard;

import com.google.gson.annotations.SerializedName;

public class Unassigned{

	@SerializedName("CancelledJobs")
	public int cancelledJobs;

	@SerializedName("ClosedJobs")
	public int closedJobs;

	@SerializedName("TotalClients")
	public int totalClients;

	@SerializedName("RejectedJobs")
	public int rejectedJobs;

	@SerializedName("PendingJobs")
	public int pendingJobs;

	@SerializedName("InProgressJobs")
	public int inProgressJobs;
}