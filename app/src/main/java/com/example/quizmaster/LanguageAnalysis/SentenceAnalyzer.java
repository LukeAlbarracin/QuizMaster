package com.example.quizmaster.LanguageAnalysis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    public SentenceAnalyzer(final String s) {
        input = s;
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
        return tags;
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
     * @return An array of strings
     * @throws IOException - in case the model file is not found
     */
    public String[] getTrueMeanings() throws IOException {
        InputStream stream = new FileInputStream("en-lemmatizer.dict");
        DictionaryLemmatizer lem = new DictionaryLemmatizer(stream);
        String[] lemmas = lem.lemmatize(detectSentence(), partOfSpeechTags());
        return lemmas;
    }
}
