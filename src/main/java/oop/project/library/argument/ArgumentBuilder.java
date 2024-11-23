package oop.project.library.argument;

import oop.project.library.parsing.Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class ArgumentBuilder<T> {
    private final String name;
    private ArgumentType argumentType;
    private final Parser<T> parser;
    private final List<Function<T, Boolean>> validators;

    public ArgumentBuilder(String argumentName, Parser<T> parser) {
        name = argumentName;
        argumentType = ArgumentType.NOT_SET;
        validators = new ArrayList<>();
        this.parser = parser;
    }

    public String getName() {
        return name;
    }

    public ArgumentBuilder<T> positional() {
        argumentType = ArgumentType.POSITIONAL;
        return this;
    }

    public ArgumentBuilder<T> named() {
        argumentType = ArgumentType.NAMED;
        return this;
    }

    public boolean isPositional() {
        if (argumentType.equals(ArgumentType.NOT_SET)) {
            // TODO throw error
        }
        return argumentType.equals(ArgumentType.POSITIONAL);
    }

    public boolean isNamed() {
        if (argumentType.equals(ArgumentType.NOT_SET)) {
            // TODO throw error
        }
        return argumentType.equals(ArgumentType.NAMED);
    }

    public ArgumentBuilder<T> validator(Function<T, Boolean> validator) {
        validators.add(validator);
        return this;
    }

    public ArgumentBuilder<T> choices(T... choices) {
        validator(t1 -> Arrays.stream(choices).anyMatch(t2 -> t1 == t2));
        return this;
    }

    public Argument<T> build() {
        if (argumentType.equals(ArgumentType.NOT_SET)) {
            throw new ArgumentException();
        }
        return new Argument<T>(name, parser, validators);
    }

    private enum ArgumentType {
        NOT_SET,
        POSITIONAL,
        NAMED,
    }
}
