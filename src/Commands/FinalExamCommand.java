package Commands;

import Core.GameContext;
import JsonDTO.CaseFile;
import java.util.List;
import java.util.Scanner;

public class FinalExamCommand implements Command {
    @Override
    public void execute(String[] args, GameContext context) {
        CaseFile caseFile = context.getSelectedCase();
        List<CaseFile.ExamQuestion> examQuestions = caseFile.getFinalExam();
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        System.out.println("Final Exam:");
        for (CaseFile.ExamQuestion question : examQuestions) {
            System.out.print(question.getQuestion() + " ");
            String answer = scanner.nextLine().trim();

            if (answer.equalsIgnoreCase(question.getAnswer())) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect. Review your clues and try again.");
            }
        }

        // Pass the score to the Detective and evaluate the rank
        context.getDetective().setFinalExamScore(score);
        context.getDetective().evaluateRank();

        // Display feedback based on the score and rank
        if (score == examQuestions.size()) {
            System.out.println("Case solved! All answers correct.");
        } else {
            System.out.println("Case unsolved. You'll need to try again later.");
        }

        System.out.println("Your rank: " + context.getDetective().getRank());
    }

    @Override
    public String getDescription() {
        return "Answer key questions to solve the case.";
    }
}