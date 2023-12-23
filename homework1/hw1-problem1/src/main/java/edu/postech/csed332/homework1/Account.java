package edu.postech.csed332.homework1;

/**
 * This interface contains the information needed to represent an account.
 */
interface Account {
    /**
     * Return the account number.
     *
     * @return the account number
     */
    int getAccountNumber();

    /**
     * Return the current balance of the account.
     *
     * @return the balance
     */
    double getBalance();

    /**
     * Return the owner of the account.
     *
     * @return the owner
     */
    String getOwner();

    /**
     * Update the balance of the account after the given number of days,
     * according to the interest rate and the account type.
     *
     * @param numDays the number of days elapsed
     */
    void elapseTime(int numDays);

    /**
     * Deposit a given amount of money to the account.
     *
     * @param amount the amount to deposit
     */
    void deposit(double amount);

    /**
     * Withdraw a given amount of money from the account.
     *
     * @param amount the amount to withdraw
     * @throws IllegalStateException if the balance is not enough
     */
    void withdraw(double amount) throws IllegalStateException;
}


