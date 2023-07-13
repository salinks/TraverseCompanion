package com.tc.crm.model.clientDetails;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ClientDetailsResponse {
//
//	@SerializedName("costItems")
//	public List<CostItemsItem> costItems;
//
//
//
//
//
//	@SerializedName("sourceCategory")
//	public List<SourceCategoryItem> sourceCategory;
//
//	@SerializedName("clientStatus")
//	public List<ClientStatusItem> clientStatus;




    @SerializedName("Results")
    public Results results;
    @SerializedName("clientInfo")
    public ClientInfo clientInfo;

    @SerializedName("countryMaster")
    public List<CountryMasterItem> countryMaster;

    @SerializedName("intakeSections")
	public List<IntakeSectionsItem> intakeSections;

    @SerializedName("clientTypes")
    public List<StaticDataModel> clientTypes;

    @SerializedName("genderList")
    public List<StaticDataModel> genderList;

    @SerializedName("passengerTypes")
    public List<StaticDataModel> passengerTypes;

    @SerializedName("country")
    public List<CountryItem> country;

    @SerializedName("travelConsultants")
    public List<TravelConsultantsItem> travelConsultants;


//
//	@SerializedName("airports")
//	public List<AirportsItem> airports;
//
//	@SerializedName("lettings")
//	public List<LettingsItem> lettings;
//
//	@SerializedName("universities")
//	public List<UniversitiesItem> universities;

//	@SerializedName("drivers")
//	public List<DriversItem> drivers;
}