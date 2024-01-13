

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Player {
    static Logger logger = LogManager.getLogger(Player.class);
    private List<Item> inventory;
    private List<Item> holdInventory;
    private List<Item> wearInventory;
    private double money;

    public Player(double money){
        logger.info("New player created");
        holdInventory = new ArrayList<Item>();
        wearInventory = new ArrayList<Item>();
        inventory = new ArrayList<Item>();
        this.money = money;
        //Each player starts with a bag
        Item bag = new Item("Bag", 100.0);
        acquireItem(bag);
    }
    
    public void acquireItem(Item item){
        inventory.add(item);
    }

    public void relinquishItem(Item item) throws FailedTransactionException{
        removeHold(item);
        removeWear(item);
        if (!inventory.remove(item)){
            throw new FailedTransactionException();
        }
    }

    public double spendMoney(double amount) throws FailedTransactionException{
        if (this.money - amount < 0){
            //Transaction could not be completed
            throw new FailedTransactionException();
        } else {
            //Transaction was completed
            this.money -= amount;
            return amount;
        }
    }

    public void getMoney(double amount){
        //This refers to gaining money
        this.money += amount; 
    }
    
    public void removeMoney(double amount) throws FailedTransactionException{
        try{
        spendMoney(amount);
        }catch(Exception e){
            throw new FailedTransactionException();
        }
    }

    
    public void addMoney(double amount){
        getMoney(amount);
    }

    
    public void addItem(Item item){
        acquireItem(item);
    }

    //Main checks if the item in the player's inventory before sale goes through

    public void removeItem(Item item) throws FailedTransactionException{
        try{
        relinquishItem(item);
        } catch (Exception e){
            throw new FailedTransactionException();
        }
    }

    public Item getItemByName(String itemName){
        //item is looked for in inventory and returned else null is returned
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
    
    private int getItemInventoryLocation(Item item){
        //TODO is there a case if there are 2 objects
        return inventory.indexOf(item);
    }






    //Exposes
    public List<Item> exposeInventory(){
        return inventory;
    }

    public List<Item> exposeWearInventory(){
        return wearInventory;
    }

    public List<Item> exposeHoldInventory(){
        return holdInventory;
    }

    public List<Item> exposeEquipInventory(){
        List<Item> equipInventory = holdInventory;
        equipInventory.addAll(wearInventory);
        return equipInventory;
    }

    public List<Item> exposeEatInventory(){
        return null; //TODO what?
    }

    public List<Item> exposeDrinkInventory(){
        return null;
    }

    public List<Item> exposeConsumeInventory(){
        return null;
    }

    public void exposeCommonMethodConsume(Item item){
        eat(item);
    }

    public void exposeCommonMethodEquip(Item item){
        hold(item);
    }

    public void exposeCommonMethodUse(Item item){
        //TODO items do not have Types yet
    }






    //BASE Methods
    public void buyUsingEscrow(Item item, Store store){
        try {
            Escrow.escrowMoney(spendMoney(item.getPrice()));
            Escrow.requestItem(item);
            store.customerBuyUsingEscrow();
            acquireItem(Escrow.receiveItem());
            logger.info("Item successfully bought");
        } catch(Exception e){
            addMoney(Escrow.returnMoney());
            System.out.println("Could not purchase the desired item.");
        }
    }

    public void sellUsingEscrow(Item item, Store store){
        try{
            Escrow.escrowItem(item);
            removeItem(item);
            store.customerSellUsingEscrow();
            addMoney(Escrow.receiveMoney());
            finalizeEscrowSell();
        } catch (Exception e){
            Escrow.returnItem();
            System.out.println("Item could not be sold");
        }
    }

    public void finalizeEscrowSell(){
        Escrow.itemInEscrow = null;
        logger.info("Item succesfully sold");
    }

    public void hold(Item item){
        holdInventory.add(inventory.get(getItemInventoryLocation(item)));
    }

    public void wear(Item item){
        wearInventory.add(inventory.get(getItemInventoryLocation(item)));
    }

    private void removeHold(Item item){
        if (holdInventory.contains(item)){
            holdInventory.remove(item);
        }
    }

    private void removeWear(Item item) {
        if (wearInventory.contains(item)){
            wearInventory.remove(item);
        }
    }

    public void eat(Item item){
        removeHold(item);
        removeWear(item);
        inventory.remove(item);
    }

    public void drink(Item item){
        removeHold(item);
        removeWear(item);
        inventory.remove(item);
    }

    public void consume(Item item){
        removeHold(item);
        removeWear(item);
        inventory.remove(item);
    }

    public void equip(Item item){
        wearInventory.add(inventory.get(getItemInventoryLocation(item)));
    }

}    