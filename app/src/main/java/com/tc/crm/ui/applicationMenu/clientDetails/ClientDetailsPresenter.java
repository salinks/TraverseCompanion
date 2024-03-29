package com.tc.crm.ui.applicationMenu.clientDetails;


import com.tc.crm.data.model.CommonResult;
import com.tc.crm.model.clientDetails.ClientDetailsRequest;
import com.tc.crm.model.clientDetails.ClientDetailsResponse;
import com.tc.crm.model.clientDetails.req.UploadImageRequest;
import com.tc.crm.remote.TCService;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientDetailsPresenter {
    private ClientDetailsView mView;
    private TCService tcService;

    public ClientDetailsPresenter(ClientDetailsView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void getClientDetails(ClientDetailsRequest dataModal) {
        tcService
                .getAPI()
                .getClientDetails(dataModal)
                .enqueue(new Callback<ClientDetailsResponse>() {
                    @Override
                    public void onResponse(Call<ClientDetailsResponse> call, Response<ClientDetailsResponse> res) {
                        mView.onClientDetailsResponse(res.body());
                    }

                    @Override
                    public void onFailure(Call<ClientDetailsResponse> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void generateLink(ClientDetailsRequest dataModal) {
        tcService
                .getAPI()
                .generateLink(dataModal)
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




    public void ChangeLogo(UploadImageRequest dataModal) {


        RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), dataModal.imageFile);

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("Image_to_upload",
                dataModal.imageFile.getName(), mFile);

        RequestBody userId = RequestBody.create(
                MultipartBody.FORM, dataModal.userId);

        RequestBody clientId = RequestBody.create(
                MultipartBody.FORM, dataModal.clientId);

        RequestBody uploadFor = RequestBody.create(
                MultipartBody.FORM, dataModal.uploadFor);

        RequestBody recId = RequestBody.create(
                MultipartBody.FORM, dataModal.recId);

        tcService.getAPI().UploadFile(fileToUpload,userId,clientId,uploadFor,recId)
                .enqueue(new Callback<CommonResult>() {
                    @Override
                    public void onResponse(Call<CommonResult> call, Response<CommonResult> response) {
                        mView.onCommonResult(response.body());
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
