package edu.postech.csed332.homework1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    Bank wb;

    @BeforeEach
    void setup() {
        wb = new Bank();
    }

    @Test
    void testCreateAccount() {
        var account = wb.createAccount("Thomas", 90000, 0.01, false);
        assertNotNull(account);
        assertEquals(account.getClass(), SimpleInterestAccount.class);
        assertEquals(90000, account.getBalance());
        assertEquals("Thomas", account.getOwner());
        assertEquals(100000, account.getAccountNumber());
    }

    @Test
    void testCreateSecondAccount() {
        wb.createAccount("Thomas", 90000, 0.01, false);
        var account = wb.createAccount("Peter", 80000, 0.02, true);
        assertNotNull(account);
        assertEquals(account.getClass(), CompoundInterestAccount.class);
        assertEquals(80000, account.getBalance());
        assertEquals("Peter", account.getOwner());
        assertEquals(100001, account.getAccountNumber());
    }

    @Test
    void testTransferMoney() {
        var account1 = wb.createAccount("Thomas", 90000, 0.01, false);
        var account2 = wb.createAccount("Peter", 80000, 0.02, true);
        wb.transfer(account1, account2, 20000);
        assertEquals(70000, account1.getBalance());
        assertEquals(100000, account2.getBalance());
        wb.transfer(account2, account1, 10000);
        assertEquals(80000, account1.getBalance());
        assertEquals(90000, account2.getBalance());
    }

    @Test
    void testFindAccount() {
        wb.createAccount("Thomas", 90000, 0.01, false);
        wb.createAccount("Peter", 80000, 0.02, false);
        var reqAccount = wb.findAccount(100001);
        assertEquals("Peter", reqAccount.getOwner());
    }

    @Test
    void testCompoundInterestAccount() {
        wb.createAccount("Thomas", 90000, 0.01, true);
        var account = wb.findAccount(100000);
        assertNotNull(account);
        assertEquals(account.getClass(), CompoundInterestAccount.class);
        account.elapseTime(20);
        assertEquals(90049.08350, account.getBalance(), 0.01);
    }

    @Test
    void testSimpleInterestAccount() {
        wb.createAccount("Thomas", 90000, 0.01, false);
        var account = wb.findAccount(100000);
        assertNotNull(account);
        assertEquals(account.getClass(), SimpleInterestAccount.class);
        account.elapseTime(20);
        assertEquals(90049.31507, account.getBalance(), 0.01);
    }
    // testFindAccountsByName
    @Test
    void testFindAccountByName() {
        String name = "Thomas";
        List<Account> expected_accounts = new ArrayList<>();
        expected_accounts.add(wb.createAccount(name, 90000, 0.01, false));
        expected_accounts.add(wb.createAccount(name, 90001, 0.02, true));
        List<Account> accounts = wb.findAccountByName(name);
        // compare
        assertIterableEquals(accounts, expected_accounts);
    }

    @Test
    void testWithdrawShouldBeFailedWhenBalanceIsInsufficient() {
        String name = "Thomas";
        Account account = wb.createAccount(name, 10, 0.01, false);
        Assertions.assertThrows(IllegalStateException.class, () -> account.withdraw(11));
    }

    @Test
    void testWithdrawShouldBeFailedWhenAmountIsNegative() {
        String name = "Thomas";
        Account account = wb.createAccount(name, 10, 0.01, false);
        Assertions.assertThrows(IllegalStateException.class, () -> account.withdraw(-1));
    }
}

