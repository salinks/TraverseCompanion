package com.tc.crm.ui.contents.userGroups;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.countries.CountriesResponse;
import com.tc.crm.model.userGroups.UserGroupsResponse;

public interface UserGroupsView {


    void onError(String msg);

    void onCommonResult(CommonResult body);

    void onUserGroupsResponse(UserGroupsResponse body);
}
