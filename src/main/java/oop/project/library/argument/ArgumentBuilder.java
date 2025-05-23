package oop.project.library.argument;

import oop.project.library.parsing.Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Sets all the configurable fields of an Argument.
 *
 * @param <T> The type that the Argument will parse.
 */
public class ArgumentBuilder<T> {
    private final String name;
    private final Parser<T> parser;
    private final List<Argument.Validator<T>> validators;
    private Argument.ArgumentType argumentType;
    private boolean optional;
    private T defaultValue;

    /**
     * @param argumentName The name that will be used to extract the value later.
     * @param parser       Used to convert a String to a T.
     */
    public ArgumentBuilder(String argumentName, Parser<T> parser) {
        name = argumentName;
        argumentType = null;
        validators = new ArrayList<>();
        this.parser = parser;
        this.optional = false;
        this.defaultValue = null;
    }

    /**
     * @return The name of the argument.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Sets this argument as a positional argument. Overrides any previous call to named().
     */
    public ArgumentBuilder<T> positional() {
        argumentType = Argument.ArgumentType.Positional;
        return this;
    }

    /**
     * @return Sets this argument as a named argument. Overrides any previous call to positional().
     */
    public ArgumentBuilder<T> named() {
        argumentType = Argument.ArgumentType.Named;
        return this;
    }

    /**
     * @param validator a predicate that any input must pass.
     */
    public ArgumentBuilder<T> validator(Predicate<T> validator) {
        return this.validator(null, validator);
    }

    /**
     * @param message   This will be included in the error if the predicate fails
     * @param validator a predicate that any input must pass.
     */
    public ArgumentBuilder<T> validator(String message, Predicate<T> validator) {
        validators.add(new Argument.Validator<>(message, validator));
        return this;
    }


    /**
     * @param choices the input must be among the values passed to choices.
     */
    public ArgumentBuilder<T> choices(T... choices) {
        validator("Invalid choice selected", t -> Arrays.asList(choices).contains(t));
        return this;
    }

    /**
     * Input must be between low and high. It is an error to use this with types that aren't integers.
     *
     * @param low  minimum allowed value (inclusive)
     * @param high maximum allowed value (inclusive)
     */
    public ArgumentBuilder<T> range(int low, int high) {
        validator("Value was not in range " + low + " to " + high, v -> low <= (int) v && (int) v <= high);
        return this;
    }

    /**
     * @return marks this argument as non-required.
     */
    public ArgumentBuilder<T> optional(T value) {
        this.defaultValue = value;
        optional = true;
        return this;
    }

    /**
     * @param value This is value is used if the argument is optional and no input is provided.
     */
    public ArgumentBuilder<T> defaultValue(T value) {
        this.defaultValue = value;
        return this;
    }

    /**
     * @return Converts the builder into an Argument.
     */
    public Argument<T> build() throws ArgumentException {
        if (argumentType == null) {
            throw new ArgumentException("Argument was not positional or named");
        }
        return new Argument<T>(parser, validators, name, argumentType, optional, defaultValue);
    }
}
