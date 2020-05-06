package com.example.quizmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class BasicQuestionActivity extends AppCompatActivity implements ButtonListener{


    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_question_maker);
        displayButtons();
    }

    public void displayButtons() {
        ArrayList<CharSequence> qList = new ArrayList<CharSequence>();
        ArrayList<CharSequence> tList = new ArrayList<CharSequence>();
        Button confirm = findViewById(R.id.ConfirmQuestion);
        TextInputEditText qText = findViewById(R.id.QuestionText);
        TextInputEditText aText = findViewById(R.id.AnswerText);
        qList = getIntent().getCharSequenceArrayListExtra("backQ") != null ?
                getIntent().getCharSequenceArrayListExtra("backQ") : qList;
        tList = getIntent().getCharSequenceArrayListExtra("backT") != null ?
                getIntent().getCharSequenceArrayListExtra("backT") : tList;
        confirm.setVisibility(View.VISIBLE);
        final ArrayList<CharSequence> immutableQ = qList;
        final ArrayList<CharSequence> immutableT = tList;
        Context context = this;
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CharSequence input = qText.getText();
                CharSequence input2 = aText.getText();
                ArrayList copyQ = new ArrayList<CharSequence>(immutableQ);
                ArrayList copyT = new ArrayList<CharSequence>(immutableT);
                copyQ.add(input);
                copyT.add(input2);

                Log.d("qList", copyQ.toString());
                Log.d("tList", copyT.toString());
                Intent intent = new Intent(context, QuizMakerActivity.class);
                intent.putExtra("qText", copyQ);
                intent.putExtra("tText", copyT);
                intent.putExtra("mode", "basic");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    /*@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putCharSequenceArrayList("qList", qList);
        savedInstanceState.putCharSequenceArrayList("tList", tList);

    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        qList = savedInstanceState.getCharSequenceArrayList("qList");
        tList = savedInstanceState.getCharSequenceArrayList("tList");
    }*/
}
