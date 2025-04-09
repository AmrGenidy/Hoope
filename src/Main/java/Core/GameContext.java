package Core;


import JsonDTO.CaseFile;

public class GameContext {
    private Building building; // Dynamic game world
    private Detective detective;
    private DoctorWatson watson;
    private Journal journal;
    private TaskList taskList;
    private CaseFile selectedCase; // Centralized case reference
    private boolean exitCurrentGame = false;
    private boolean isCaseStarted = false;

    public GameContext(Building building, Detective detective, DoctorWatson watson, Journal journal, TaskList taskList, CaseFile selectedCase) {
        this.building = building;
        this.detective = detective;
        this.watson = watson;
        this.journal = journal;
        this.taskList = taskList;
        this.selectedCase = selectedCase;

    }


    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public boolean isCaseStarted() {
        return isCaseStarted;
    }

    public void setCaseStarted(boolean isCaseStarted) {
        this.isCaseStarted = isCaseStarted;
    }


    // Getters
    public CaseFile getSelectedCase() { return selectedCase; }
    public Building getBuilding() { return building; } // Replaces getMansion()
    public Detective getDetective() { return detective; }
    public DoctorWatson getWatson() { return watson; }
    public Journal getJournal() { return journal; }
    public TaskList getTaskList() { return taskList;}
    public boolean isExitCurrentGame() { return exitCurrentGame; }
    public void setExitCurrentGame(boolean exit) { this.exitCurrentGame = exit; }
}