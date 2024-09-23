package com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.AdsApi;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() throws UnsupportedEncodingException {

        if (retrofit == null) {

            String str = "aHR0cHM6Ly9yaW5ndG9uZS5kZXpueXguY29tL2pzb24ucGhw";
            byte[] data = Base64.decode(str, Base64.DEFAULT);
            String wallUrl = new String(data, StandardCharsets.UTF_8);
            retrofit = new Retrofit.Builder()
                    .baseUrl(wallUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getAdsClient() throws UnsupportedEncodingException {

        if (retrofit == null) {

            String str = "aHR0cHM6Ly9kZXpueXguY29tLw==";
            byte[] data = Base64.decode(str, Base64.DEFAULT);
            String text = new String(data, "UTF-8");

            retrofit = new Retrofit.Builder()
                    .baseUrl(text)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}
