package com.example.quizmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class BulletpointActivity extends AppCompatActivity implements ButtonListener {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletpoint);
        displayButtons();
    }

    public void displayButtons() {
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.AddBulletpointButton);
        button.setOnClickListener((unused) -> {
            TextInputEditText textBox = new TextInputEditText(this);
            LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.addView(textBox, params);
        });
    }

    // Need to create RecycleViews / TextInputLayout to add to bulletpoints

}
