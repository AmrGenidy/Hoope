package Commands;

import Core.CaseFile;
import Core.GameContext;
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
                // Modified feedback: No answer revealed
                System.out.println("Incorrect. Review your clues and try again.");
            }
        }

        if (score == examQuestions.size()) {
            System.out.println("Case solved! All answers correct.");
            context.setCaseSolved(true);
            context.getDetective().promote();
        } else {
            System.out.println("Case unsolved. You'll need to try again later.");
            context.getDetective().demote();
        }

        System.out.println("Your rank: " + context.getDetective().getRank());
    }
}