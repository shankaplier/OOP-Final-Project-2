package oop.project.library.scenarios;

import java.util.function.Function;

public sealed interface Result<T> {

    public <O> Result<O> map(Function<T, O> f);

    public <O> Result<O> bind(Function<T, Result<O>> f);

    public Result<T> guard(String message, Function<T, Boolean> f);

    record Success<T>(T value) implements Result<T> {

        @Override
        public <O> Result<O> map(Function<T, O> f) {
            return new Success<>(f.apply(value));
        }

        @Override
        public <O> Result<O> bind(Function<T, Result<O>> f) {
            return f.apply(value);
        }

        @Override
        public Result<T> guard(String message, Function<T, Boolean> f) {
            if (f.apply(value)) {
                return new Success<>(value);
            } else {
                return new Failure<>(message);
            }
        }
    }

    record Failure<T>(String error) implements Result<T> {
        @Override
        public <O> Result<O> map(Function<T, O> f) {
            return new Failure<>(error);
        }

        @Override
        public <O> Result<O> bind(Function<T, Result<O>> f) {
            return new Failure<>(error);
        }

        @Override
        public Result<T> guard(String message, Function<T, Boolean> f) {
            return new Failure<>(error);
        }
    }

}
