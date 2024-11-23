package oop.project.library.argument;

public class ValidateException extends RuntimeException {
    String error;
    public ValidateException(String s) {
        this.error = s;
    }
}
