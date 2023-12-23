package edu.postech.csed332.homework1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A bank manages a collection of accounts. An account number is assigned
 * incrementally from 100000. E.g., the first account has 100000, the second
 * 100001, and so on. There are also functions to find specific accounts.
 */
public class Bank {

    private List<Account> accounts;
    private int accountNumber = 100_000;
    // Use the Java Collection Framework, including List, Map, Set, etc.

    /**
     * Create a bank. Initially, there is no account.
     */
    Bank() {
        accounts = new ArrayList<>();
    }

    /**
     * Find an account by a given account number.
     *
     * @param accNum an account number
     * @return the account with number accNum; null if no such account exists
     */
    Account findAccount(int accNum) {
        for(Account account: accounts) {
            if(account.getAccountNumber() == accNum) {
                return account;
            }
        }
        return null;
    }

    /**
     * Find all accounts owned by a given name
     *
     * @param name owner's name
     * @return a list of accounts sorted in ascending order by account number
     */
    List<Account> findAccountByName(String name) {
        List<Account> result =  new ArrayList<>();
        for(Account account: accounts) {
            if(account.getOwner() == name) {
                result.add(account);
            }
        }
        result.sort(Comparator.comparingInt(Account::getAccountNumber));
        return result;
    }

    /**
     * Create a new account and register it to the bank.
     *
     * @param name     owner name
     * @param initial  initial balance
     * @param rate     annual interest rate
     * @param compound true if the account has a compound interest
     * @return the newly created account; null if not possible
     */
    Account createAccount(String name, double initial, double rate, boolean compound) {
        Account account;
        if(compound == true) {
            account = new CompoundInterestAccount(accountNumber, name, initial, rate);
        }
        else {
            account = new SimpleInterestAccount(accountNumber, name, initial, rate);
        }
        accounts.add(account);
        accountNumber++;
        return account;
    }

    /**
     * Transfer a given amount of money from src account to dst account.
     *
     * @param src    source account
     * @param dst    destination account
     * @param amount of money
     * @throws IllegalStateException if not possible
     */
    void transfer(Account src, Account dst, double amount) throws IllegalStateException {
        // check whether src or dst exists
        // check balance in src account at least more than amount
        if(src == null || dst == null) {
            throw new IllegalStateException("Invalid Account");
        }
        try {
            src.withdraw(amount);
            dst.deposit(amount);
        } catch (Exception e) {
            throw new IllegalStateException("Withdraw Value ERROR");
        }
    }
}
