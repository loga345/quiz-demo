package com.demo.quiz.model;

import java.util.Map;
import java.util.TreeMap;

public class QuizQuestion {
    String question;
    TreeMap<String, String> options;
    String answer;

    // Constructors
    public QuizQuestion() {}

    public QuizQuestion(String question, TreeMap<String, String> options, String answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    // Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(TreeMap<String, String> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    // Optional: toString() for debugging
    @Override
    public String toString() {
        return "QuizQuestion{" +
                "question='" + question + '\'' +
                ", options=" + options +
                ", answer='" + answer + '\'' +
                '}';
    }
}

