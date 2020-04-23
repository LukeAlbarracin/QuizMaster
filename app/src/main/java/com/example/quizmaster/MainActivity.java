package com.example.quizmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Button quizMakerButton = findViewById(R.id.NewQuizButton);
        quizMakerButton.setVisibility(View.VISIBLE);
        Log.i("Yogurt", "Hey There!");
        quizMakerButton.setOnClickListener((unused -> {
            // What does BaseContext do?
            detectSentence();
            Intent intent = new Intent(this, QuizMakerActivity.class);
            startActivity(intent);
        }));
    }

    public void detectSentence() {}

    // CREATE YOUR OWN QUIZ OPTION

    // OLD QUIZZES OPTION

    // YOUR QUIZZES OPTION
}
