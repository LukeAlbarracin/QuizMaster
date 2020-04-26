package com.example.quizmaster;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

public class QuizMakerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_maker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                displayPopup(view);
                startActivity(new Intent(getApplicationContext(), QuestionMakerActivity.class));
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        TextView text = findViewById(R.id.BlankField);
        text.setVisibility(View.VISIBLE);
        text.setText(getIntent().getCharSequenceExtra("FormInput"));
    }

    public void displayPopup (View v) {
        PopupMenu popup = new PopupMenu(this, getCurrentFocus());
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.question_type_menu, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.basicQuestion:
                break;
            case R.id.mathQuestion:
                break;
            case R.id.bulletPointQuestion:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
