package com.example.quizmaster.StartingMenu;

import android.content.Intent;
import android.os.Bundle;

import com.example.quizmaster.ButtonListener;
import com.example.quizmaster.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.Toolbar;

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
