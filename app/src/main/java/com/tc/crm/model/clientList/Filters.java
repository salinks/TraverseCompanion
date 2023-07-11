package com.tc.crm.model.clientList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Filters {

	@SerializedName("SourceFilter")
	public List<SourceFilterItem> sourceFilter;

	@SerializedName("CountryFilter")
	public List<CountryFilterItem> countryFilter;

	@SerializedName("ClientTypeFilter")
	public List<ClientTypeFilterItem> clientTypeFilter;

	@SerializedName("TeamFilter")
	public List<TeamFilterItem> teamFilter;

	@SerializedName("ClientStageFilter")
	public List<ClientStageFilterItem> clientStageFilter;

	@SerializedName("UniversityFilters")
	public List<UniversityFiltersItem> universityFilters;

	@SerializedName("ClientStatusFilter")
	public List<ClientStatusFilterItem> clientStatusFilter;
}