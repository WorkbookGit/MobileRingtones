package com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager;

import android.content.Context;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class WallApiClient {

    private static RequestQueue requestQueue;
    private static RequestQueue wallpaperRequestQueue;

    public static RequestQueue getWallpaperRequestQueue(Context context) {
        if (wallpaperRequestQueue == null) {
            wallpaperRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return wallpaperRequestQueue;
    }
    public static void makeWallpaperStringRequest(Context context, String url, final ApiCallback callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.toString());
                    }
                });

        getWallpaperRequestQueue(context).add(jsonObjectRequest);
    }

    public interface ApiCallback {
        void onSuccess(String response);
        void onError(String error);
    }
}
