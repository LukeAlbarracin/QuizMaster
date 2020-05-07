package com.example.quizmaster.StartingMenu;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.quizmaster.ButtonListener;
import com.example.quizmaster.QuestionActivities.BasicQuestionActivity;
import com.example.quizmaster.QuestionActivities.InputReaderActivity;
import com.example.quizmaster.QuizTakerActivity;
import com.example.quizmaster.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuizMakerActivity extends AppCompatActivity{
    private transient Intent intent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_maker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<CharSequence> qList = new ArrayList<CharSequence>();
        ArrayList<CharSequence> tList = new ArrayList<CharSequence>();
        if (getIntent().getCharSequenceArrayListExtra("qText") == null ||
                getIntent().getCharSequenceArrayListExtra("tText") == null) {
            displayButtons(qList, tList);
            return;
        }
        qList = getIntent().getCharSequenceArrayListExtra("qText");
        tList = getIntent().getCharSequenceArrayListExtra("tText");
        displayButtons(qList, tList);
        qList.forEach((q) -> { addBoxesDynamically(R.id.leftLayout, q); });
        tList.forEach((t) -> { addBoxesDynamically(R.id.rightLayout, t); });
        intent = new Intent(this, BasicQuestionActivity.class);
        if (getIntent().getStringExtra("mode") != null &&
                getIntent().getStringExtra("mode").equals("basic")) {
            intent.putExtra("backQ", qList);
            intent.putExtra("backT", tList);
        }
    }

    public void displayButtons(List<CharSequence> qList, List<CharSequence> tList) {
        FloatingActionButton questionButton = (FloatingActionButton) findViewById(R.id.AddQuestionButton);
        questionButton.setOnClickListener((unused) -> {
            displayPopup(getCurrentFocus());
        });
        Button saveQuizButton = (Button) findViewById(R.id.SaveQuizButton);
        saveQuizButton.setOnClickListener((unused) -> {
            if (qList.size() >= 5 && tList.size() >= 5) {
                intent.setClass(this, QuizTakerActivity.class);
                startActivity(intent);
            } else {
                CharSequence text = "You must make at least 5 question/answers before saving...";
                Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void displayPopup (final View v) {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.AddQuestionButton);
        PopupMenu popup = new PopupMenu(this, fab);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.question_type_menu, popup.getMenu());
        popup.setOnMenuItemClickListener((item) -> {
           return itemSelected(item);
        });
        popup.show();
    }

    private boolean itemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.basicQuestion:
                Log.i("Console", "*** Debugging Purposes");
                if (this.intent == null) {
                    this.intent = new Intent(this, BasicQuestionActivity.class);
                }
                startActivity(intent);
                return true;
            case R.id.autoQuestion:
                if (this.intent == null) {
                    getIntent().putExtra("mode", "complex");
                    this.intent = new Intent(this, BasicQuestionActivity.class);
                }
                this.intent.setClass(this, InputReaderActivity.class);
                startActivity(this.intent);
                return true;
            /*case R.id.bulletPointQuestion:
                intent = new Intent(this, BulletpointActivity.class);
                Log.i("Console", "*** Debugging Purposes");
                //intent.putExtra("QuestionType", "Bullet Point");
                startActivity(intent);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addBoxesDynamically(final int sideLayout, final CharSequence text) {
        TextView textBox = new TextView(this);
        textBox.setText(text);
        LinearLayout layout = (LinearLayout) findViewById(sideLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.addView(textBox, params);
        textBox.setVisibility(View.VISIBLE);
    }

    private void saveQuizToServer() {
        String url = "http://my-json-feed";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //activity.findViewById(R.id.???)
                        //textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through your singleton class.
        RequestEntity.getInstance().addToRequestQueue(jsonObjectRequest);
    }




}
