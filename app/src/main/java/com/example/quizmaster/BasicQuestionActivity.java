package com.example.quizmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class BasicQuestionActivity extends AppCompatActivity implements ButtonListener{

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_maker);
        displayButtons();
    }

    public void displayButtons() {
        Button confirm = findViewById(R.id.ConfirmQuestion);
        TextInputEditText qText = findViewById(R.id.QuestionText);
        TextInputEditText aText = findViewById(R.id.AnswerText);
        confirm.setVisibility(View.VISIBLE);
        confirm.setOnClickListener((unused -> {
            CharSequence input = qText.getText();
            CharSequence input2 = aText.getText();
            Intent intent = new Intent(getApplicationContext(), QuizMakerActivity.class);
            intent.putExtra("qText", input);
            intent.putExtra("aText", input2);
            startActivity(intent);
            finish();
        }));

    }
}
