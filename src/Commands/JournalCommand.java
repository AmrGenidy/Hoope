package Commands;

import Core.GameContext;
import Core.Journal;

import java.util.List;

public class JournalCommand implements Command {
    @Override
    public void execute(String[] args, GameContext context) {
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
}