package multibank.exception;

public class InvalidTransactionException extends Exception {
    public InvalidTransactionException(String msg) {
        super(msg);
    }
}
