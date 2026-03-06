// QUESTION:

/* 
Banking Management System
com.bank
    ├── accounts
    ├── customers
    ├── loans
    └── util

Package: com.bank.accounts
Create a class Account with:
accountNumber
balance

Methods: deposit(), withdraw()
Create a class SavingsAccount that extends Account:
Add interestRate
Method: calculateInterest()

Package: com.bank.customers
Create a class Customer with:
customerId
name

Method to link an Account
Package: com.bank.loans
Create a class Loan with:
loanAmount
Method: calculateEMI()

Try accessing members of Account and observe access control behavior.
 Package: com.bank.util
Create a utility class BankUtil with:
Static method to generate account number
Static method to validate minimum balance

Main Class
Create a main class BankApplication inside com.bank package that:
Creates Customer and Account objects
Performs deposit and withdrawal
Calculates interest
Uses static import for utility methods
Demonstrates package access behavior */

//BANKING MANAGEMENT SYSTEM USING JAVA
import java.util.Random;

// --- UTILITY CLASS ---
class BankUtil {
    public static String generateAccountNumber() {
        Random rand = new Random();
        return "ACCOUNT-" + (100000 + rand.nextInt(900000));
    }
    public static boolean validateMinimumBalance(double balance) {
        double MIN_BALANCE = 500.0;
        return balance >= MIN_BALANCE;
    }
}

class Account {
    protected String accountNumber;
    private double balance; 
    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: Rs." + amount + ". New Balance: Rs." + balance);
        }
    }
    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrew: Rs." + amount + ". New Balance: Rs." + balance);
        } else {
            System.out.println("Withdrawal failed. Insufficient funds or invalid amount.");
        }
    }
    public double getBalance() {
        return balance;
    }
}

class SavingsAccount extends Account {
    private double interestRate;
    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance); 
        this.interestRate = interestRate;
    }
    public double calculateInterest() {
        System.out.println("Calculating interest for account: " + this.accountNumber);
        return getBalance() * (interestRate / 100);
    }
}

class Customer {
    private String customerId;
    private String name;
    private Account account; 
    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }
    public void linkAccount(Account account) {
        this.account = account;
        System.out.println("Account linked successfully to customer: " + name);
    }
    public Account getAccount() {
        return account;
    }
    public String getName() {
        return name;
    }
}

class Loan {
    private double loanAmount;
    public Loan(double loanAmount) {
        this.loanAmount = loanAmount;
    }
    public double calculateEMI(int tenureMonths, double annualInterestRate) {
        double monthlyRate = annualInterestRate / 12 / 100;
        return (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) 
                / (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    }
    public void testAccessControl(Account acc) {
        System.out.println("--- Access Control Test from Loan Class ---");
        System.out.println("Public access OK: " + acc.getBalance());

        //System.out.println(acc.balance); 
        System.out.println("Protected access works here (same default package): " + acc.accountNumber);
    }
}

// --- MAIN CLASS ---
public class Main {
    public static void main(String[] args) {
        System.out.println(" Welcome to the Banking Management System \n");
        // 1. Generate an account number
        String newAccNumber = BankUtil.generateAccountNumber();
        
        // 2. Create Customer and Account objects
        Customer hello = new Customer("CUSTOMER-01", "HELLO");
        SavingsAccount helloAccount = new SavingsAccount(newAccNumber, 1000.0, 4.5);
        hello.linkAccount(helloAccount);
        
        // 3. Perform deposit and withdrawal
        System.out.println("\n Performing Transactions ");
        helloAccount.deposit(500.0);
        helloAccount.withdraw(200.0);

        // 4. Validate Minimum Balance
        boolean isValid = BankUtil.validateMinimumBalance(helloAccount.getBalance());
        System.out.println("Meets minimum balance requirement? " + isValid);

        // 5. Calculate Interest
        double interest = helloAccount.calculateInterest();
        System.out.println("Estimated Annual Interest: Rs." + interest);

        // 6. Loan & Access Control
        System.out.println("\n Loan EMI ");
        Loan helloLoan = new Loan(50000.0);
        System.out.printf("Monthly EMI for $50k loan (12 months, 8%%): $%.2f\n", helloLoan.calculateEMI(12, 8.0));
        helloLoan.testAccessControl(helloAccount);
    }
}
