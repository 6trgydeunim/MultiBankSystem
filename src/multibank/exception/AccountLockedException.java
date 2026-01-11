package multibank.exception;

public class AccountLockedException extends Exception {
    public AccountLockedException(String msg) {
        super(msg);
    }
}
