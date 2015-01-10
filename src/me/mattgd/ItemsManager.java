package me.mattgd;

import java.util.HashMap;
import java.util.Map;

public class ItemsManager {
	
	public static Map<Integer, Item> availableItems = new HashMap<Integer, Item>();
	//public static Map<Item, Integer> playerInventory = new HashMap<Item, Integer>();
	
	public static void loadItems() {
	
		// Format: name (0), crafting price (1), purchase price (2), energy cost (3), delivery reward (4).
		
		// Create objects for each available item
		Item teddyBear = new Item("Teddy Bear", 3, 9, 5, 2);
		Item toySoldier = new Item("Toy Soldier", 4, 12, 6, 3);
		Item woodenHorse = new Item("Wooden Horse", 8, 24, 10, 6);
		
		// Put values into availableItems Map
		availableItems.put(1, teddyBear);
		availableItems.put(2, toySoldier);
		availableItems.put(3, woodenHorse);
	}
}
