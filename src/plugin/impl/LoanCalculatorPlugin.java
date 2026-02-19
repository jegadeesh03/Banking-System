package plugin.impl;

import plugin.BankPlugin;
import java.util.Scanner;

public class LoanCalculatorPlugin implements BankPlugin {

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter loan amount: ");
        double amount = sc.nextDouble();
        System.out.print("Enter interest rate (%): ");
        double rate = sc.nextDouble();
        System.out.print("Enter years: ");
        int years = sc.nextInt();

        double interest = amount * rate * years / 100;
        System.out.println("Total interest: " + interest);
        System.out.println("Total repayment: " + (amount + interest));
    }
}
