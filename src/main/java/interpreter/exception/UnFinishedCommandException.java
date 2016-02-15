package interpreter.exception;

public class UnFinishedCommandException extends CommandParseException {
    public UnFinishedCommandException(String message) {
        super(message);
    }
}
