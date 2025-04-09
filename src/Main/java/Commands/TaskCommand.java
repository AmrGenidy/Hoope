package Commands;

import Core.GameContext;
import Core.TaskList;

public class TaskCommand implements Command {
    @Override
    public void execute(String[] args, GameContext context) {
        if (!context.isCaseStarted()) {
            System.out.println("The case has not started yet. Type 'start case' to begin the investigation.");
            return;
        }

        TaskList taskList = context.getTaskList();

        if (taskList.isEmpty()) {
            System.out.println("No tasks available for this case.");
        } else {
            System.out.println("Case Tasks:");
            int index = 1;
            for (String task : taskList.getTasks()) {
                System.out.printf("%d. %s%n", index++, task);
            }
        }
    }

    @Override
    public String getDescription() {
        return "View your investigation guide.";
    }
}