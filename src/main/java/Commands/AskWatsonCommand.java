package Commands;

import Core.GameContext;

public class AskWatsonCommand implements Command {
    @Override
    public void execute(String[] args, GameContext context) {
        if (!context.isCaseStarted()) {
            System.out.println("The case has not started yet. Type 'start case' to begin the investigation.");
            return;
        }

        // Use getBuilding() instead of getMansion()
        if (context.getWatson().getCurrentRoom().getName().equals(
                context.getBuilding().getCurrentRoom().getName())) {
            context.getWatson().provideHint();
        } else {
            System.out.println("Dr. Watson is not in this room.");
        }
    }
    @Override
    public String getDescription() {
        return "Ask Doctor Watson for a hint or his point of view.";
    }
}