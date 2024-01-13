

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Store store = storeSetup();
        Player player = new Player(100.0);
        Scanner scanner = new Scanner(System.in);

        store.displayInventory();
        gamePlayLoop(scanner, store, player);

        scanner.close();
    }

    private static void gamePlayLoop(Scanner scanner, Store store, Player player) {
        while (true) {
            System.out.println("\nEnter a command (1 to enter the store, 4 to exit):");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                store.enter(player);
                storeMenuLoop(scanner, store, player);
                store.exit(player);
            } else if (input.equals("4")) {
                System.out.println("Exiting the program...");
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    public static Store storeSetup() {
        Item sword = new Item("Sword", 10.0);
        Item potion = new Item("Health Potion", 5.0);
        Item hat = new Item("Hat", 1);

        Store store = new Store();
        store.addItem(sword);
        store.addItem(potion);
        store.addItem(hat);

        return store;
    }

    public static void storeMenuLoop(Scanner scanner, Store store, Player player) {
        while (true) {
            System.out.println("\nStore Menu:");
            System.out.println("1. Buy an item");
            System.out.println("2. Sell an item");
            System.out.println("3. Display inventory");
            System.out.println("4. Exit store");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                buyItemAction(scanner, store, player);
            } else if (input.equals("2")) {
                sellItemAction(scanner, store, player);
            } else if (input.equals("3")) {
                store.displayInventory();
            } else if (input.equals("4")) {
                System.out.println("Exiting the store...");
                store.exit(player);
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    //PA5 version
    private static void sellItemAction(Scanner scanner, Store store, Player player) {

        System.out.println("Enter the name of the item you want to sell:");
        String itemName = scanner.nextLine();
        Item item = player.getItemByName(itemName);
        try {
            //store.sellItem(item, player);
            player.sellUsingEscrow(item,store);
            System.out.println("Item sold successfully!");
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Item not found in your inventory.");
        }
    }

    //PA5 version
    public static void buyItemAction(Scanner scanner, Store store, Player player) {
        store.displayInventory();
        System.out.println("Enter the name of the item you want to buy:");
        String itemName = scanner.nextLine();
        Item item = store.getItemByName(itemName);
        if (item != null) {
            try {
                //store.buyItem(item, player);
                player.buyUsingEscrow(item, store);
                System.out.println("Item purchased successfully!");
            } catch (Exception e) {
                System.out.println("Could not purchase the desired item.");
            }
        } else {
            System.out.println("Item not available in the store.");
        }

    }

    public static void exposeGameSetup() {
        // TODO not sure what's supposed to be here
    }

    public static void exposeGamePlay() {
        // TODO again not entirely sure what is supposed to be here
    }

    public static void exposeGameStop() {
        
    }
}