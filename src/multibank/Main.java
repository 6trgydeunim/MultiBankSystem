package multibank;

import java.util.ArrayList;
import java.util.Scanner;
import multibank.model.*;

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
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Aadhaar (12 digits): ");
                    String aadhaar = sc.nextLine();
                    if (!aadhaar.matches("\\d{12}")) {
                        System.out.println("Invalid Aadhaar!");
                        break;
                    }

                    System.out.print("DOB: ");
                    String dob = sc.nextLine();

                    System.out.print("Set password: ");
                    String pwd = sc.nextLine();

                    System.out.print("Initial funding: ");
                    double bal = sc.nextDouble();

                    Account acc = new Account(bal);
                    Customer cust = new Customer(name, aadhaar, dob, pwd, acc);
                    customers.add(cust);

                    System.out.println("Customer added successfully!");
                    System.out.println("Account Number: " + acc.getAccountNo());
                    break;

                case 2: // DELETE CUSTOMER
                    System.out.print("Account number: ");
                    int delAcc = sc.nextInt();

                    customers.removeIf(c ->
                            c.getAccount().getAccountNo() == delAcc);
                    System.out.println("Customer deleted (if existed)");
                    break;

                case 3: // WITHDRAW
                    Customer wc = findCustomer(customers, sc);
                    if (wc == null) break;

                    System.out.print("Enter password: ");
                    if (!wc.verifyPassword(sc.nextLine())) {
                        System.out.println("Wrong password!");
                        break;
                    }

                    System.out.print("Amount: ");
                    wc.getAccount().withdraw(sc.nextDouble());
                    System.out.println("Withdrawal successful");
                    break;

                case 4: // DEPOSIT
                    Customer dc = findCustomer(customers, sc);
                    if (dc == null) break;

                    System.out.print("Enter password: ");
                    if (!dc.verifyPassword(sc.nextLine())) {
                        System.out.println("Wrong password!");
                        break;
                    }

                    System.out.print("Amount: ");
                    dc.getAccount().deposit(sc.nextDouble());
                    System.out.println("Deposit successful");
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
