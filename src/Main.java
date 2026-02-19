import model.Customer;
import model.User;

import service.AccountService;
import service.AuthService;
import service.CustomerService;
import service.TransactionService;

import service.impl.AccountServiceImpl;
import service.impl.AuthServiceImpl;
import service.impl.CustomerServiceImpl;
import service.impl.TransactionServiceImpl;
import session.SessionManager;
import session.SessionWatcherThread;
import thread.CacheFlushThread;
import util.CacheLoader;

import java.util.Scanner;
import cache.AccountCache;

public class Main {

    // 🔥 START BACKGROUND SYSTEMS ONCE
    static {
        try {
            System.out.println("[SYSTEM] Loading account cache from DB...");
            CacheLoader.loadAccounts();

            CacheFlushThread cacheThread = new CacheFlushThread();
            cacheThread.setDaemon(true);
            cacheThread.start();

            System.out.println("[SYSTEM] Cache flush thread started.");

        } catch (Exception e) {
            System.out.println("[SYSTEM] Startup failure: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        AuthService auth = new AuthServiceImpl();
        TransactionService ts = new TransactionServiceImpl();

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User u = auth.login(username, password);

        if (u == null) {
            System.out.println("❌ Invalid Login!");
            return;
        }

        System.out.println("✅ Welcome " + u.getUsername() + " | Role: " + u.getRole());

        // ---- ADMIN MENU ----
        if (u.getRole().equals("ADMIN")) {
        	
        	String sessionId = SessionManager.createSession(u.getUsername());
        	
        	SessionWatcherThread watcher = new SessionWatcherThread();
        	watcher.setDaemon(true);
        	watcher.start();

        	System.out.println("[SYSTEM] Session watcher started.");
        	

            while (true) {
            	if (!SessionManager.isSessionValid(sessionId)) {
                    System.out.println("⚠ Session expired. Please login again.");
                    break;
                }

                System.out.println("\n--- ADMIN MENU ---");
                System.out.println("1. Create Customer");
                System.out.println("2. Open Account");
                System.out.println("3. Transfer Money");
                System.out.println("4. LogOut");
                System.out.print("Enter choice: ");
                
               


                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        sc.nextLine();

                        System.out.print("Customer Name: ");
                        String name = sc.nextLine();

                        System.out.print("City: ");
                        String city = sc.nextLine();

                        System.out.print("Mobile: ");
                        String mobile = sc.nextLine();

                        Customer c = new Customer(name, city, mobile);
                        CustomerService cs = new CustomerServiceImpl();
                        cs.createCustomer(c, u.getUsername());

                        System.out.println("✅ Customer created successfully!");
                        break;

                    case 2:
                        sc.nextLine();

                        System.out.print("Customer ID: ");
                        int cid = sc.nextInt();

                        System.out.print("Initial Deposit: ");
                        double deposit = sc.nextDouble();

                        AccountService as = new AccountServiceImpl();
                        as.openAccount(cid, deposit, u.getUsername());

                        System.out.println("✅ Account opened successfully");
                        break;

                    case 3:
                        sc.nextLine();

                        System.out.print("From Account: ");
                        int from = sc.nextInt();

                        System.out.print("To Account: ");
                        int to = sc.nextInt();

                        System.out.print("Amount: ");
                        double amt = sc.nextDouble();

                        try {
                            ts.transfer(from, to, amt, u.getUsername());
                            System.out.println("✅ Transfer successful");
                        } catch (Exception e) {
                            System.out.println("❌ Transfer failed: " + e.getMessage());
                        }

                        // 🔍 DEBUG VIEW (CACHE SNAPSHOT)
//                        System.out.println("\n--- ACCOUNT CACHE SNAPSHOT ---");
//                        AccountCache.getSnapshot().forEach(
//                                (accId, balance) ->
//                                        System.out.println("Account ID: " + accId + " | Balance: " + balance)
//                        );
//                        System.out.println("--------------------------------");
                        System.out.println("\n--- LAST TRANSACTION SNAPSHOT ---");

                        Double fromBal = AccountCache.get(from);
                        Double toBal = AccountCache.get(to);

                        System.out.println("From Account: " + from + " | Balance: " + fromBal);
                        System.out.println("To Account: " + to + " | Balance: " + toBal);

                        System.out.println("--------------------------------");
                        break;

                    case 4:
                    	SessionManager.logout(sessionId);
                        System.out.println("👋 Logged out");
                        return;

                    default:
                        System.out.println("❌ Invalid choice");
                }
            }
        }
    }
}
