package Commands;

import Core.GameContext;
import Core.TaskList;

public class TaskCommand implements Command {
    @Override
    public void execute(String[] args, GameContext context) {
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
}