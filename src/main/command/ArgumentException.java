package main.command;

/**
 * Exception triggered when a command doesn't have the desire parameter.
 * Can be expended for more robust catch.
 */
public class ArgumentException extends RuntimeException {
    public ArgumentException(String msg) {
        super(msg);
    }
}
