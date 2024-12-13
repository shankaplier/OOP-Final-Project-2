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
            if (lexer.NamedArguments.isEmpty() && lexer.PositionalArguments.isEmpty())
            {
                return result;
            }
            else
            {
                throw new CommandException("Error");
            }

    } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        for arg in args:
//            current_arg = arg.build()
//            if current_arg.type == positional:
//                if current_arg.optional:
//                    optional = true
//                else if optional:
//                    throw error
//
//
//            if current_arg.type() == Named:
//                take name from named
//            else:
//                take first from positional
//            entries.add(current_arg.name, current_arg.parse())
//        make sure we used all the named and positional from lexed
//
//        for (int i = 0; i < ArgumentBuilderList.size(); i++)
//        {
//            if (i+1 < ArgumentBuilderList.size())
//            {
//                if (ArgumentBuilderList.get(i).isOptional() && !(ArgumentBuilderList.get(i+1).isOptional()))
//                {
//                    throw new CommandException("The flag " + ArgumentBuilderList.get(i).getName() + " is an optional flag which occurs before the non-optional flag" + ArgumentBuilderList.get(i+1).getName() + " Optional flags must not occur before non-optional flags.");
//                }
//                else if (ArgumentBuilderList.get(i).isNamed() && ArgumentBuilderList.get(i+1).isPositional())
//                {
//                    throw new CommandException("The flag " + ArgumentBuilderList.get(i).getName() + " is an named flag which occurs before the positional flag " + ArgumentBuilderList.get(i+1).getName() + " Named flags must not occur before positional flags");
//
//                }
//            }
//            ArgumentList.add(i, ArgumentBuilderList.get(i).build());
//            //Add a bit of line to modify values to keep track of how many values are expected to be given
//        }
//
//        var result = new HashMap<String, Object>();
//        try
//        {
//            Lexer lexer = new Lexer(inputString);
//
//            var args = Lexer.parse(inputString);
//
//            var entryIterator = args.entrySet().iterator();
//            if (args.size() > ArgumentList.size())
//            {
//                throw new CommandException("The number of arguments is " + args.size() + " but the number of expected arguments is " + ArgumentList.size());
//            }
//            for (Argument arg : ArgumentList)
//            {
//                if (entryIterator.hasNext())
//                {
//                    var entry = entryIterator.next();
//                    if (isNumeric(entry.getKey()) && arg.getArgumentType() == Argument.ArgumentType.Positional)
//                    {
//                        result.put(arg.getName(), arg.run((String) entry.getValue()));
//                    }
//                    else if (entry.getKey().equals(arg.getName()) && arg.getArgumentType() == Argument.ArgumentType.Named)
//                    {
//                        result.put(arg.getName(), arg.run((String) entry.getValue()));
//                    }
//                    else
//                    {
//                        throw new CommandException("The flag " + arg.getName() + " is not a valid flag");
//                    }
//                }
//                else if (arg.isOptional())
//                {
//                    result.put(arg.getName(), arg.getDefaultValue());
//                }
//                else{
//                    throw new Exception("The argument " + arg.getName() + " has not been given a value");
//                }
//
//            }
//        }
//        catch (Exception e)
//        {
//            throw new CommandException("Error parsing command line arguments: " + e.getMessage());
//        }
//        return result;
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
