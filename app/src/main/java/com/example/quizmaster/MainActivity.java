package com.example.quizmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizmaster.LanguageAnalysis.SentenceDetector;
import com.google.android.material.snackbar.Snackbar;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.WhitespaceTokenizer;
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

    public void detectSentence() {
        try {
            String input = "Monkeys like bananas. Steve is a monkey. John is a dude.";
            //InputStream stream = getClass().getResourceAsStream("./models/langdetect-183.bin");
            //SentenceModel model = new SentenceModel(stream);
            //SentenceDetectorME sdetect = new SentenceDetectorME(model);
            SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
            String[] sentences = tokenizer.tokenize(input);
            //getClass().getResourceAsStream("/models/en-token.bin");
            InputStream inputStreamNameFinder = getResources().getAssets().open("models/token.model");
            TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);
            NameFinderME nameFinderME = new NameFinderME(model);
            List<Span> spans = Arrays.asList(nameFinderME.find(sentences));
            for (String s : sentences) {
                TextView text = new TextView(this);
                text.setText(s);
                System.out.println(s);
            }
            for (Span s : spans) {
                System.out.println("Span: " + s.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // CREATE YOUR OWN QUIZ OPTION

    // OLD QUIZZES OPTION

    // YOUR QUIZZES OPTION
}
