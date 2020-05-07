package com.example.quizmaster.QuestionActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizmaster.StartingMenu.QuizMakerActivity;
import com.example.quizmaster.R;

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
            simpleVolleyRequest(s);
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

    private String addParticularTokens(CharSequence s) {
        return s.toString().trim().replace(" ", "%20");
    }

    private void simpleVolleyRequest(String s) {
        final TextView textView = (TextView) findViewById(R.id.someText);
        RequestQueue queue = Volley.newRequestQueue(this);
        Context context = this;
        String url ="http://www.nla-cs125x-276023.uc.r.appspot.com/process/?input=" + addParticularTokens(s);
        //String url = "https://google.com";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText(response);
                        queue.stop();
                        Intent intent = new Intent(context, QuizMakerActivity.class);
                        ArrayList<CharSequence> qList = (getIntent().getCharSequenceArrayListExtra("backQ") == null)
                                ? qList = new ArrayList<CharSequence>() : getIntent().getCharSequenceArrayListExtra("backQ");
                        ArrayList<CharSequence> tList = (getIntent().getCharSequenceArrayListExtra("backT") == null)
                                ? tList = new ArrayList<CharSequence>() : getIntent().getCharSequenceArrayListExtra("backT");
                        String[] col = response.split(";");
                        qList.add(col[0]+ " ...");
                        tList.add(col[1]);
                        intent.putExtra("mode", "basic");
                        intent.putExtra("qText", qList);
                        intent.putExtra("tText", tList);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                textView.setText("Error: " + e);
                e.printStackTrace();
                queue.stop();
            }});
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() { return 20000; }
            @Override
            public int getCurrentRetryCount() { return 40000; }
            @Override
            public void retry(VolleyError error) throws VolleyError {}});
        queue.add(stringRequest);
    }



}
