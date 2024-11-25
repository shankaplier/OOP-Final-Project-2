package oop.project.library.argument;

import oop.project.library.parsing.Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ArgumentBuilder<T> {
    private final String name;
    private final Parser<T> parser;
    private final List<Function<T, Boolean>> validators;
    private Argument.ArgumentType argumentType;
    private boolean optional;
    private T defaultValue;

    public ArgumentBuilder(String argumentName, Parser<T> parser) {
        name = argumentName;
        argumentType = null;
        validators = new ArrayList<>();
        this.parser = parser;
        this.optional = false;
        this.defaultValue = null;
    }

    public String getName() {
        return name;
    }

    public ArgumentBuilder<T> positional() {
        argumentType = Argument.ArgumentType.Positional;
        return this;
    }

    public ArgumentBuilder<T> named() {
        argumentType = Argument.ArgumentType.Named;
        return this;
    }

    public boolean isPositional() {
        if (argumentType == null) {
            throw new ArgumentException("Argument was not positional or named");
        }
        return argumentType.equals(Argument.ArgumentType.Positional);
    }

    public boolean isNamed() {
        if (argumentType == null) {
            throw new ArgumentException("Argument was not positional or named");
        }
        return argumentType.equals(Argument.ArgumentType.Named);
    }

    public ArgumentBuilder<T> validator(Function<T, Boolean> validator) {
        validators.add(validator);
        return this;
    }

    public ArgumentBuilder<T> choices(T... choices) {
        validator(t1 -> Arrays.stream(choices).anyMatch(t2 -> t1 == t2));
        return this;
    }

    public ArgumentBuilder<T> optional() {
        optional = true;
        return this;
    }

    public boolean isOptional() {
        return optional;
    }

    public ArgumentBuilder<T> defaultValue(T value) {
        this.defaultValue = value;
        return this;
    }

    public Optional<T> getDefault() {
        return Optional.ofNullable(defaultValue);
    }

    public Argument<T> build() {
        if (argumentType == null) {
            throw new ArgumentException("Argument was not positional or named");
        }
        return new Argument<T>(name, parser, validators, argumentType, defaultValue, optional);
    }
}
