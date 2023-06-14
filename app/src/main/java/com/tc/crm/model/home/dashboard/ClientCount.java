package com.tc.crm.model.home.dashboard;

import com.google.gson.annotations.SerializedName;

public class ClientCount{

	@SerializedName("TMInPrgNos")
	public int tMInPrgNos;

	@SerializedName("rejectedNos")
	public int rejectedNos;

	@SerializedName("totalClients")
	public int totalClients;

	@SerializedName("pendingNos")
	public int pendingNos;

	@SerializedName("TMCancelledAdvNos")
	public int tMCancelledAdvNos;

	@SerializedName("inPrgNos")
	public int inPrgNos;

	@SerializedName("completedNos")
	public int completedNos;

	@SerializedName("TMCompletedNos")
	public int tMCompletedNos;

	@SerializedName("cancelledAdvNos")
	public int cancelledAdvNos;

	@SerializedName("TMOpenNos")
	public int tMOpenNos;

	@SerializedName("TMCancelledFullPayNos")
	public int tMCancelledFullPayNos;

	@SerializedName("TMPendingNos")
	public int tMPendingNos;

	@SerializedName("TMRejectedNos")
	public int tMRejectedNos;

	@SerializedName("cancelledFullPayNos")
	public int cancelledFullPayNos;

	@SerializedName("TMClients")
	public int tMClients;

	@SerializedName("openNos")
	public int openNos;
}