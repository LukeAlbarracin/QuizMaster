package com.example.quizmaster.LanguageAnalysis;

import android.view.View;

import com.example.quizmaster.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.uima.tokenize.WhitespaceTokenizer;

public class SentenceDetector {

    public void detectSentence(View v) {
        try {
            String input = "Monkeys like bananas. Steve is a monkey. John is a dude.";
            InputStream stream = getClass().getResourceAsStream("/models/langdetect-183.bin");
            SentenceModel model = new SentenceModel(stream);
            SentenceDetectorME sdetect = new SentenceDetectorME(model);
            String[] sentences = sdetect.sentDetect(input);
            for (String s : sentences) {
                Snackbar.make(v, "Jamba Juice", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                System.out.println("Sentences : "+ s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
