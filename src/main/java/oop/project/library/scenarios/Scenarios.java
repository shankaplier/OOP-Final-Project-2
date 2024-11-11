package oop.project.library.scenarios;

import oop.project.library.lexer.Lexer;
import oop.project.library.parsing.DoubleParser;
import oop.project.library.parsing.IntegerParser;

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
        return Lexer.parse(arguments); //TODO
    }

    private static Result<Map<String, Object>> add(String arguments) {
        //Note: For this part of the project, we're focused on lexing/parsing.
        //The implementation of these scenarios isn't going to look like a full
        //command, but rather some weird hodge-podge mix. For example:
        //var args = Lexer.parse(arguments);
        //var left = IntegerParser.parse(args.positional[0]);
        //This is fine - our goal right now is to implement this functionality
        //so we can build up the actual command system in Part 3.
        var lexed = Lexer.parse(arguments);
        switch (lexed) {
            case Result.Failure<Map<String, Object>> v -> {
                return new Result.Failure<>(v.error());
            }
            case Result.Success<Map<String, Object>> v -> {
                var args = v.value();
                var unparsedRight = (String) args.remove("1");
                var unparsedLeft = (String) args.remove("0");
                if (unparsedLeft == null || unparsedRight == null) {
                    return new Result.Failure<>("Missing arguments");
                }
                if (!args.isEmpty()) {
                    return new Result.Failure<>("Too many arguments");
                }
                try {
                    var right = IntegerParser.parse(unparsedRight);
                    var left = IntegerParser.parse(unparsedLeft);
                    return new Result.Success<>(Map.of("left", left, "right", right));
                } catch (Exception e) {
                    return new Result.Failure<>(e.getMessage());
                }
            }
        }
    }

    private static Result<Map<String, Object>> sub(String arguments) {
        var lexed = Lexer.parse(arguments);
        switch (lexed) {
            case Result.Failure<Map<String, Object>> v -> {
                return new Result.Failure<>(v.error());
            }
            case Result.Success<Map<String, Object>> v -> {
                var args = v.value();
                var unparsedRight = (String) args.remove("right");
                var unparsedLeft = (String) args.remove("left");
                if (unparsedLeft == null || unparsedRight == null) {
                    return new Result.Failure<>("Missing arguments");
                }
                if (!args.isEmpty()) {
                    return new Result.Failure<>("Too many arguments");
                }
                try {
                    var right = DoubleParser.parse(unparsedRight);
                    var left = DoubleParser.parse(unparsedLeft);
                    return new Result.Success<>(Map.of("left", left, "right", right));
                } catch (Exception e) {
                    return new Result.Failure<>(e.getMessage());
                }
            }
        }
    }

    private static Result<Map<String, Object>> fizzbuzz(String arguments) {
        //Note: This is the first command your library may not support all the
        //functionality to implement yet. This is fine - parse the number like
        //normal, then check the range manually. The goal is to get a feel for
        //the validation involved even if it's not in the library yet.
        //var number = IntegerParser.parse(lexedArguments.get("number"));
        //if (number < 1 || number > 100) ...
        var lexed = Lexer.parse(arguments);
        switch (lexed) {
            case Result.Failure<Map<String, Object>> v -> {
                return new Result.Failure<>(v.error());
            }
            case Result.Success<Map<String, Object>> v -> {
                var args = v.value();
                var unparsedFizz = (String) args.remove("0");
                if (unparsedFizz == null) {
                    return new Result.Failure<>("Missing arg");
                }
                try {
                    var fizz = IntegerParser.parse(unparsedFizz);
                    if (1 > fizz || fizz > 100) {
                        return new Result.Failure<>("Out of range");
                    }
                    return new Result.Success<>(Map.of("number", fizz));
                } catch (Exception e) {
                    return new Result.Failure<>(e.getMessage());
                }
            }
        }
    }

    private static Result<Map<String, Object>> difficulty(String arguments) {
        var lexed = Lexer.parse(arguments);
        switch (lexed) {
            case Result.Failure<Map<String, Object>> v -> {
                return new Result.Failure<>(v.error());
            }
            case Result.Success<Map<String, Object>> v -> {
                var args = v.value();
                var difficulty = args.remove("0");
                if (difficulty == null) {
                    return new Result.Failure<>("Missing arg");
                }
                if (!(difficulty.equals("easy") || difficulty.equals("normal") || difficulty.equals("hard") || difficulty.equals("peaceful"))) {
                    return new Result.Failure<>("Invalid difficulty");
                }
                if (!args.isEmpty()) {
                    return new Result.Failure<>("Too many args");
                }
                return new Result.Success<>(Map.of("difficulty", difficulty));
            }
        }
    }

    private static Result<Map<String, Object>> echo(String arguments) {
        throw new UnsupportedOperationException("TODO"); //TODO
    }

    private static Result<Map<String, Object>> search(String arguments) {
        throw new UnsupportedOperationException("TODO"); //TODO
    }

    private static Result<Map<String, Object>> weekday(String arguments) {
        throw new UnsupportedOperationException("TODO"); //TODO
    }

}
