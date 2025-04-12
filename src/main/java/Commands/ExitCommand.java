package Commands;

import Core.GameContext;

public class ExitCommand extends BaseCommand {
    public ExitCommand() {
        super(true); // Requires the case to be started
    }
    @Override
    public void executeCommand(String[] args, GameContext context) {
        System.out.println("Exiting Case. Goodbye!");
        context.setExitCurrentGame(true); // Signal to exit game loop
    }

    @Override
    public String getDescription() {
        return "Take you back to the select case menu.";
    }
}
