package com.tc.crm.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TCService {
    private Retrofit retrofit = null;


    public TCAPI getAPI() {
        String BASE_URL = "https://juzdevz.com/crm/ws/";

        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(TCAPI.class);
    }
}
