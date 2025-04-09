package Commands;

import Core.GameContext;
import Core.Room;

import java.util.Map;

public class LookCommand implements Command {
    @Override
    public void execute(String[] args, GameContext context) {

        if (!context.isCaseStarted()) {
            System.out.println("The case has not started yet. Type 'start case' to begin the investigation.");
            return;
        }

        Room current = context.getBuilding().getCurrentRoom();
        System.out.println(current.getDescription());
        System.out.println(current.getObjectsDescription());
        System.out.println(context.getBuilding().getOccupantsDescription()); // Room description

        // Format exits
        Map<String, Room> neighbors = current.getNeighbors();
        if (!neighbors.isEmpty()) {
            System.out.print("Exits: ");
            for (Map.Entry<String, Room> entry : neighbors.entrySet()) {
                System.out.print(entry.getKey() + " (" + entry.getValue().getName() + ") ");
            }
            System.out.println(); // Newline after exits
        } else {
            System.out.println("Exits: None");
        }

    }

    @Override
    public String getDescription() {
        return "View your surroundings.";
    }
}
