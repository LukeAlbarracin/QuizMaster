package com.example.quizmaster.LanguageAnalysis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
/**
 * Inspiration from 2 OpenNLP (Natural Language Processing) tutorials :
 * https://www.baeldung.com/apache-open-nlp
 * https://www.programcreek.com/2012/05/opennlp-tutorial/
 *
 * Sentence Analyzer takes a String and uses the OpenNLP library to structure it down into readable form
 */
public class SentenceAnalyzer {
    private String input;
    private String[] sentences;
    public SentenceAnalyzer(final String s) {
        input = s;
        try {
            sentences = detectSentence();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Split up the 'input' string into words, counting a period as its own seperate token while taking into account
     * professional names such as Dr., Mr., Mrs., etc...
     * @return An array of strings (each string is an individual word)
     * @throws IOException - in case the model file is not found
     */
    public String[] detectSentence() throws IOException {
        InputStream stream = new FileInputStream("en-token.bin");
        TokenizerModel model = new TokenizerModel(stream);
        TokenizerME tokenizer = new TokenizerME(model);
        String[] tokens = tokenizer.tokenize(input);
        return tokens;
    }

    /**
     * Uses a pre-made model to detect a pronoun associated with a person's name
     * @return An array of Span, which includes an inclusive and exclusive inner and outer bound
     * @throws IOException - in case the model file is not found
     */
    public Span[] detectPerson() throws IOException {
        InputStream stream = new FileInputStream("en-ner-person.bin");
        TokenNameFinderModel tFinder = new TokenNameFinderModel(stream);
        NameFinderME nFinder = new NameFinderME(tFinder);
        Span[] spans = nFinder.find(this.detectSentence());
        return spans;
    }

    /**
     * Uses a pre-made model to detect part-of-speech
     * @return An array of word's part-of-speech values (e.g. noun, adjective, verb)
     * @throws IOException - in case the model file is not found
     */
    public String[] partOfSpeechTags() throws IOException {
        InputStream stream = new FileInputStream("en-pos-perceptron.bin");
        POSModel model = new POSModel(stream);
        POSTaggerME tagger = new POSTaggerME(model);
        String[] tags = tagger.tag(detectSentence());
        return fixUnmarkedTags(tags);
    }

    /**
     * Uses a pre-made model to separate sentences mainly between noun and verb phrases
     * @return An array of Span, which includes an inclusive and exclusive inner and outer bound
     * @throws IOException - in case the model file is not found
     */
    public Span[] chunkSentences() throws IOException {
        InputStream stream = new FileInputStream("en-chunker.bin");
        ChunkerModel model = new ChunkerModel(stream);
        ChunkerME chunker = new ChunkerME(model);
        Span[] spans = chunker.chunkAsSpans(detectSentence(), partOfSpeechTags());
        return spans;
    }

    /**
     * Uses a pre-made model to find an unfiltered version of a word (e.g. people becomes person)
     * Removes plurality and tense amongst other things.
     * @return A Linked List of Strings
     * @throws IOException - in case the model file is not found
     */
    public List<String> getTrueMeanings() throws IOException {
        InputStream stream = new FileInputStream("en-lemmatizer.dict");
        DictionaryLemmatizer lem = new DictionaryLemmatizer(stream);
        List<String> lemmas = new LinkedList<>(Arrays.asList(lem.lemmatize(detectSentence(), partOfSpeechTags())));
        lemmas.remove(new String("O"));
        return lemmas;
    }

    /**
     * Overriden equals method used to check for sentence meaning equality. Still in progress.
     * @param o - The object being tested against for equality
     * @return true / false
     */
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (o == null || !(o instanceof SentenceAnalyzer)) {
            return false;
        }
        SentenceAnalyzer analyzer = (SentenceAnalyzer) o;
        try {
            return analyzer.getTrueMeanings().equals(this.getTrueMeanings());
        } catch (IOException exception) {
            System.out.println("IOException found: " + exception);
            return false;
        }
        // Idea : create a model that analyzes the similarity between answers
    }

    /**
     * The assumption that all unknown words are proper nouns ("e.g. Motorola").
     * @param tags - The part of speech (e.g. noun) tags
     * @return the updated tags, with unknown part of speech tags assumed to be proper nouns
     */
    private String[] fixUnmarkedTags(String[] tags) {
        String[] newTags = tags;
        for (int i = 0; i < tags.length; i++) {
            // `` is what is returned when the part of speech is unrecognized
            if (tags[i].equals("``")) {
                newTags[i] = "NNP";
            }
        }
        return newTags;
    }

    /**
     * Creates a question (noun phrase + verb phrase + (TO/DT/PRP))
     * @param sentence - The sentence passed in to create a question from
     * @return a question
     */
    public String generateQuestion(final String[] sentence) {
        try {
            Span[] spans = this.chunkSentences();
            int endIndex = findEndOfPhrase(spans, 0);
            return concatStrings(sentence, 0, endIndex)
                    + findRemainingPhrase(endIndex)
                    + "?";
        } catch (IOException exception) {
            System.out.println(exception);
            return "ERROR IN GENERATING QUESTION";
        }
    }

    /**
     * Based on the part of speech, will add itself to the question schema
     * @param index - The index where the verb phrase ends
     * @return a String to be added to the question
     */
    private String findRemainingPhrase(int index) {
        try {
            String[] sentence = detectSentence();
            String[] tags = partOfSpeechTags();
            String remainingPhrase = "";
            int acc = index;
            while (tags[acc].equals("TO") || tags[acc].equals("DT") || tags[acc].equals("PRP")) {
                remainingPhrase = remainingPhrase + " " + sentence[acc];
                if (acc >= sentence.length) {
                    return "FAILURE FAILURE FAILURE";
                }
                acc++;
            }
            return remainingPhrase;
        } catch (IOException exception) {
            return "";
        }
    }

    /**
     * Adds strings together from two given indices
     * @param words - The passed in words
     * @param start - the start index
     * @param end - the end index
     * @return a string consisting of all elements within the two indices
     */
    private String concatStrings(String[] words, int start, int end) {
        String newWord = "";
        for (int i = start; i < end; i++) {
            newWord = newWord + " " + words[i];
        }
        return newWord;
    }

    /**
     * The primary logic for question making that finds a noun + verb phrase combination
     * @param spans - the range over which a noun/verb/etc phrase covers
     * @param acc - currently more of a local variable, but may be utilized in future updates for something else
     * @return the index of the end of the verb phrase
     */
    private int findEndOfPhrase(final Span[] spans, int acc) {
        int phraseEndIndex = 0;
        while (!spans[acc].getType().equals("VP")) {
            phraseEndIndex = spans[acc].getEnd();
            acc++;
            if (acc >= spans.length) {
                return -1;
            }
        }
        while (!spans[acc].getType().equals("NP")) {
            phraseEndIndex = spans[acc].getEnd();
            acc++;
            if (acc >= spans.length) {
                return -1;
            }
        }
        return phraseEndIndex;
    }
}
