package edu.postech.csed332.homework1;

/**
 * A simple interest account. Given the current balance B, the annual interest
 * rate r and the elapsed date d, the new balance is B * (1 + r * d/365).
 */
class SimpleInterestAccount extends AbstractAccount implements Account {
    public SimpleInterestAccount(int accountNumber, String name, double initial, double rate) {
        super(accountNumber, name, initial, rate);
    }

    public void elapseTime(int elapsedDate) {
        double annualInterestRate = getAnnualInterestRate();
        double elapsedYear = elapsedDate/(double)365;
        double result = getBalance()*(1+annualInterestRate*elapsedYear);
        setBalance(result);
    }
}
