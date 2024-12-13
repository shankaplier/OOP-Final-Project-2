package oop.project.library.argument;

import oop.project.library.parsing.ParseException;
import oop.project.library.parsing.Parser;

import java.util.List;
import java.util.function.Predicate;

public record Argument<T> (Parser<T> parser, List<Validator<T>> validators, String name, ArgumentType argumentType, boolean optional, T defaultValue){
    public T run(String input) throws ValidateException, ParseException {
        T value = parser.parse(input);
        for (var validator : validators) {
            if (!validator.predicate.test(value)) {
                throw new ValidateException(value + " failed validation for " + name + (validator.message != null ? ": " + validator.message : "" ));
            }
        }
        return value;
    }
    public enum ArgumentType {
        Positional,
        Named,
    }

    public record Validator<T>(String message, Predicate<T> predicate) {
    }
}