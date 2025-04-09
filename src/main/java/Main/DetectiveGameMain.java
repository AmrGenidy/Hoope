package Main;

import Commands.Command;
import Commands.CommandFactory;
import Commands.CommandParser;
import Core.*;
import Extractors.*;
import JsonDTO.CaseFile;
import java.util.List;
import java.util.Scanner;

public class DetectiveGameMain {
    private static final String CASES_DIR_ENV = "CASES_DIR";
    private static final String DEFAULT_CASES_DIR = "cases";

    public static void main(String[] args) {
        String casesDir = System.getenv().getOrDefault(CASES_DIR_ENV, DEFAULT_CASES_DIR);
        if (args.length > 0) {
            casesDir = args[0];
        }

        try (Scanner scanner = new Scanner(System.in)) {
            boolean exitApplication = false; // Flag to control the outer loop

            while (!exitApplication) {
                List<CaseFile> cases = CaseLoader.loadCases(casesDir);
                displayCaseMenu(cases);
                CaseFile selectedCase = selectCase(scanner, cases, casesDir);

                if (selectedCase == null) {
                    // Check if the user wants to exit the application
                    exitApplication = true; // Set flag if 'quit' was called in selectCase
                    continue;
                }

                // Start the game and check if the user wants to exit entirely
                exitApplication = startGame(scanner, selectedCase, casesDir);
            }
        }
    }

    private static void displayCaseMenu(List<CaseFile> cases) {
        System.out.println(
                "╔══════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println(
                "║                                     SELECT A CASE TO INVESTIGATE                             ║");
        System.out.println(
                "╠══════════════════════════════════════════════════════════════════════════════════════════════╣");

        if (cases.isEmpty()) {
            System.out.println(
                    "║ No cases available. Please add cases to the cases folder.                                     ║");
        } else {
            for (int i = 0; i < cases.size(); i++) {
                System.out.printf("║ %d. %-89s ║%n", i + 1, cases.get(i).getTitle());
            }
        }

        System.out.println(
                "╚══════════════════════════════════════════════════════════════════════════════════════════════╝");
    }

    private static CaseFile selectCase(Scanner scanner, List<CaseFile> cases, String casesDir) {
        boolean isSelecting = true;

        while (isSelecting) {
            System.out.print("Enter case number (0 to add case, 'quit' to exit game): ");
            String input = scanner.nextLine().trim();

            try {
                if (input.equalsIgnoreCase("quit")) {
                    System.out.println("Exiting the game. Goodbye!");
                    return null; // Signal to exit the main loop
                }

                if (input.equalsIgnoreCase("add case") || input.startsWith("add case ")) {
                    handleAddCase(scanner, input, casesDir);
                    isSelecting = false; // Exit the loop
                    return null; // Signal to reload the case menu
                }

                int choice = Integer.parseInt(input);
                if (choice == 0) {
                    handleAddCase(scanner, "add case", casesDir);
                    isSelecting = false;
                    return null; // Signal to reload the case menu
                } else if (choice > 0 && choice <= cases.size()) {
                    isSelecting = false;
                    return cases.get(choice - 1); // Return selected case
                } else {
                    System.out.println("Invalid choice. Please select a valid case number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number, 'add case', or 'quit'.");
            }
        }
        return null; // Fallback
    }

    private static void handleAddCase(Scanner scanner, String input, String casesDir) {
        Command addCaseCommand = CommandFactory.getCommand("add");
        if (addCaseCommand == null) {
            System.out.println("Error: Add case command not found.");
            return;
        }

        if (input.equalsIgnoreCase("add case")) {
            System.out.print("Enter the file path: ");
            String filePath = scanner.nextLine().trim();
            addCaseCommand.execute(new String[] {"add", "case", filePath}, null);
        } else if (input.startsWith("add case ")) {
            // Validate input length before extracting the file path
            if (input.length() <= "add case ".length()) {
                System.out.println("Error: No file path provided. Please specify a file path.");
                return;
            }
            String filePath = input.substring("add case ".length()).trim();
            addCaseCommand.execute(new String[] {"add", "case", filePath}, null);
        } else {
            System.out.println("Invalid input. Please type 'add case' or 'add case [file_path]'.");
        }
    }

    private static boolean startGame(Scanner scanner, CaseFile caseFile, String casesDir) {
        Building building = BuildingExtractor.loadBuilding(caseFile);
        if (building == null) {
            return false; // Exit to case selection menu
        }

        try {
            SuspectExtractor.loadSuspects(caseFile, building);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return false; // Reload the case menu
        }
        GameObjectExtractor.loadObjects(caseFile, building);

        TaskList taskList = new TaskList(caseFile.getTasks());
        Detective detective = new Detective("Sherlock Holmes");
        DoctorWatson watson = new DoctorWatson(caseFile.getWatsonHints());
        GameContext context =
                new GameContext(building, detective, watson, new Journal(), taskList, caseFile);

        watson.setCurrentRoom(building.getCurrentRoom());
        building.setWatson(watson);

        Letter letter = new Letter();
        LetterExtractor.loadLetter(caseFile, letter);
        letter.displayInvitation();
        System.out.println("\nNow type 'start case' to begin the investigation.");

        while (!context.isExitCurrentGame()) {
            System.out.print("<CaseFile>");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String commandName = CommandParser.parseCommand(input);

            Command command = CommandFactory.getCommand(commandName);
            if (command != null) {
                command.execute(input.split(" "), context);

            } else {
                System.out.println("Unknown command. Type 'help' for a list of commands.");
            }
        }
        return false; // Return to case selection
    }
}
