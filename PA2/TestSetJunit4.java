

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import Recitation09.Item;
import Recitation09.Player;
import Recitation09.Store;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TestSetJunit4 {

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
    store.buyItem(item0, player);
    store.buyItem(item1, player);
    store.buyItem(item2, player);

    
    assertSame(item0, player.getItemByName("test0"));
    assertSame(item1, player.getItemByName("test1"));
    assertSame(item2, player.getItemByName("test2"));
  }

  

  @Test
  public void testPlayerCanSell() {
    Store store = new Store();
    Player player = new Player(100.0);

    Item item = new Item("player_item", 1.0);
    player.acquire(item);
    assertSame(item, player.getItemByName("player_item"));

    store.enter(player);
    assertTrue(store.sellItem(item, player));
    assertNull(player.getItemByName("player_item"));
  }


}