package com.tc.crm.ui.applicationMenu.editClient;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.clientDetails.ClientDetailsRequest;
import com.tc.crm.model.clientDetails.ClientDetailsResponse;
import com.tc.crm.model.clientDetails.req.CountryUploadRequest;
import com.tc.crm.model.clientDetails.req.IntakeSectionUpdateRequest;
import com.tc.crm.model.clientDetails.req.UpdateClientTypeRequest;
import com.tc.crm.model.clientDetails.req.UpdateStaffRequest;
import com.tc.crm.model.clientDetails.req.UploadImageRequest;
import com.tc.crm.remote.TCService;
import com.tc.crm.ui.applicationMenu.clientDetails.ClientDetailsView;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditClientPresenter {
    private EditClientView mView;
    private TCService tcService;

    public EditClientPresenter(EditClientView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void updateClientCountry(CountryUploadRequest dataModal) {
        tcService
                .getAPI()
                .updateClientCountry(dataModal)
                .enqueue(new Callback<CommonResult>() {
                    @Override
                    public void onResponse(Call<CommonResult> call, Response<CommonResult> res) {
                        mView.onCommonResult(res.body());
                    }

                    @Override
                    public void onFailure(Call<CommonResult> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public void updateClientIntakeSection(IntakeSectionUpdateRequest dataModal) {
        tcService
                .getAPI()
                .updateClientIntakeSection(dataModal)
                .enqueue(new Callback<CommonResult>() {
                    @Override
                    public void onResponse(Call<CommonResult> call, Response<CommonResult> res) {
                        mView.onCommonResult(res.body());
                    }

                    @Override
                    public void onFailure(Call<CommonResult> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public void updateClientType(UpdateClientTypeRequest dataModal) {
        tcService
                .getAPI()
                .updateClientType(dataModal)
                .enqueue(new Callback<CommonResult>() {
                    @Override
                    public void onResponse(Call<CommonResult> call, Response<CommonResult> res) {
                        mView.onCommonResult(res.body());
                    }

                    @Override
                    public void onFailure(Call<CommonResult> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void updateStaff(UpdateStaffRequest dataModal) {
        tcService
                .getAPI()
                .updateStaff(dataModal)
                .enqueue(new Callback<CommonResult>() {
                    @Override
                    public void onResponse(Call<CommonResult> call, Response<CommonResult> res) {
                        mView.onCommonResult(res.body());
                    }

                    @Override
                    public void onFailure(Call<CommonResult> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



}
