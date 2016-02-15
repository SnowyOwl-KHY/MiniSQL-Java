package interpreter.exception;

public abstract class CommandParseException extends Exception {

    public CommandParseException() {
    }

    public CommandParseException(String message) {
        super(message);
    }

    public void printMessage() {
        System.err.println(getMessage());
    }

}
