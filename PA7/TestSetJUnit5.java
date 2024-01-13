import org.junit.jupiter.api.AfterAll; 
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.Exception;

public class TestSetJUnit5 {
  
  static Store store;
  static Player player;
  static Item item_0;
  static Item item_1;
  static Item item_2;
  static Item item_3;
  static Item expensiveItem;
  

  @BeforeAll
  static void setup() {
    store = new Store();

    item_0 = new Item("test0", 1.0);
    item_1 = new Item("test1", 1.0); 
    item_2 = new Item("test2", 1.0);
    item_3 = new Item("test3",1.0);
    expensiveItem = new Item("expensiveItem", 1200);
    

    player = new Player(100.0);

    store.addItem(item_0);
    store.addItem(item_1);
    store.addItem(item_2);
    store.addItem(item_3);
    store.addItem(expensiveItem);
    
  }
  
  //PA4
  @Test
  @Tag("buyItemPA4")
  void buyItemPA4() {
    try {
      store.enter(player);
      store.buyItem(item_0, player);
      store.buyItem(item_1, player);
      store.buyItem(item_2, player);
      
      //Case expected behavior
      assertTrue(item_0 == player.getItemByName("test0"), "First item purchased not found in Player's inventory");
      assertTrue(item_1 == player.getItemByName("test1"), "Second item purchased not found in Player's inventory");
      assertTrue(item_2 == player.getItemByName("test2"), "Third item purchased not found in Player's inventory");

      //Case item not in store
      Item fakeItem = new Item("fakeItem", 3);
      store.buyItem(fakeItem,player);
      //TODO what is the expected behavior, throws an exception or returns false, item was not sold
      assertTrue(null == player.getItemByName("fakeItem"), "Player purchased an item not available in store");

      //Case insufficient funds
      try{
        store.buyItem(expensiveItem,player);
      }
      catch(Exception e){}

      assertTrue(null == player.getItemByName("expensiveItem"), "Player with insufficient funds purchased item");

      assertTrue(expensiveItem == store.getItemByName("ExpensiveItem"), "Store inventory does not contain item after failed purchase");

      //Case player not in store
      store.exit(player);
      store.buyItem(item_3, player);
      assertTrue(null == player.getItemByName("item_3"), "Player not in store purchased an item");
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  
  @Test
  @Tag("buyItemPA5")
  void buyItemPA5() {
    try{    
    //PA5 version
    store.enter(player);
    player.buyUsingEscrow(item_0, store);
    player.buyUsingEscrow(item_1, store);
    player.buyUsingEscrow(item_2, store);

    // Do the actions
    assertTrue(item_0 == player.getItemByName("test0"), "First item purchased not found in Player's inventory");
    assertTrue(item_1 == player.getItemByName("test1"), "Second item purchased not found in Player's inventory");
    assertTrue(item_2 == player.getItemByName("test2"), "Third item purchased not found in Player's inventory");

    //Case item not in store
      Item fakeItem = new Item("fakeItem", 3);
      player.buyUsingEscrow(fakeItem, store);
      //TODO what is the expected behavior, throws an exception or returns false, item was not sold
      assertTrue(null == player.getItemByName("fakeItem"), "Player purchased an item not available in store");

      //Case insufficient funds
      try {
      player.buyUsingEscrow(expensiveItem, store);
      } catch(Exception e){}
      assertTrue(null == player.getItemByName("expensiveItem"), "Player with insufficient funds purchased item");
      assertTrue(expensiveItem == store.getItemByName("ExpensiveItem"), "Store inventory does not contain item after failed purchase");
      
      //Case player not in store
      store.exit(player);
      player.buyUsingEscrow(item_3, store);
      assertTrue(null == player.getItemByName("item_3"), "Player not in store purchased an item");
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  
  @Test
  @Tag("sellItemPA4")
  void sellItemPA4() throws FailedTransactionException{
    Store store = new Store();
    Player player = new Player(100.0);

    //player buys item
    Item item = new Item("player_item", 1.0);
    player.acquireItem(item);
    assertTrue(item == player.getItemByName("player_item"), "Item not found in inventory after acquire");

    // Sell the item
    store.enter(player);
    store.sellItem(item, player);
    assertNull(player.getItemByName("player_item"), "Item still in inventory after sale");

    //TODO:
    //case selling item player does not have
    //check that correct amount of money was received
  }

  @Test
  @Tag("sellItemPA5")
  void sellItemPA5() throws FailedTransactionException{
    // Set up 
    Store store = new Store();
    Player player = new Player(100.0);

    //aquire item
    Item item = new Item("player_item", 1.0);
    player.acquireItem(item);
    player.sellUsingEscrow(item, store);
    assertNull(player.getItemByName("player_item"));
    assertTrue(store.getItemByName("player_item") == item);
    
   //case player doesn't own item
   Item item2 = new Item("Player does not own", 1.0);
   try{
   player.sellUsingEscrow(item2, store);
   }catch(Exception e){}
   assertNull(player.getItemByName("Player does not own"), "Player aquired item not previously owned");
   assertTrue(store.getItemByName("Player does not own") != item2, "Item player did not own was sold");


    

  }

  @Test
  @Tag("playerRelinquishPA4")
  void playerRelinquishPA4() {
    // Relinquish item
    store.enter(player);
    try {
    player.relinquishItem(item_1);
    } catch(Exception e){}
    assertNull(player.getItemByName("test1"), "Item still in inventory after relinquish");
  }
 
  //TODO
  //check functionality of additional methods
  //Escrow class on it's own
  //exposeinventory
  //equip and consume

  
}