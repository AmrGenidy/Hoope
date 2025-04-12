package Commands;

import Core.GameContext;

public abstract class BaseCommand implements Command {
    private final boolean requiresCaseStarted;

    protected BaseCommand(boolean requiresCaseStarted) {
        this.requiresCaseStarted = requiresCaseStarted;
    }

    @Override
    public final void execute(String[] args, GameContext context) {
        // Check if the command requires the case to be started
        if (requiresCaseStarted && !context.isCaseStarted()) {
            System.out.println("The case has not started yet. Type 'start case' to begin the investigation.");
            return;
        }

        // Check if the command is only allowed before the case starts
        if (!requiresCaseStarted && context.isCaseStarted()) {
            System.out.println("The case has already started. Exit the case first to use this command.");
            return;
        }

        // Delegate to the specific command's execution logic
        executeCommand(args, context);
    }

    // Abstract method for specific command logic
    protected abstract void executeCommand(String[] args, GameContext context);
}