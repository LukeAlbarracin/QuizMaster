package com.example.quizmaster.LanguageAnalysis;

// Credit: https://www.programcreek.com/2012/05/opennlp-tutorial/

import java.util.jar.Attributes;
/**
 * Uses Apache OpenNLP Library to analyze text and supply an intelligent question to the user
 */
public class AnswerGenerator {
    private NameFinder nameFinder;
    private SentenceDetector sentenceDetector;
    private SentenceParser sentenceParser;
    private SentenceTokenizer sentenceTokenizer;
    private SpeechPartTagger speechPartTagger;

    public AnswerGenerator() {}

}
