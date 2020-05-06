package com.example.quizmaster;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.Size;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class QuizMakerActivity extends AppCompatActivity implements ButtonListener {
    private transient Intent intent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_maker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displayButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinearLayout text = findViewById(R.id.linLayout);
        System.out.println("***CONSOLE*** HELLO!!!");
        //text.setText(getIntent().getCharSequenceExtra("qText"));
        if (getIntent().getCharSequenceArrayListExtra("qText") == null ||
                getIntent().getCharSequenceArrayListExtra("tText") == null) {
            return;
        }
        ArrayList<CharSequence> qList = getIntent().getCharSequenceArrayListExtra("qText");
        ArrayList<CharSequence> tList = getIntent().getCharSequenceArrayListExtra("tText");
        for (CharSequence q : qList) {
            TextView textBox = new TextView(this);
            textBox.setText(q);
            LinearLayout layout = (LinearLayout) findViewById(R.id.leftLayout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.addView(textBox, params);
            textBox.setVisibility(View.VISIBLE);

        }
        for (CharSequence t : tList) {
            TextView textBox = new TextView(this);
            textBox.setText(t);
            LinearLayout layout = (LinearLayout) findViewById(R.id.rightLayout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            textBox.setGravity(View.FOCUS_RIGHT);
            layout.addView(textBox, params);
            textBox.setVisibility(View.VISIBLE);
        }
        this.intent = new Intent(this, BasicQuestionActivity.class);
        Log.i("qStuff" , qList.toString());
        Log.i("tStuff", tList.toString());
        if (getIntent().getStringExtra("mode") != null && getIntent().getStringExtra("mode").equals("basic")) {
            intent.putExtra("backQ", qList);
            intent.putExtra("backT", tList);
        }
    }

    public void displayButtons() {
        FloatingActionButton questionButton = (FloatingActionButton) findViewById(R.id.AddQuestionButton);
        questionButton.setOnClickListener((unused) -> {
            displayPopup(getCurrentFocus());
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
                //intent.putExtra("QuestionType", "Basic");
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
                //this.intent.setClassName(this, "InputReaderActivity");
                this.intent.setClass(this, InputReaderActivity.class);
                        //= new Intent(this, InputReaderActivity.class);

                Log.i("Console", "*** Debugging Purposes");
                //intent.putExtra("QuestionType", "Math");
                startActivity(this.intent);
                return true;
            /*case R.id.bulletPointQuestion:
                intent = new Intent(this, BulletpointActivity.class);
                Log.i("Console", "*** Debugging Purposes");
                //intent.putExtra("QuestionType", "Bullet Point");
                startActivity(intent);
                return true;*/
            default:
                //Log.i("Console", "*** Debugging Purposes");
                return super.onOptionsItemSelected(item);
        }
    }

}
