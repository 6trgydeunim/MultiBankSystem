package multibank.service;

import multibank.model.Customer;
import multibank.exception.*;

public class Transaction {

    public static void process(Customer customer, double amount) {

        try {
            System.out.println("Transaction started");

            try {
                customer.getAccount().withdraw(amount);
            }
            catch (OverdraftException e) {
                System.out.println("Overdraft Error: " + e.getMessage());
            }
            catch (InvalidTransactionException e) {
                System.out.println("Invalid Transaction: " + e.getMessage());
            }

        }
        catch (AccountLockedException e) {
            System.out.println("Account Error: " + e.getMessage());
        }
        finally {
            System.out.println("Transaction ended\n");
        }
    }
}
