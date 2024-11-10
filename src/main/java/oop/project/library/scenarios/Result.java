package oop.project.library.scenarios;

import java.util.function.Function;

public sealed interface Result<T> {

    record Success<T>(T value) implements Result<T> {

        @Override
        public <O> Result<O> map(Function<T, O> f) {
            return new Success<>(f.apply(value));
        }

        @Override
        public <O> Result<O> bind(Function<T, Result<O>> f) {
            return f.apply(value);
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
    }

    public <O> Result<O> map(Function<T, O> f);
    public <O> Result<O> bind(Function<T, Result<O>> f);

}
