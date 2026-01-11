package multibank.model;

public class Account {

    private static int accCounter = 1000;
    private int accountNo;
    private double balance;
    private boolean locked;

    public Account(double balance) {
        this.accountNo = ++accCounter;
        this.balance = balance;
        this.locked = false;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isLocked() {
        return locked;
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    public void deposit(double amt) {
        balance += amt;
    }

    public void withdraw(double amt) {
        balance -= amt;
    }
}
