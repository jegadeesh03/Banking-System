import plugin.PluginManager;

import java.util.Scanner;

public class Plugin {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PluginManager manager = new PluginManager();

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Run Plugins");
            System.out.println("0. Exit");
            System.out.println("Enter a choice:");

            int choice = sc.nextInt();
            if (choice == 0) {
                System.out.println("Exiting app...");
                break;
            } else if (choice == 1) {
            	
                manager.showMenu();
            } else {
                System.out.println("Invalid choice");
            }
        }
    }
}
