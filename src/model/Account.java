package model;

public class Account {

    private int customerId;
    private double balance;
    private double dailyLimit;
    private double minBalance;

    public Account(int customerId, double balance) {
        this.customerId = customerId;
        this.balance = balance;
        this.dailyLimit = 50000;
        this.minBalance = 2000;
    }

    // Getters
    public int getCustomerId() {
        return customerId;
    }

    public double getBalance() {
        return balance;
    }

    public double getDailyLimit() {
        return dailyLimit;
    }

    public double getMinBalance() {
        return minBalance;
    }

    // 🔹 REQUIRED FOR ACID ENGINE

    // Used for rollback
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Debit with rules
    public void debit(double amount) {

        if (amount <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        if (amount > dailyLimit) {
            throw new RuntimeException("Daily limit exceeded");
        }

        if (balance - amount < minBalance) {
            throw new RuntimeException("Minimum balance rule violated");
        }

        balance -= amount;
    }

    // Credit
    public void credit(double amount) {

        if (amount <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        balance += amount;
    }
}
