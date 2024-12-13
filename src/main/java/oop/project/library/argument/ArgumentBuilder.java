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
     * @return Named or Positional
     * @throws ArgumentException if the argument is neither named nor positional.
     */
    public Argument.ArgumentType type() {
        if (argumentType == null) {
            throw new ArgumentException(name + " is not set to positional or named");
        }
        return this.argumentType;
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
     * @return marks this argument as non-required.
     */
    public ArgumentBuilder<T> optional() {
        optional = true;
        return this;
    }

    /**
     * @return whether the argument is marked as non-required.
     */
    public boolean isOptional() {
        return optional;
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
    public Argument<T> build() {
        if (argumentType == null) {
            throw new ArgumentException("Argument was not positional or named");
        }
        return new Argument<T>(parser, validators, name, argumentType, optional, defaultValue);
    }
}
