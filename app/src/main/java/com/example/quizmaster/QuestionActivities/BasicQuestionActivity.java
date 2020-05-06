package com.example.quizmaster.QuestionActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.quizmaster.ButtonListener;
import com.example.quizmaster.StartingMenu.QuizMakerActivity;
import com.example.quizmaster.R;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class BasicQuestionActivity extends AppCompatActivity implements ButtonListener {


    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_basic_question);
        displayButtons();
    }

    public void displayButtons() {
        ArrayList<CharSequence> qList = new ArrayList<>();
        ArrayList<CharSequence> tList = new ArrayList<>();
        TextInputEditText qText = findViewById(R.id.QuestionText);
        TextInputEditText aText = findViewById(R.id.AnswerText);
        qList = getIntent().getCharSequenceArrayListExtra("backQ") != null ?
                getIntent().getCharSequenceArrayListExtra("backQ") : qList;
        tList = getIntent().getCharSequenceArrayListExtra("backT") != null ?
                getIntent().getCharSequenceArrayListExtra("backT") : tList;

        final ArrayList<CharSequence> immutableQ = qList;
        final ArrayList<CharSequence> immutableT = tList;
        Context context = this;
        Button confirm = findViewById(R.id.ConfirmQuestion);
        confirm.setOnClickListener((unused) -> {
            setVisible(true);
            CharSequence input = qText.getText();
            CharSequence input2 = aText.getText();
            ArrayList copyQ = new ArrayList(immutableQ);
            ArrayList copyT = new ArrayList(immutableT);
            copyQ.add(input);
            copyT.add(input2);
            Intent intent = new Intent(context, QuizMakerActivity.class);
            intent.putExtra("qText", copyQ);
            intent.putExtra("tText", copyT);
            intent.putExtra("mode", "basic");
            startActivity(intent);
        });
        Button cancel = findViewById(R.id.CancelQuestion);
        cancel.setOnClickListener((unused) -> {
            setVisible(true);
            Intent intent = new Intent(context, QuizMakerActivity.class);
            intent.putExtra("qText", immutableQ);
            intent.putExtra("tText", immutableT);
            intent.putExtra("mode", "basic");
            startActivity(intent);
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
