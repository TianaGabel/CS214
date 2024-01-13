import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import java.beans.Transient;

@RunWith(JUnit4.class)
public class TestSetJUnit4 {

  static Store store;
  static Player player;
  static Item item0;
  static Item item1; 
  static Item item2;

  @Before 
  public void setup() {
    store = new Store();

    item0 = new Item("test0", 1.0);
    item1 = new Item("test1", 1.0);
    item2 = new Item("test2", 1.0); 


    player = new Player(100.0);

    store.addItem(item0);
    store.addItem(item1);
    store.addItem(item2);
  }

  @Test
  public void testAquire() { 
    store.enter(player);
    try{
    store.buyItem(item0, player);
    store.buyItem(item1, player);
    store.buyItem(item2, player);

    assertSame(item0, player.getItemByName("test0"));
    assertSame(item1, player.getItemByName("test1"));
    assertSame(item2, player.getItemByName("test2"));
    }catch(Exception e){
      e.printStackTrace();
    }

  }

  @Test
  public void testAquirePA5() {
    try{
      //this is the PA5 version
      store.enter(player);
      player.buyUsingEscrow(item0, store);
      player.buyUsingEscrow(item1, store);
      player.buyUsingEscrow(item2, store);

      assertSame(item0, player.getItemByName("test0"));
      assertSame(item1, player.getItemByName("test1"));
      assertSame(item2, player.getItemByName("test2"));
    }catch(Exception e){
      e.printStackTrace();
    }

  }

  @Test
  public void testPlayerCanSell() throws FailedTransactionException {
    //This is the PA4 verison
    Store store = new Store();
    Player player = new Player(100.0);

    Item item = new Item("player_item", 1.0);
    player.acquireItem(item);
    assertSame(item, player.getItemByName("player_item"));
    
    store.enter(player);
    store.sellItem(item, player);
    assertNull(player.getItemByName("player_item"));
  }

  @Test
  public void testPlayerCanSellPA5() throws FailedTransactionException {
    Store store = new Store();
    Player player = new Player(100.0);

    Item item = new Item("player_item", 1.0);
    //This is the PA5 version
    player.acquireItem(item);
    assertSame(item, player.getItemByName("player_item"));

    player.sellUsingEscrow(item, store);
    assertNull(player.getItemByName("player_item"));
  }
  

  @Test
  public void testPlayercanRelinquish(){
    store.enter(player);
    player.relinquishItem(item1);
    assertNull(player.getItemByName("test1"));
    }
  }