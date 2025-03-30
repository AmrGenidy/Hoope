package Commands;

import Core.GameContext;

public class ExitCommand implements Command {
    @Override
    public void execute(String[] args, GameContext context) {
        System.out.println("Exiting Case. Goodbye!");
        context.setExitCurrentGame(true); //Signal to exit game loop
    }
}