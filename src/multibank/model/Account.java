package multibank.model;

import multibank.exception.*;

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

    public void deposit(double amt) throws InvalidTransactionException, AccountLockedException {
        if (locked) {
            throw new AccountLockedException("Account is locked");
        }
        if (amt <= 0) {
            throw new InvalidTransactionException("Deposit amount must be positive");
        }
        balance += amt;
    }

    public void withdraw(double amt) throws OverdraftException, InvalidTransactionException, AccountLockedException {
        if (locked) {
            throw new AccountLockedException("Account is locked");
        }
        if (amt <= 0) {
            throw new InvalidTransactionException("Withdrawal amount must be positive");
        }
        if (amt > balance) {
            throw new OverdraftException("Insufficient funds");
        }
        balance -= amt;
    }
}
