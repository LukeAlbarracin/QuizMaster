package com.example.quizmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class InputReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_reader);
    }


    @Override
    protected void onResume() {
        super.onResume();
        final TextView textView = (TextView) findViewById(R.id.someText);
        addButtons();
        textView.setText("");
    }

    public void addButtons() {
        Button cancel = (Button) findViewById(R.id.CancelStuff);
        Button accept = (Button) findViewById(R.id.QuestionStuff);
        accept.setOnClickListener((unused) -> {
            Intent intent = new Intent(this, QuizMakerActivity.class);
            ArrayList<CharSequence> qList = (getIntent().getCharSequenceArrayListExtra("backQ") == null)
                    ? qList = new ArrayList<CharSequence>() : getIntent().getCharSequenceArrayListExtra("backQ");
            ArrayList<CharSequence> tList = (getIntent().getCharSequenceArrayListExtra("backT") == null)
                    ? tList = new ArrayList<CharSequence>() : getIntent().getCharSequenceArrayListExtra("backT");
            String s = addParticularTokens(((TextView) findViewById(R.id.someText)).getText());
            processRequest(s);
        });
        cancel.setOnClickListener((unused) -> {
            Intent intent = new Intent(this, QuizMakerActivity.class);
            ArrayList<CharSequence> qList = (getIntent().getCharSequenceArrayListExtra("backQ") == null)
                    ? qList = new ArrayList<CharSequence>() : getIntent().getCharSequenceArrayListExtra("backQ");
            ArrayList<CharSequence> tList = (getIntent().getCharSequenceArrayListExtra("backT") == null)
                    ? tList = new ArrayList<CharSequence>() : getIntent().getCharSequenceArrayListExtra("backT");
            intent.putExtra("mode", "complex");
            intent.putExtra("qText", qList);
            intent.putExtra("tText", tList);
            startActivity(intent);
        });
    }

    public String addParticularTokens(CharSequence s) {
        return s.toString().trim().replace(" ", "%20");
    }

    private void processRequest(String s) {
        final TextView textView = (TextView) findViewById(R.id.someText);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        String url ="http://www.nla-cs125x-276023.uc.r.appspot.com/process/?input=" + addParticularTokens(s);
        //String url = "https://google.com";
        // "http://192.168.4.31:8080/";
        //String url = ""
        Context context = this;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i("Y", "***There***");
                        textView.setText("Response is: " + response);
                        queue.stop();

                        Intent intent = new Intent(context, QuizMakerActivity.class);
                        ArrayList<CharSequence> qList = (getIntent().getCharSequenceArrayListExtra("backQ") == null)
                                ? qList = new ArrayList<CharSequence>() : getIntent().getCharSequenceArrayListExtra("backQ");
                        ArrayList<CharSequence> tList = (getIntent().getCharSequenceArrayListExtra("backT") == null)
                                ? tList = new ArrayList<CharSequence>() : getIntent().getCharSequenceArrayListExtra("backT");
                        String s = addParticularTokens(((TextView) findViewById(R.id.someText)).getText());
                        qList.add(response);
                        intent.putExtra("mode", "basic");
                        intent.putExtra("qText", qList);
                        intent.putExtra("tText", tList);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Errorsss", "Hello Friend!");
                textView.setText("Error: " + error);
                error.printStackTrace();
                queue.stop();
            }});
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 20000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 40000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {}
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
