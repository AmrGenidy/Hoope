package Commands;

import Core.GameContext;
import Core.Journal;

import java.util.List;

public class JournalCommand implements Command {
    @Override
    public void execute(String[] args, GameContext context) {

        if (!context.isCaseStarted()) {
            System.out.println("The case has not started yet. Type 'start case' to begin the investigation.");
            return;
        }

        Journal journal = context.getJournal();
        List<String> entries = journal.getEntries();

        if (entries.isEmpty()) {
            System.out.println("Journal is empty.");
        } else {
            System.out.println("Journal Contents:");
            for (String entry : entries) {
                System.out.println(" - " + entry);
            }
        }
    }
    @Override
    public String getDescription() {
        return "Review your collected clues.";
    }
}