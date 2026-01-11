package multibank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import multibank.model.*;
import multibank.exception.*;

public class Main {

    private static final String ADMIN_PASSWORD = "Admin123";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Customer> customers = new ArrayList<>();

        System.out.print("Enter admin password: ");
        if (!sc.nextLine().equals(ADMIN_PASSWORD)) {
            System.out.println("Access denied!");
            return;
        }

        while (true) {

            System.out.println("\n===== ADMIN PANEL =====");
            System.out.println("1. Add Customer");
            System.out.println("2. Delete Customer");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Deposit Money");
            System.out.println("5. Lock / Unlock Account");
            System.out.println("6. KYC");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1: // ADD CUSTOMER
                    try {
                        System.out.print("Name: ");
                        String name = sc.nextLine();

                        System.out.print("Aadhaar (12 digits): ");
                        String aadhaar = sc.nextLine();
                        if (!aadhaar.matches("\\d{12}")) {
                            throw new InvalidTransactionException("Invalid Aadhaar");
                        }

                        // check duplicate aadhaar
                        for (Customer c : customers) {
                            if (c.getAadhaar().equals(aadhaar)) {
                                throw new InvalidTransactionException("Aadhaar already registered");
                            }
                        }

                        System.out.print("DOB: ");
                        String dob = sc.nextLine();

                        System.out.print("Initial funding: ");
                        double bal = sc.nextDouble();
                        sc.nextLine();

                        if (bal < 0) {
                            throw new InvalidTransactionException("Initial funding must be non-negative");
                        }

                        Account acc = new Account(bal);
                        Customer cust = new Customer(name, aadhaar, dob, acc);
                        customers.add(cust);

                        System.out.println("Customer added successfully!");
                        System.out.println("Account Number: " + acc.getAccountNo());
                    } catch (InvalidTransactionException e) {
                        System.out.println("Add Customer Error: " + e.getMessage());
                    }
                    break;

                case 2: // DELETE CUSTOMER
                    if (customers.isEmpty()) {
                        System.out.println("No customers available to delete");
                        break;
                    }
                    try {
                        System.out.print("Account number: ");
                        int delAcc = sc.nextInt();
                        sc.nextLine();

                        boolean removed = false;
                        Iterator<Customer> it = customers.iterator();
                        while (it.hasNext()) {
                            Customer c = it.next();
                            if (c.getAccount().getAccountNo() == delAcc) {
                                it.remove();
                                removed = true;
                                break;
                            }
                        }

                        if (!removed) {
                            throw new InvalidTransactionException("Account not found");
                        }

                        System.out.println("Customer deleted successfully");
                    } catch (InvalidTransactionException e) {
                        System.out.println("Delete Error: " + e.getMessage());
                    }
                    break;

                case 3: // WITHDRAW
                    Customer wc = findCustomer(customers, sc);
                    if (wc == null) break;

                    System.out.print("Amount: ");
                    double wAmt = sc.nextDouble();
                    try {
                        wc.getAccount().withdraw(wAmt);
                        System.out.println("Withdrawal successful");
                    } catch (OverdraftException e) {
                        System.out.println("Overdraft Error: " + e.getMessage());
                    } catch (InvalidTransactionException e) {
                        System.out.println("Invalid Transaction: " + e.getMessage());
                    } catch (AccountLockedException e) {
                        System.out.println("Account Error: " + e.getMessage());
                    }
                    break;

                case 4: // DEPOSIT
                    Customer dc = findCustomer(customers, sc);
                    if (dc == null) break;

                    System.out.print("Amount: ");
                    double dAmt = sc.nextDouble();
                    try {
                        dc.getAccount().deposit(dAmt);
                        System.out.println("Deposit successful");
                    } catch (InvalidTransactionException e) {
                        System.out.println("Invalid Transaction: " + e.getMessage());
                    } catch (AccountLockedException e) {
                        System.out.println("Account Error: " + e.getMessage());
                    }
                    break;

                case 5: // LOCK / UNLOCK
                    Customer lc = findCustomer(customers, sc);
                    if (lc == null) break;

                    System.out.print("Enter name for verification: ");
                    if (!sc.nextLine().equals(lc.getName())) {
                        System.out.println("Name mismatch!");
                        break;
                    }

                    if (lc.getAccount().isLocked()) {
                        lc.getAccount().unlock();
                        System.out.println("Account unlocked");
                    } else {
                        lc.getAccount().lock();
                        System.out.println("Account locked");
                    }
                    break;

                case 6: // KYC
                    Customer kc = findCustomer(customers, sc);
                    if (kc != null) {
                        kc.completeKYC();
                        System.out.println("KYC completed");
                    }
                    break;

                case 7:
                    System.out.println("Logged out");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static Customer findCustomer(ArrayList<Customer> list, Scanner sc) {
        System.out.print("Enter account number: ");
        int accNo = sc.nextInt();
        sc.nextLine();

        for (Customer c : list)
            if (c.getAccount().getAccountNo() == accNo)
                return c;

        System.out.println("Account not found!");
        return null;
    }
}
