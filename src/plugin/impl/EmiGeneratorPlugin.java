package plugin.impl;

import plugin.BankPlugin;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.DecimalFormat;

public class EmiGeneratorPlugin implements BankPlugin {

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        double principal = 0, rate = 0;
        int months = 0;

        while (true) {
            try {
                System.out.print("Enter loan amount: ");
                principal = sc.nextDouble();
                if (principal <= 0) { System.out.println("Amount must be > 0"); continue; }

                System.out.print("Enter annual interest rate (%): ");
                rate = sc.nextDouble();
                if (rate <= 0) { System.out.println("Rate must be > 0"); continue; }

                System.out.print("Enter tenure in months: ");
                months = sc.nextInt();
                if (months <= 0) { System.out.println("Months must be > 0"); continue; }

                break; // valid input
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter numbers only.");
                sc.nextLine();
            }
        }

        double monthlyRate = rate / 12 / 100;
        double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) /
                     (Math.pow(1 + monthlyRate, months) - 1);

        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Monthly EMI: " + df.format(emi));
        System.out.println("✅ EMI calculation completed!");
    }
}
