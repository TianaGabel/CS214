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
  

  //TODO is it worth checking to make sure current class files correspond with updated code
  @BeforeAll
  static void setup() {
    store = new Store();

    item_0 = new Item("test0", 1.0);
    item_1 = new Item("test1", 1.0); 
    item_2 = new Item("test2", 1.0);
    

    player = new Player(100.0);

    store.addItem(item_0);
    store.addItem(item_1);
    store.addItem(item_2);
    
  }
  

 @Test
  @Tag("acquire")
  void acquire() {
    try {
      store.enter(player);
      store.buyItem(item_0, player);
      store.buyItem(item_1, player);
      store.buyItem(item_2, player);
      
      // Do the actions
      assertTrue(item_0 == player.getItemByName("test0"), "First item purchased not found in Player's inventory");
      assertTrue(item_1 == player.getItemByName("test1"), "Second item purchased not found in Player's inventory");
      assertTrue(item_2 == player.getItemByName("test2"), "Third item purchased not found in Player's inventory");
      System.out.println(player.exposeInventory());
    }catch(Exception e){
      e.printStackTrace();
    }

  }
 
  @Test
  @Tag("acquirePA5")
  void acquirePA5() {

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
    System.out.println(player.exposeInventory());
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  

  @Test
  @Tag("player_can_sell")
  void player_can_sell() throws FailedTransactionException{
    // Set up the store
    Store store = new Store();

    // Create the player
    Player player = new Player(100.0);

    Item item = new Item("player_item", 1.0);
    player.acquireItem(item);
    assertTrue(item == player.getItemByName("player_item"), "Item not found in inventory after acquire");

    // Sell the item
    store.enter(player);
    store.sellItem(item, player);
    assertNull(player.getItemByName("player_item"), "Item still in inventory after sale");

    //This is the PA5 version
    player.acquireItem(item);
    assertTrue(item == player.getItemByName("player_item"));

    player.sellUsingEscrow(item, store);
    assertNull(player.getItemByName("player_item"));
  }

  @Test
  @Tag("player_can_relinquish")
  void player_can_relinquish() {
    // Relinquish item
    store.enter(player);
    player.relinquishItem(item_1);
    assertNull(player.getItemByName("test1"), "Item still in inventory after relinquish");
  }

  
}