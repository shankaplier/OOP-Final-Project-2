package oop.project.library.command;

import com.sun.jdi.connect.Connector;
import oop.project.library.lexer.Lexer;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.lang.Object;
import oop.project.library.lexer.Lexer;

public class command {
    String commandName;
    Vector<Argument>  ArgumentList;

    command(String commandName)
    {
        this.commandName = commandName;
    }

    ArgumentBuilder argument(String argumentName)
    {
        var result = new ArgumentBuilder(argumentName);
        ArgumentList.add(result);
        return result;
    }

    public void build() throws Exception
    {
        for (int i = 0; i < ArgumentList.size(); i++)
        {
            if (i+1 < ArgumentList.size())
            {
                if (ArgumentList.get(i).isOptional() && (ArgumentList.get(i+1).isNamed() || ArgumentList.get(i+1).isPositional()))
                {
                    String flagtype = ArgumentList.get(i).isNamed() ? "Named flag " : "Positional Flag ";
                    throw new Exception("The flag " + ArgumentList.get(i).name() + " is an optional flag which occurs before the " + flagtype + ArgumentList.get(i+1).name + " Optional flags must not occur before " + flagtype + " flags");
                }
                elif (ArgumentList.get(i).isNamed() && ArgumentList.get(i+1).isPositional())
                {
                    throw new Exception("The flag " + ArgumentList.get(i).name() + " is an named flag which occurs before the positional flag " + ArgumentList.get(i+1).name + " Named flags must not occur before positional flags");

                }
            }
            ArgumentList.set(i, ArgumentList.get(i).build());
        }
    }

    public Map<String, Object> parse(String inputString) throws Exception
    {
        var result = new HashMap<String, Object>();
        try
        {
            var args = Lexer.parse(inputString);
            for(int i = 0; i < args.size(); i++)
            {
                String arg = args.get(i);
                if (isNumeric(arg))
            }

        }
        catch (Exception e)
        {

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
