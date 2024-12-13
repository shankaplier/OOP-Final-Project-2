package oop.project.library.command;

import oop.project.library.argument.ValidateException;
import oop.project.library.lexer.Lexer;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.lang.Object;

import oop.project.library.argument.ArgumentBuilder;
import oop.project.library.argument.Argument;
import oop.project.library.parsing.ParseException;
import oop.project.library.parsing.Parser;


public class Command {
    String commandName;
    Vector<ArgumentBuilder> ArgumentBuilderList = new Vector<>();
    Vector<Argument>  ArgumentList = new Vector<>();

    public Command(String commandName)
    {
        this.commandName = commandName;
    }

    public <T> ArgumentBuilder<T> argument(String argumentName, Parser<T> parser)
    {
        var result = new ArgumentBuilder(argumentName, parser);
        ArgumentBuilderList.add(result);
        return result;
    }

//    public void build() throws Exception
//    {
//
//    }

    public Map<String, Object> parse(String inputString) throws CommandException
    {
        Lexer lexer = new Lexer(inputString);

        //more arguments than expected were entered throw an error
        if (lexer.lexedArgumentsLength() > ArgumentBuilderList.size())
        {
            throw new CommandException("The number of arguments is " + lexer.lexedArgumentsLength() + " but the number of expected arguments is " + ArgumentList.size());
        }
        Map<String, Object> result = new HashMap<>();


        boolean optional = false;
        try
        {
            for (var arg: ArgumentBuilderList)
            {
                var current_arg = arg.build();
                if (current_arg.getArgumentType() == Argument.ArgumentType.Positional)
                {
                    //Check if the Positional List is not empty
                    if (!lexer.PositionalArguments.isEmpty())
                    {
                        String positionalValue = lexer.PositionalArguments.removeFirst();
                        result.put(current_arg.getName(), current_arg.run(positionalValue));
                        ArgumentList.add(current_arg);
                    }
                    else if (current_arg.isOptional())
                    {
                        optional = true;
                        result.put(current_arg.getName(), current_arg.getDefaultValue());
                        ArgumentList.add(current_arg);
                    }
                    else if (optional)
                    {
                        //Make this better
                        throw new CommandException("Required arguments not allowed after Optional Arguments");
                    }
                }
                else if (current_arg.getArgumentType() == Argument.ArgumentType.Named)
                {
                    if (!lexer.PositionalArguments.isEmpty())
                    {
                        throw new CommandException("Positional Arguments was entered in the place of an optional argument");
                    }
                    //Check if the Named List is not empty
                    if (!lexer.NamedArguments.isEmpty())
                    {
                        var removedValue = lexer.NamedArguments.remove(current_arg.getName());
                        result.put(current_arg.getName(), current_arg.run(removedValue));
                        ArgumentList.add(current_arg);
                    }
                    //If the list is empty it might be an optional
                    else if (current_arg.isOptional())
                    {
                        optional = true;
                        result.put(current_arg.getName(), current_arg.getDefaultValue());
                        ArgumentList.add(current_arg);
                    }
                    else if (optional)
                    {
                        //Make this better
                        throw new CommandException("Required arguments not allowed after Optional Arguments");
                    }
                }


            }
            return result;

    } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
