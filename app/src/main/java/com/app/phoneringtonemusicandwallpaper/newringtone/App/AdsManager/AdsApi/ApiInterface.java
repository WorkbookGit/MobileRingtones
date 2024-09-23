package com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.AdsApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("json.php")
    Call<AdsModelClass> callAdsApi(@Query("package") String pack);

}
