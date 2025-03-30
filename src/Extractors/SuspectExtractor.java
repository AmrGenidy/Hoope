package Extractors;

import Core.Building;
import Core.Room;
import Core.Suspect;
import Core.CaseFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuspectExtractor {
    public static void loadSuspects(CaseFile caseFile, Building building) {
        for (CaseFile.SuspectData suspectData : caseFile.getSuspects()) {
            Suspect suspect = new Suspect(
                    suspectData.getName(),
                    suspectData.getStatement(),
                    suspectData.getClue()
            );
            // Assign starting room
            suspect.setCurrentRoom(assignStartingRoom(suspect, building));
            building.addSuspect(suspect);
        }
    }

    private static Room assignStartingRoom(Suspect suspect, Building building) {
        return building.getRooms().values().stream()
                .skip(new Random().nextInt(building.getRooms().size()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No valid rooms for suspect: " + suspect.getName()));
    }
}