package com.demo.quiz.service;

import com.demo.quiz.model.QuizQuestion;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.*;

@Service
public class QuizService {

    private final ObjectMapper mapper = new ObjectMapper();

    private  ChatClient chatClient;

    public QuizService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public List<QuizQuestion> generateQuiz(String topic) {
        String prompt = """
            Generate a quiz on the topic '%s'.
            The quiz should contain 5 questions, and each question should have 4 multiple-choice answers (A, B, C, D).
            Only one option should be the correct answer.
            Provide the response in JSON format with the following structure:
            [
              {
                "question": "Question text here?",
                "options": {
                  "A": "Option A text",
                  "B": "Option B text",
                  "C": "Option C text",
                  "D": "Option D text"
                },
                "answer": "Correct option letter (e.g., 'B')"
              }
            ]
        """.formatted(topic);

        System.out.println("Retrieving the questions ...");

        String response  = Objects.requireNonNull(chatClient.prompt()
                .user(prompt)
                .call().chatResponse()).getResult().getOutput().getText();

        int startIndex = response.indexOf("```json");
        int endIndex = response.lastIndexOf("```");

        String response2 = null;

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
             response2 = response.substring(startIndex + 7, endIndex).trim();
        } else {
            System.out.println("JSON block not found");
        }

        try {
            return mapper.readValue(response2,
                    mapper.getTypeFactory().constructCollectionType(List.class, QuizQuestion.class));
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse quiz questions", e);
        }
    }
}

