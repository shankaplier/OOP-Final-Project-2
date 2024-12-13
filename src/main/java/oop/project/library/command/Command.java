package oop.project.library.command;

import oop.project.library.lexer.Lexer;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.lang.Object;

import oop.project.library.argument.ArgumentBuilder;
import oop.project.library.argument.Argument;
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
        for (int i = 0; i < ArgumentBuilderList.size(); i++)
        {
            if (i+1 < ArgumentBuilderList.size())
            {
                if (ArgumentBuilderList.get(i).isOptional() && !(ArgumentBuilderList.get(i+1).isOptional()))
                {
                    throw new CommandException("The flag " + ArgumentBuilderList.get(i).getName() + " is an optional flag which occurs before the non-optional flag" + ArgumentBuilderList.get(i+1).getName() + " Optional flags must not occur before non-optional flags.");
                }
                else if (ArgumentBuilderList.get(i).type() == Argument.ArgumentType.Named && ArgumentBuilderList.get(i+1).type() == Argument.ArgumentType.Positional)
                {
                    throw new CommandException("The flag " + ArgumentBuilderList.get(i).getName() + " is an named flag which occurs before the positional flag " + ArgumentBuilderList.get(i+1).getName() + " Named flags must not occur before positional flags");

                }
            }
            ArgumentList.add(i, ArgumentBuilderList.get(i).build());
            //Add a bit of line to modify values to keep track of how many values are expected to be given
        }

        var result = new HashMap<String, Object>();
        try
        {
            var args = Lexer.parse(inputString);

            var entryIterator = args.entrySet().iterator();
            if (args.size() > ArgumentList.size())
            {
                throw new CommandException("The number of arguments is " + args.size() + " but the number of expected arguments is " + ArgumentList.size());
            }
            for (Argument arg : ArgumentList)
            {
                if (entryIterator.hasNext())
                {
                    var entry = entryIterator.next();
                    if (isNumeric(entry.getKey()) && arg.argumentType() == Argument.ArgumentType.Positional)
                    {
                        result.put(arg.name(), arg.run((String) entry.getValue()));
                    }
                    else if (entry.getKey().equals(arg.name()) && arg.argumentType() == Argument.ArgumentType.Named)
                    {
                        result.put(arg.name(), arg.run((String) entry.getValue()));
                    }
                    else
                    {
                        throw new CommandException("The flag " + arg.name() + " is not a valid flag");
                    }
                }
                else if (arg.optional())
                {
                    result.put(arg.name(), arg.defaultValue());
                }
                else{
                    throw new Exception("The argument " + arg.name() + " has not been given a value");
                }

            }
        }
        catch (Exception e)
        {
            throw new CommandException("Error parsing command line arguments: " + e.getMessage());
        }
        return result;
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
