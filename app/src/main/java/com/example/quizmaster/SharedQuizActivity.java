package com.example.quizmaster;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class SharedQuizActivity extends OldQuizActivity implements ButtonListener {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_quiz);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displayButtons();
    }

    public void displayButtons() {
        // Allow create your own quiz from all sectors
        FloatingActionButton add = findViewById(R.id.createQuiz);
        add.setOnClickListener((unused) -> {
            Intent intent = new Intent(this, QuizMakerActivity.class);
            startActivity(intent);
            finish();
        });
    }

}
