package com.example.quizmaster;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class QuizMakerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_maker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.AddQuestionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                displayPopup(getCurrentFocus());
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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.AddQuestionButton);
        PopupMenu popup = new PopupMenu(this, fab);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.question_type_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return itemSelected(item);
            }
        });
        popup.show();
    }

    private boolean itemSelected(MenuItem item) {
        Intent intent = new Intent(this, BasicQuestionActivity.class);
        switch (item.getItemId()) {
            case R.id.basicQuestion:
                //intent.putExtra("QuestionType", "Basic");
                Log.i("Console", "*** Debugging Purposes");
                startActivity(intent);
                return true;
            case R.id.mathQuestion:
                intent = new Intent(this, MathQuestionActivity.class);
                Log.i("Console", "*** Debugging Purposes");
                //intent.putExtra("QuestionType", "Math");
                startActivity(intent);
                return true;
            case R.id.bulletPointQuestion:
                intent = new Intent(this, BulletpointActivity.class);
                Log.i("Console", "*** Debugging Purposes");
                //intent.putExtra("QuestionType", "Bullet Point");
                startActivity(intent);
                return true;
            default:
                //Log.i("Console", "*** Debugging Purposes");
                return super.onOptionsItemSelected(item);
        }
    }

}
