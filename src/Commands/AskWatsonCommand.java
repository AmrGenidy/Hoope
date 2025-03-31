package Commands;

import Core.GameContext;

public class AskWatsonCommand implements Command {
    @Override
    public void execute(String[] args, GameContext context) {
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