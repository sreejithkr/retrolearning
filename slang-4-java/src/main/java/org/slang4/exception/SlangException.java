package org.slang4.exception;

import org.slang4.Pair;

public class SlangException extends RuntimeException {
    // Custom metadata fields

    private final int characterIndex;

    // Constructor that passes a message to RuntimeException and saves the metadata
    public SlangException(String message, String content, Integer index) {
        Pair<Integer,Integer> details = findLineAndColumn(content,index);
        super(String.format("%s (Line: %d, Column: %d)", message, details.first, details.second));

        this.characterIndex = index;

    }


    public int getCharacterIndex() { return characterIndex; }
    public static Pair<Integer,Integer> findLineAndColumn(String content, Integer targetIndex) {
        // Edge case: Validate index boundaries
        if (targetIndex < 0 || targetIndex >= content.length()) {
            System.out.println("Index out of bounds.");
            return null;
        }

         Integer lineNumber = 1;      // Lines usually start at 1
         Integer lineStartIndex = 0;  // Track where the current line began

        // Iterate up to the target character index
        for (int i = 0; i < targetIndex; i++) {
            char c = content.charAt(i);

            if (c == '\n') {
                lineNumber++;
                lineStartIndex = i + 1; // The next line starts right after '\n'
            }
        }

        // Calculate the position relative to the start of its own line
        int columnIndex = targetIndex - lineStartIndex;

        System.out.println("Character Index: " + targetIndex);
        System.out.println("Line Number: " + lineNumber);
        System.out.println("Column Position: " + columnIndex);
        return new Pair<>(lineNumber,columnIndex);
    }
}
