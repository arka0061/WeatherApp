package com.example.weatherapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingletonClass {

    private static MySingletonClass mInstance;
    private RequestQueue mRequestQueue;

    public MySingletonClass(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized MySingletonClass getInstance(Context context) {
        if (mInstance == null)
            mInstance = new MySingletonClass(context);
        return mInstance;
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }
}
