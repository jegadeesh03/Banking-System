package plugin;

import plugin.impl.LoanCalculatorPlugin;
import plugin.impl.EmiGeneratorPlugin;
import plugin.impl.CurrencyConverterPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PluginManager {

    private List<BankPlugin> plugins = new ArrayList<>();

    public PluginManager() {
        plugins.add(new LoanCalculatorPlugin());
        plugins.add(new EmiGeneratorPlugin());
        plugins.add(new CurrencyConverterPlugin());
    }

    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- PLUGIN MENU ---");
            for (int i = 0; i < plugins.size(); i++) {
                System.out.println((i + 1) + ". " + plugins.get(i).getClass().getSimpleName());
            }
            System.out.println("0. Back");
            System.out.println("Enter your choice:");

            int choice = sc.nextInt();
            if (choice == 0) break;
            if (choice > 0 && choice <= plugins.size()) {
                plugins.get(choice - 1).execute();
            } else {
                System.out.println("Invalid choice");
            }
        }
    }
}
