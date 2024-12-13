package oop.project.library.command;

import oop.project.library.argument.Argument;
import oop.project.library.argument.ArgumentBuilder;
import oop.project.library.argument.ArgumentException;
import oop.project.library.argument.ValidateException;
import oop.project.library.lexer.Lexer;
import oop.project.library.lexer.LexerException;
import oop.project.library.parsing.ParseException;
import oop.project.library.parsing.Parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Verifies the input entered by the user against the program
 */
public class Command {
    String commandName;
    Vector<ArgumentBuilder> ArgumentBuilderList = new Vector<>();

    /**
     * @param commandName The name of the command to be runned
     */
    public Command(String commandName) {
        this.commandName = commandName;
    }

    /**
     * @param argumentName Name of the argument that will be entered
     * @param parser       Parser that the user wishes to use to check the validity of the input
     * @param <T>          Represents what type of input the parser will validate
     * @return Returns an Argument Builder
     */
    public <T> ArgumentBuilder<T> argument(String argumentName, Parser<T> parser) {
        var result = new ArgumentBuilder(argumentName, parser);
        ArgumentBuilderList.add(result);
        return result;
    }

    /**
     * @param inputString The command line input entered by the user
     * @return Returns a Formatted map that represents what the user entered in the command line
     * @throws CommandException Throws a command exception error if invalid input was entered
     */
    public Map<String, Object> parse(String inputString) throws CommandException {
        try {
            Lexer lexer = new Lexer(inputString);

            //more arguments than expected were entered throw an error
            Map<String, Object> result = new HashMap<>();


            boolean optional = false;
            for (var arg : ArgumentBuilderList) {
                var current_arg = arg.build();
                if (current_arg.argumentType() == Argument.ArgumentType.Positional) {
                    if (current_arg.optional()) {
                        optional = true;
                    } else if (optional) {
                        throw new CommandException("Required arguments must come before optional arguments, but " + current_arg.name() + " came after one.");
                    }
                    //Check if the Positional List is not empty
                    if (!lexer.PositionalArguments.isEmpty()) {
                        String positionalValue = lexer.PositionalArguments.removeFirst();
                        result.put(current_arg.name(), current_arg.run(positionalValue));

                    } else if (current_arg.optional()) {
                        result.put(current_arg.name(), current_arg.defaultValue());

                    } else {
                        throw new CommandException("Missing argument: " + current_arg.name());
                    }
                } else if (current_arg.argumentType() == Argument.ArgumentType.Named) {
                    if (!lexer.PositionalArguments.isEmpty()) {
                        throw new CommandException("A positional Argument was entered in place of the named argument: " + current_arg.name());
                    }

                    //Check if the Named List is not empty
                    if (lexer.NamedArguments.containsKey(current_arg.name())) {
                        var removedValue = lexer.NamedArguments.remove(current_arg.name());
                        result.put(current_arg.name(), current_arg.run(removedValue));

                    }
                    //If the list is empty it might be an optional
                    else if (current_arg.optional()) {
                        result.put(current_arg.name(), current_arg.defaultValue());

                    } else {
                        //Make this better
                        throw new CommandException("Missing flag argument: " + current_arg.name());
                    }
                }


            }
            if (!lexer.NamedArguments.isEmpty() || !lexer.PositionalArguments.isEmpty()) {
                throw new CommandException("Too many arguments");
            }
            return result;
        } catch (ParseException e) {
            throw new CommandException("ParseException: " + e.getMessage());
        } catch (ValidateException e) {
            throw new CommandException("ValidationException: " + e.getMessage());
        } catch (ArgumentException e) {
            throw new CommandException("ArgumentException: " + e.getMessage());
        } catch (LexerException e) {
            throw new CommandException("LexerException: " + e.getMessage());
        }
    }
}
