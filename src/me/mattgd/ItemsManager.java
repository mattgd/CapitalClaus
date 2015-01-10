package me.mattgd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsManager {
	
	public static Map<Integer, List<String>> availableItems = new HashMap<Integer, List<String>>();
	public static Map<Integer, Integer> playerInventory = new HashMap<Integer, Integer>();
	
	public static void loadItems() {
	
		// Format: name (0), crafting price (1), purchase price (2), energy cost (3), delivery reward (4).
		
		// List for Teddy Bear
		List<String> teddyBearSet = new ArrayList<String>();
		teddyBearSet.add("Teddy Bear"); // Item name
		teddyBearSet.add("3"); // Crafting price
		teddyBearSet.add("9"); // Purchase price
		teddyBearSet.add("5"); // Energy cost
		teddyBearSet.add("2"); // Delivery reward
		
		// List for Toy Soldier
		List<String> toySoldierSet = new ArrayList<String>();
		toySoldierSet.add("Toy Soldier"); // Item name
		toySoldierSet.add("4"); // Crafting price
		teddyBearSet.add("12"); // Purchase price
		teddyBearSet.add("6"); // Energy cost
		teddyBearSet.add("3"); // Delivery reward

		// List for Wooden Horse
		List<String> woodenHorseSet = new ArrayList<String>();
		woodenHorseSet.add("Wooden Horse"); // Item name
		toySoldierSet.add("8"); // Crafting price
		teddyBearSet.add("24"); // Purchase price
		teddyBearSet.add("10"); // Energy cost
		teddyBearSet.add("6"); // Delivery reward
		
		// Put values into availableItems Map
		availableItems.put(1, teddyBearSet);
		availableItems.put(2, toySoldierSet);
		availableItems.put(3, woodenHorseSet);
	}
	
	// Returns the item name based on the provided ID
	public static String getItemName(int id) {
		String name = availableItems.get(id).get(0);
		return name;
	}
	
	// Returns the item crafting cost based on the provided ID
	public static Integer getItemCraftingCost(int id) {
		int price = Integer.parseInt(availableItems.get(id).get(1));
		return price;
	}
	
	// Returns the item purchase cost based on the provided ID
	public static Integer getItemPurchaseCost(int id) {
		int price = Integer.parseInt(availableItems.get(id).get(2));
		return price;
	}
	
	// Returns the item energy cost based on the provided ID
	public static Integer getItemEnergy(int id) {
		int energy = Integer.parseInt(availableItems.get(id).get(3));
		return energy;
	}
	
	// Returns the item delivery reward based on the provided ID
	public static Integer getItemDeliveryReward(int id) {
		int price = Integer.parseInt(availableItems.get(id).get(4));
		return price;
	}
	
	// Returns the item delivery reward based on the provided ID
	public static Integer getAmountInInventory(int id) {
		return playerInventory.get(id);
	}
}
