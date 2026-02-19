package plugin.impl;

import plugin.BankPlugin;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.DecimalFormat;

public class CurrencyConverterPlugin implements BankPlugin {

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        double amount = 0;

        // Loop until valid input
        while (true) {
            try {
                System.out.print("Enter amount in USD: ");
                amount = sc.nextDouble();
                if (amount <= 0) {
                    System.out.println("❌ Amount must be greater than 0.");
                    continue;
                }
                break; // valid input
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter numbers only.");
                sc.nextLine(); // clear invalid input
            }
        }

        // Conversion example: USD → INR
        double inr = amount * 82.5; // conversion rate example
        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println("💵 " + df.format(amount) + " USD = ₹" + df.format(inr) + " INR");
        System.out.println("✅ Currency conversion completed successfully!");
    }
}
