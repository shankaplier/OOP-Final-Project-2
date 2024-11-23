package oop.project.library.argument;

import oop.project.library.parsing.Parser;

import java.util.List;
import java.util.function.Function;

public class Argument<T> {
    Parser<T> parser;
    List<Function<T, Boolean>> validators;
    String name;
    public Argument(String name, Parser<T> parser, List<Function<T, Boolean>> validators) {
        this.parser = parser;
        this.validators = validators;
        this.name = name;
    }

    public T run(String input) {
        T value = parser.parse(input);
        for (var validator : validators) {
            if (!validator.apply(value)) {
                throw new ValidateException(value + " failed validation for " + name);
            }
        }
        return value;
    }
}