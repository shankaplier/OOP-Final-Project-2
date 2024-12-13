package oop.project.library.command;

import java.util.Map;

/**
 * Contains the arguments that the user entered
 */
public class Arguments {
    Map<String, Object> args;

    protected Arguments(Map<String, Object> args) {
        this.args = args;
    }

    /**
     * Extract an argument by name
     *
     * @param <T>  The type of the argument value
     * @param name The argument name to extract
     * @return The value of the argument
     * @throws ClassCastException If T does not match the parser used in the Command class
     */
    public <T> T get(String name) {
        return (T) args.get(name);
    }
}
