package oop.project.library.scenarios;

import oop.project.library.command.Command;
import oop.project.library.lexer.Lexer;
import oop.project.library.parsing.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class Scenarios {

    public static Result<Map<String, Object>> parse(String command) {
        //Note: Unlike argparse4j, our library will contain a lexer than can
        //support an arbitrary String (instead of requiring a String[] array).
        //We still need to split the base command from the actual arguments
        //string to know which scenario (aka command) we're trying to parse
        //arguments for. This sounds like something a library should handle...
        var split = command.split(" ", 2);
        var base = split[0];
        var arguments = split.length == 2 ? split[1] : "";
        return switch (base) {
            case "lex" -> lex(arguments);
            case "add" -> add(arguments);
            case "sub" -> sub(arguments);
            case "fizzbuzz" -> fizzbuzz(arguments);
            case "difficulty" -> difficulty(arguments);
            case "echo" -> echo(arguments);
            case "search" -> search(arguments);
            case "weekday" -> weekday(arguments);
            default -> throw new AssertionError("Undefined command " + base + ".");
        };
    }

    private static Result<Map<String, Object>> lex(String arguments) {
        //Note: For ease of testing, this should use your Lexer implementation
        //directly rather and return those values.
        try {
            Lexer lexer = new Lexer(arguments);
            Map<String, Object> Result = new HashMap<>();
            for (int i = 0; i< lexer.PositionalArguments.size(); i++)
            {
                Result.put(String.valueOf(i), lexer.PositionalArguments.get(i));
            }
            for (Map.Entry<String, String> entry: lexer.NamedArguments.entrySet())
            {
                Result.put(entry.getKey(), entry.getValue());
            }
            return new Result.Success<>(Result);
        } catch (Exception e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> add(String arguments) {
        //Note: For this part of the project, we're focused on lexing/parsing.
        //The implementation of these scenarios isn't going to look like a full
        //command, but rather some weird hodge-podge mix. For example:
        //var args = Lexer.parse(arguments);
        //var left = IntegerParser.parse(args.positional[0]);
        //This is fine - our goal right now is to implement this functionality
        //so we can build up the actual command system in Part 3.
        try {
            var commandObject = new Command("add");
            commandObject.argument("number1", new IntegerParser()).positional();
            commandObject.argument("number2", new IntegerParser()).positional();
//            commandObject.build();
            var argument = commandObject.parse(arguments);
            var number1 = argument.get("number1");
            var number2 = argument.get("number2");
            return new Result.Success<>(Map.of("left", number1, "right", number2));
        } catch (Exception e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> sub(String arguments) {

        try {
            var commandObject = new Command("sub");
            commandObject.argument("left", new DoubleParser()).named();
            commandObject.argument("right", new DoubleParser()).named();
//            commandObject.build();
            var argument = commandObject.parse(arguments);
            var number1 = argument.get("left");
            var number2 = argument.get("right");
            return new Result.Success<>(Map.of("left", number1, "right", number2));
        } catch (Exception e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> fizzbuzz(String arguments) {
        //Note: This is the first command your library may not support all the
        //functionality to implement yet. This is fine - parse the number like
        //normal, then check the range manually. The goal is to get a feel for
        //the validation involved even if it's not in the library yet.
        //var number = IntegerParser.parse(lexedArguments.get("number"));
        //if (number < 1 || number > 100) ...

        try {
            var commandObject = new Command("fizzbuzz");
            commandObject.argument("number", new IntegerParser()).positional().range(1,100);
//            commandObject.build();
            var argument = commandObject.parse(arguments);
            var number = argument.get("number");
            return new Result.Success<>(Map.of("number", number));
        } catch (Exception e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> difficulty(String arguments) {

        try {
            var commandObject = new Command("difficulty");
            commandObject.argument("difficulty", new StringParser()).positional().choices("easy", "normal", "hard", "peaceful");
//            commandObject.build();
            var argument = commandObject.parse(arguments);
            var difficulty = argument.get("difficulty");
            return new Result.Success<>(Map.of("difficulty", difficulty));
        } catch (Exception e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> echo(String arguments) {

        try {
            var commandObject = new Command("echo");
            commandObject.argument("message", new StringParser()).optional().positional().defaultValue("Echo, echo, echo...");
//            commandObject.build();
            var argument = commandObject.parse(arguments);
            var message = argument.get("message");
            return new Result.Success<>(Map.of("message", message));
        } catch (Exception e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> search(String arguments) {

        try {
            var commandObject = new Command("search");
            commandObject.argument("term", new StringParser()).positional();
            commandObject.argument("case-insensitive", new BooleanParser()).optional().named().defaultValue(false);
//            commandObject.build();
            var argument = commandObject.parse(arguments);
            var term = argument.get("term");
            var caseInsensitive = argument.get("case-insensitive");
            return new Result.Success<>(Map.of("term", term, "case-insensitive", caseInsensitive));
        } catch (Exception e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> weekday(String arguments) {

        try {
            var commandObject = new Command("weekday");
            commandObject.argument("date", new CustomParser<>(LocalDate::parse)).positional();
//            commandObject.build();
            var argument = commandObject.parse(arguments);
            var date = argument.get("date");
            return new Result.Success<>(Map.of("date", date));
        } catch (Exception e) {
            return new Result.Failure<>(e.getMessage());
        }
    }
}
