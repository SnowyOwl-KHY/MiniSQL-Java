package interpreter.exception;

public class SyntaxErrorException extends CommandParseException {
    public SyntaxErrorException(String message) {
        super(message);
    }
}
