package Commands;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static final Map<String, Command> commandMap = new HashMap<>();

    static {
        // Initialize all commands once and cache them
        commandMap.put("start case", new StartCaseCommand());
        commandMap.put("look", new LookCommand());
        commandMap.put("move", new MoveCommand());
        commandMap.put("examine", new ExamineCommand());
        commandMap.put("question", new QuestionCommand());
        commandMap.put("journal", new JournalCommand());
        commandMap.put("journal add", new JournalAddCommand()); // Added for completeness
        commandMap.put("deduce", new DeduceCommand());
        commandMap.put("final exam", new FinalExamCommand()); // Fixed command name
        commandMap.put("ask watson", new AskWatsonCommand());
        commandMap.put("help", new HelpCommand());
        commandMap.put("add", new AddCaseCommand());
        commandMap.put("tasks", new TaskCommand());
        commandMap.put("exit", new ExitCommand());
    }

    public static Command getCommand(String commandName) {
        // Handle case-insensitive lookup
        return commandMap.get(commandName.toLowerCase());
    }
}