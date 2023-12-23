package edu.postech.csed332.homework1;

public abstract class AbstractAccount implements Account {
    protected int accountNumber;
    private String name;
    private double balance;
    private double annualInterestRate;


    public AbstractAccount(int accountNumber, String name, double initial, double rate) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = initial;
        this.annualInterestRate = rate;
    }

    @Override
    public int getAccountNumber() {
        return accountNumber;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getOwner() {
        return name;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) throws IllegalStateException {
        if(amount < 0) {
            throw new IllegalStateException("Amount must be nonnegative");
        }
        if(Double.compare(balance, amount) < 0) {
            throw new IllegalStateException("Insufficient Balance");
        }
        balance -= amount;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAnnualInterestRate() {
        return this.annualInterestRate;
    }

}
