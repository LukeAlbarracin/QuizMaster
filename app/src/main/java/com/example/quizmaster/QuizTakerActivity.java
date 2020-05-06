package com.example.quizmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Collections;
import java.util.List;

public class QuizTakerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_taker);
    }

    public void onResume() {
        super.onResume();
        List<CharSequence> qList = getIntent().getCharSequenceArrayListExtra("qList");
        List<CharSequence> tList = getIntent().getCharSequenceArrayListExtra("tList");
        if (getIntent().getBooleanExtra("ShuffleEnabled", false)) {
            Collections.shuffle(qList);
            Collections.shuffle(tList);
        }

    }
}
/**
class TermDefinitionSet {
    private String term;
    private String definition;
    private int indexIdentifier;

     * Constructs the custom data type.
     * @param term - the term (e.g. "Country", "How many legs does an insect have...")
     * @param definition - the definition (e.g. "A type of spiral pasta")
     * @param indexIdentifier - the index from the original arrayList to prevent mix-ups when term/definitions are identical,
     *                        used for debugging purposes primarily...

    public TermDefinitionSet(final String term, final String definition, final int indexIdentifier) {
        this.term = term;
        this.definition = definition;
        this.indexIdentifier = indexIdentifier;
    }

    public String getTerm() {
        return this.term;
    }

    public String getDefinition() {
        return this.definition;
    }
}
*/