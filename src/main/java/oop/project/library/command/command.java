package oop.project.library.command;

import com.sun.jdi.connect.Connector;
import oop.project.library.lexer.Lexer;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.lang.Object;
import oop.project.library.lexer.Lexer;
import oop.project.library.argument.ArgumentBuilder;
import oop.project.library.argument.Argument;
import oop.project.library.parsing.Parser;


public class command {
    String commandName;
    Vector<ArgumentBuilder> ArgumentBuilderList = new Vector<>();
    Vector<Argument>  ArgumentList = new Vector<>();

    public command(String commandName)
    {
        this.commandName = commandName;
    }

    public <T> ArgumentBuilder<T> argument(String argumentName, Parser<T> parser)
    {
        var result = new ArgumentBuilder(argumentName, parser);
        ArgumentBuilderList.add(result);
        return result;
    }

    public void build() throws Exception
    {
        for (int i = 0; i < ArgumentBuilderList.size(); i++)
        {
            if (i+1 < ArgumentBuilderList.size())
            {
                if (ArgumentBuilderList.get(i).isOptional() && (ArgumentBuilderList.get(i+1).isNamed() || ArgumentBuilderList.get(i+1).isPositional()))
                {
                    String flagtype = ArgumentBuilderList.get(i).isNamed() ? "Named flag " : "Positional Flag ";
                    throw new Exception("The flag " + ArgumentBuilderList.get(i).getName() + " is an optional flag which occurs before the " + flagtype + ArgumentBuilderList.get(i+1).getName() + " Optional flags must not occur before " + flagtype + " flags");
                }
                else if (ArgumentBuilderList.get(i).isNamed() && ArgumentBuilderList.get(i+1).isPositional())
                {
                    throw new Exception("The flag " + ArgumentBuilderList.get(i).getName() + " is an named flag which occurs before the positional flag " + ArgumentBuilderList.get(i+1).getName() + " Named flags must not occur before positional flags");

                }
            }
            ArgumentList.add(i, ArgumentBuilderList.get(i).build());
        }
    }

    public Map<String, Object> parse(String inputString) throws Exception
    {
        build();
        var result = new HashMap<String, Object>();
        try
        {
            var args = Lexer.parse(inputString);

            var entryIterator = args.entrySet().iterator();
            if (args.size() > ArgumentList.size())
            {
                throw new Exception("The number of arguments is " + args.size() + " but the number of arguments is " + args.size());
            }
            for (Argument arg : ArgumentList)
            {
                if (entryIterator.hasNext())
                {
                    var entry = entryIterator.next();
                    if (isNumeric(entry.getKey()) && arg.getArgumentType().name().equals("Positional"))
                    {
                        result.put(arg.getName(), arg.run((String) entry.getValue()));
                    }
                    else if (entry.getKey().equals(arg.getName()) && arg.getArgumentType().name().equals("Named"))
                    {
                        result.put(arg.getName(), arg.run((String) entry.getValue()));
                    }
                    else if (isNumeric(entry.getKey()) && arg.getArgumentType().name().equals("Optional"))
                    {
                        result.put(arg.getName(), arg.run((String) entry.getValue()));
                    }
                    else if (entry.getKey().equals(arg.getName()) && arg.getArgumentType().name().equals("Optional"))
                    {
                        result.put(arg.getName(), arg.run((String) entry.getValue()));
                    }
                    else
                    {
                        throw new Exception("The argument " + arg.getName() + " is not a valid argument type");
                    }
                }
                else if (arg.getArgumentType().name().equals("Optional"))
                {
                    result.put(arg.getName(), arg.getDefaultValue());
                }
                else{
                    throw new Exception("The argument " + arg.getName() + " has not been given a value");
                }

            }
        }
        catch (Exception e)
        {
            throw new Exception("Error parsing command line arguments: " + e.getMessage());
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
