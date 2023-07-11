package com.tc.crm.ui.applicationMenu.clientDetails;


import com.tc.crm.model.clientDetails.ClientDetailsRequest;
import com.tc.crm.model.clientDetails.ClientDetailsResponse;
import com.tc.crm.remote.TCService;

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






}
