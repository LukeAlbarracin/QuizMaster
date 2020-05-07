package com.example.quizmaster.StartingMenu;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizmaster.R;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class RequestEntity {
    private static RequestEntity requestInstance = null;

    private RequestQueue queue;
    //private Context context;

    private RequestEntity() { }

    public static RequestEntity getInstance() {
        if (requestInstance == null) {
            requestInstance = new RequestEntity();
        }
        return requestInstance;
    }

    public void addToRequestQueue(JsonObjectRequest request) {
        if (queue == null) {
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            queue = new RequestQueue(cache, network);
            queue.start();
        }
        queue.add(request);
    }

    public File getCacheDir() {
        // Need to rename
        return new File("/cache");
    }








}
