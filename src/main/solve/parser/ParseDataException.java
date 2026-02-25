package parser;

public class ParseDataException extends Exception {
    public ParseDataException(String message) {
        super(message);
    }

    public ParseDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
