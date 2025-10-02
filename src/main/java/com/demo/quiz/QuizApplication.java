package com.demo.quiz;

import com.demo.quiz.service.QuizService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class QuizApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(QuizApplication.class, args);
        QuizService serviceObj = applicationContext.getBean(QuizService.class);
		System.out.println("Starting Quiz Application");
        startQuiz(serviceObj);
	}

    public static void startQuiz(QuizService quizService) {
        boolean nextQuiz = true;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a quiz topic (e.g., history, science): ");
            String topic = scanner.nextLine();

            var quiz = quizService.generateQuiz(topic);
            int score = 0;

            for (int i = 0; i < quiz.size(); i++) {
                var q = quiz.get(i);
                System.out.println("\nQ" + (i + 1) + ": " + q.getQuestion());
                for (String key : q.getOptions().keySet()) {
                    String value = q.getOptions().get(key);
                    System.out.println("  " + key + ")  " + value);
                }

                System.out.print("Your answer (A/B/C/D): ");
                String answer = scanner.nextLine().trim().toUpperCase();

                if (answer.equalsIgnoreCase(q.getAnswer())) {
                    System.out.println("✅ Correct!");
                    score++;
                } else {
                    System.out.println("❌ Incorrect. Correct answer: " + q.getAnswer());
                }
            }

            System.out.println("\n🎉 Quiz complete! You scored " + score + " out of " + quiz.size());

            System.out.print("\nDo you like a different topic (Y/N): ");
            String nextQuizStr = scanner.nextLine().trim().toUpperCase();
            if (nextQuizStr.equalsIgnoreCase("Y")) {
                nextQuiz = true;
            }else{
                nextQuiz = false;
            }
        } while (nextQuiz);
    }
}
