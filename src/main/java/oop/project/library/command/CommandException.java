package oop.project.library.command;

/**
 * Represents an error in the command class related to parsing the entered user input
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}
