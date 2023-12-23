package edu.postech.csed332.homework1;

/**
 * A compound interest account. Given the current balance B, the annual interest
 * rate r and the elapsed date d, the new balance is B * (1 + r)^(d/365)
 */
class CompoundInterestAccount extends AbstractAccount implements Account {

    public CompoundInterestAccount(int accountNumber, String name, double initial, double rate) {
        super(accountNumber, name, initial, rate);
    }

    public void elapseTime(int elapsedDate) {
        double annualInterestRate = getAnnualInterestRate();
        double elapsedYear = elapsedDate/(double)365;
        double result = getBalance()*Math.pow(1+annualInterestRate, elapsedYear);
        setBalance(result);
    }
}
