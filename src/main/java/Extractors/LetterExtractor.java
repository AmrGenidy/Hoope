package Extractors;

import JsonDTO.CaseFile;
import Core.Letter;

public class LetterExtractor {
    public static void loadLetter(CaseFile caseFile, Letter letter) {
        letter.setInvitation(caseFile.getInvitation());
        letter.setCaseDescription(caseFile.getDescription());
    }
}