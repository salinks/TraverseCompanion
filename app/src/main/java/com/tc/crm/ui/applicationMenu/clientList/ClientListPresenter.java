package com.tc.crm.ui.applicationMenu.clientList;


import com.tc.crm.data.model.CommonRequest;
import com.tc.crm.model.clientList.ClientListResponse;
import com.tc.crm.remote.TCService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientListPresenter {
    private ClientListView mView;
    private TCService tcService;

    public ClientListPresenter(ClientListView view) {
        this.mView = view;

        if (this.tcService == null) {
            this.tcService = new TCService();
        }
    }

    public void getClientList(CommonRequest dataModal) {
        tcService
                .getAPI()
                .getClientLists(dataModal)
                .enqueue(new Callback<ClientListResponse>() {
                    @Override
                    public void onResponse(Call<ClientListResponse> call, Response<ClientListResponse> res) {
                        mView.onClientListResponse(res.body());
                    }

                    @Override
                    public void onFailure(Call<ClientListResponse> call, Throwable t) {
                        try {
                            mView.onError("Something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }






}
