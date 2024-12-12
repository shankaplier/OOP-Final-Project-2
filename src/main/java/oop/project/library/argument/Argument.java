package oop.project.library.argument;

import oop.project.library.parsing.ParseException;
import oop.project.library.parsing.Parser;

import java.util.List;
import java.util.function.Predicate;

public class Argument<T> {
    Parser<T> parser;
    List<Predicate<T>> validators;
    String name;
    ArgumentType argumentType;
    boolean optional;
    T defaultValue;
    public Argument(String name, Parser<T> parser, List<Predicate<T>> validators, ArgumentType argumentType, T defaultValue, boolean optional) {
        this.parser = parser;
        this.validators = validators;
        this.name = name;
        this.argumentType = argumentType;
        this.defaultValue = defaultValue;
        this.optional = optional;
    }

    public T run(String input) throws ValidateException, ParseException {
        T value = parser.parse(input);
        for (var validator : validators) {
            if (!validator.test(value)) {
                throw new ValidateException(value + " failed validation for " + name);
            }
        }
        return value;
    }

    public String getName() {
        return name;
    }

    public Argument.ArgumentType getArgumentType() {
        return argumentType;
    }

    public boolean isOptional() {
        return optional;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public enum ArgumentType {
        Positional,
        Named,
    }
}