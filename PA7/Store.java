

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import java.util.Scanner;

class Store {
    static Logger logger = LogManager.getLogger(Player.class);
    private List<Item> inventory;
    private List<Player> players_in_store; 

    public Store() {
        inventory = new ArrayList<>();
        players_in_store = new ArrayList<>();
    }

    public void enter(Player player){
        if (check_player_in_store(player) == false){
            players_in_store.add(player);
        } else {
            System.out.println("Player is already in the store.");
        }
    }

    public void exit(Player player){
         if (check_player_in_store(player) == true){
            players_in_store.remove(player);
        } else {
            System.out.println("Player never entered the store.");
        }
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public void displayInventory() {
        System.out.println("Store Inventory:");
        for (Item item : inventory) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
    }

    private boolean check_player_in_store(Player player){
        int index =  players_in_store.indexOf(player);
        if (index == -1) {
            logger.warn("Player not in store");
            return false;
        } else {
            return true;
        }
    }

    public Item getItemByName(String name) {
        // Iterate through the player's items and return the item with the matching name
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null; // Item not found in the player's inventory
    }

    //from PA4
    public void buyItem(Item item, Player player) throws FailedTransactionException {
        if (check_player_in_store(player) == false){
            System.out.println("Player needs to enter the store before being able to buy anything");
            throw new FailedTransactionException();
        }
        
        if (inventory.contains(item)) {
            try{
                player.removeMoney(item.getPrice());
                inventory.remove(item);
                player.addItem(item);
            }catch (Exception e){
                System.out.println("Purchase Unsuccessful");
            }
        } else {
            System.out.println("Item not available in the store.");
            throw new FailedTransactionException();
        }
    }

     
    public void sellItem(Item item, Player player) throws FailedTransactionException {
         if (check_player_in_store(player) == false){
            System.out.println("Player needs to enter the store before being able to sell anything");
            //throw new FailedTransactionException()q;
        }
        player.removeItem(item);
        player.addMoney(item.getPrice());
        inventory.add(item);
    }
    
    
    public void customerBuyUsingEscrow() throws FailedTransactionException{
        try{
        sellUsingEscrow();
        }catch(Exception e){
            throw new FailedTransactionException();
        }
    }

    private void sellUsingEscrow() throws FailedTransactionException{
        Escrow.escrowItem(Escrow.requestItem);
        if (!inventory.remove(Escrow.requestItem)){
            throw new FailedTransactionException();
        }
    };

    

    public void customerSellUsingEscrow(){
        buyUsingEscrow();
    }

    private void buyUsingEscrow(){
        Escrow.escrowMoney(Escrow.itemInEscrow.getPrice());
        inventory.add(Escrow.receiveItem());
    };

    
}
