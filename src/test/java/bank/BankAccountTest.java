package bank;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BankAccountTest {

    private double calculateFragmentPayment(double total_amount, double interest, int npayments){
        double totalInterest = Math.pow((1+interest), npayments);
        double numerator = total_amount * (interest * totalInterest);
        double denominator = totalInterest - 1;
        return (numerator / denominator);
    }

    @Test
    public void Constructor_InitialBalance_ReturnsInitialBalance() {
        int balance = 100;
        BankAccount bankAccount = new BankAccount(balance);

        int result = bankAccount.getBalance();

        assertEquals(balance, result);
    }

    @Test
    public void WithDraw_BalanceGreaterThanAmount_ReturnsTrue() {
        int balance = 100;
        int amount = 60;
        BankAccount bankAccount = new BankAccount(balance);

        boolean result = bankAccount.withdraw(amount);

        assertTrue(result);
    }

    @Test
    public void WithDraw_BalanceLessThanAmount_ReturnsFalse() {
        int balance = 50;
        int amount = 60;
        BankAccount bankAccount = new BankAccount(balance);

        boolean result = bankAccount.withdraw(amount);

        assertFalse(result);
    }

    @Test
    public void Deposit_AmountGreaterThanZero_ReturnSumBalanceWithAmount() {
        int balance = 50;
        int amount = 60;
        int expectedResult = balance + amount;
        BankAccount bankAccount = new BankAccount(balance);

        int result = bankAccount.deposit(amount);

        assertEquals(expectedResult, result);
    }

    @Test
    public void Deposit_AmountLessThanZero_ThrowIllegalArgumentException() {
        int amount = -4;
        BankAccount bankAccount = new BankAccount(50);

        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(amount));
    }

    @Test
    void Payment_WithTotalAmountAndInterestAndNpayments_ReturnTheCorrectPayment(){
        double total_amount = 50;
        double interest = 1.5;
        int npayments = 3;
        double expectedResult = calculateFragmentPayment(total_amount, interest, npayments);
        BankAccount bankAccount = new BankAccount(50);

        double  result = bankAccount.payment(total_amount,interest, npayments);

        assertEquals(expectedResult, result, 0.001);
    }

    @Test
    void Payment_WithInterestAndNpaymentsLessThanZero_ThrowIllegalArgumentException(){
        double total_amount = 50;
        double interest = -1;
        int npayments = -2;
        BankAccount bankAccount = new BankAccount(50);

        assertThrows(IllegalArgumentException.class, () -> bankAccount.payment(total_amount,interest, npayments));
    }

    @Test
    void Pending_WithAllArgumentsPositives_ReturnPendingAmount(){
        double total_amount = 50;
        double interest = 1.5;
        int npayments = 3;
        int month = 2;
        double expectedResultMonth1 = total_amount -
                (calculateFragmentPayment(total_amount, interest, npayments) - interest * total_amount);
        double expectedResult = expectedResultMonth1 -
                (calculateFragmentPayment(total_amount, interest, npayments) - interest * expectedResultMonth1);
        BankAccount bankAccount = new BankAccount(50);

        double result = bankAccount.pending(total_amount, interest, npayments, month);

        assertEquals(expectedResult, result, 0.001);
    }

    @Test
    void Pending_WithAllArgumentsPositives_ThrowIllegalArgumentException(){
        double total_amount = -2;
        double interest = -4;
        int npayments = -3;
        int month = -1;
        BankAccount bankAccount = new BankAccount(50);

        assertThrows(IllegalArgumentException.class, () -> bankAccount.pending(total_amount,interest, npayments, month));
    }

}