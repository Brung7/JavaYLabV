package by.vladimir.exception;

public class ReturnToMainException extends Exception{
    public ReturnToMainException() {
        super("Returning to main loop");
    }
}
