package me.mattgd;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

/*
 * Ideas
 * 
 * Deliver presents (time and energy)
 * Buy presents (money)
 * Make presents (time and energy)
 * Earn money for delivering presents
 * Starts at 6 AM, ends at 10 AM EST.
 * Reminders of completion based on time
 * About section (max energy, money explanation, etc.)
 * Save, load, and delete games
 * Commands for getting stats
 * Gifts exponentially get more expensive
 * Make a price and name hashmap
 * Inventory trackers
 * Pause command
 * Add > symbol for user typing
 * Explain crafting, buying, delivering (CRAFT: energy and some money, buying: money (expensive), delivering: energy and gain money)
 * Add way to not exit activity (buy different items at once)
 * Allow users to add own locations
 * Set locations as numbers
 * Break locations to next line every 5
 */

/*
 * How the inventory works
 * 
 * ID and price
 * ID and name
 * 
 * How I want it to work
 * 
 * ID - Name, Price
 */

public class CapitalClaus {
	
	public static int money;
	public static int energy;
	public static int completion;
	
	public static void main(String[] args) {
		
		// Adds items for buying and crafting, and the inventory
		ItemsManager.loadItems();
		
		// Populate the delivery location list
		Locations.populateLocationList();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Please enter your name: ");
		String name = sc.next();
		
		System.out.printf("Welcome to Capital Claus, %s!%n", name);
		System.out.println("Capital Claus is a simulation of Santa Claus delivering presents as if it were a business.");
		System.out.println("Use numbers to make selections.");
		
		// Player money, random starting value between 400 and 500
		Random rand = new Random();
		money = rand.nextInt(500 - 400) + 400;
		
		// Player energy
		energy = 100;
		
		// Percentage of gifts delivered
		completion = 0;
		
		// Display player statistics
		displayStatistics();
		
		int timeLeft = 1;
		
		while (timeLeft > 0) {
			// Ask user for the next activity
			getNextActivity();
		}

		sc.close();
		
		//int totalSeconds = 100800;
	}
	
	// Method for getting next activity selection
	public static void getNextActivity() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n*** Activity Selection ***");
		System.out.print("> 1 - Craft Gifts\n> 2 - Buy Gifts\n> 3 - Deliver Gifts\n");
		System.out.print("\nWhat would you like to do next?\n> ");
		int nextEventCode = sc.nextInt();
		
		switch (nextEventCode) {
			case 1: craftGifts();
				break;
			case 2: purchaseGifts();
				break;
			case 3: deliverGifts();
				break;
			default: System.out.println("Invalid selection. Please try again.");
		}
		
		sc.close();
	}
	
	// Method for crafting gifts
	public static void craftGifts() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n*** Gift Crafting ***");
		
		for (Entry<Integer, List<String>> entry : ItemsManager.availableItems.entrySet()) {
			int id = entry.getKey();
			System.out.println(id + " - " + ItemsManager.getItemName(id) + " ($" + ItemsManager.getItemCraftingCost(id) + "/pc., " + ItemsManager.getItemEnergy(id) + " energy)");
		}
		
		System.out.print("\nWhich gift would you like to craft?\n> ");
		int selection = sc.nextInt();
		
		if (ItemsManager.availableItems.containsKey(selection)) {
			System.out.print("How many " + ItemsManager.getItemName(selection) + "s would you like?\n> ");
			int amount = sc.nextInt();
			
			while (amount <= 0) {
				System.out.println("Invalid amount specified.");
				
				System.out.print("\nHow many " + ItemsManager.getItemName(selection) + "s would you like?\n> ");
				amount = sc.nextInt();
			}
			
			// Subtract the money from the user's account
			alterMoney(amount * -(ItemsManager.getItemCraftingCost(selection)));
			
			// Add the item to the user's inventory
			alterInventory(selection, amount);
			
			// Remove energy from the player
			alterEnergy(ItemsManager.getItemEnergy(selection));
		}
		
		sc.close();
		
	}
	
	// Method for buying gifts
	public static void purchaseGifts() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n*** Gift Buying ***");
		
		for (Entry<Integer, List<String>> entry : ItemsManager.availableItems.entrySet()) {
			int id = entry.getKey();
			System.out.println(id + " - " + ItemsManager.getItemName(id) + " ($" + ItemsManager.getItemPurchaseCost(id) + "/pc.)");
		}
		
		System.out.print("\nWhich gift would you like to purchase?\n> ");
		int selection = sc.nextInt();
		
		if (ItemsManager.availableItems.containsKey(selection)) {
			System.out.print("How many " + ItemsManager.getItemName(selection) + "s would you like?\n> ");
			int amount = sc.nextInt();
			
			while (amount <= 0) {
				System.out.println("Invalid amount specified.");
				
				System.out.print("\nHow many " + ItemsManager.getItemName(selection) + "s would you like?\n> ");
				amount = sc.nextInt();
			}
			
			// Subtract the money from the user's account
			alterMoney(amount * -(ItemsManager.getItemPurchaseCost(selection)));
			
			// Add the item to the user's inventory
			alterInventory(selection, amount);
		}
		
		sc.close();
		
	}

	// Method for delivering gifts
	public static void deliverGifts() {
		
		// Make sure user has gifts to give
		if (ItemsManager.playerInventory.isEmpty()) {
			System.out.println("You don't have any gifts to deliver!");
		} else {
			Scanner sc = new Scanner(System.in);
			
			System.out.println("\n*** Gift Delivery ***");
			
			System.out.print("To select a delivery location, please enter the first letter of the city you would like to deliver to.\n> ");
			String letter = sc.next().toUpperCase();

			// Display possible locations
			System.out.print("Here is a list of possible locations that start with an '" + letter + "': " + Locations.getListOfLocations(letter) + ".");
			
			System.out.print("\nWhere would you like to deliver to?\n> ");
			String selectedLocation = sc.next();
			
			if (selectedLocation != "") {
				selectedLocation = Locations.formatLocationInput(selectedLocation);
			}
			
			//TODO Add way to exit location selection
			// Check to make sure location is valid, if not keep asking until it is
			if (Locations.locationList.contains(selectedLocation)) {
				System.out.println("Your next delivery will be made in " + selectedLocation + ".");
			} else {
				while (!(Locations.locationList.contains(selectedLocation))) {
					System.out.println("Invalid location. Please try again.");
					System.out.print("\nWhere would you like to deliver to?\n> ");
					selectedLocation = sc.nextLine();
				}
			}
			
			for (Entry<Integer, Integer> entry : ItemsManager.playerInventory.entrySet()) {
				int id = entry.getKey();
				System.out.println(id + " - " + ItemsManager.getItemName(id) + " (" + ItemsManager.getAmountInInventory(id) + " in inventory.)");
			}
			
			System.out.printf("%nWhich gift would you like to deliver in %s?%n> ", selectedLocation);
			int selection = sc.nextInt();
			
			if (ItemsManager.availableItems.containsKey(selection)) {
				System.out.printf("How many %ss would you like to deliver?%n> ", ItemsManager.getItemName(selection));
				int amount = sc.nextInt();
				
				while (amount <= 0) {
					System.out.println("Invalid amount specified.");
					
					System.out.printf("How many %ss would you like to deliver?%n> ", ItemsManager.getItemName(selection));
					amount = sc.nextInt();
				}
				
				// Add the money from the user's account
				alterMoney(amount * ItemsManager.getItemDeliveryReward(selection));
				
				// Remove the item from the user's inventory
				alterInventory(selection, amount);
				
				// Remove energy from the player
				alterEnergy(ItemsManager.getItemEnergy(selection));
				
				// Remove the location from the possible locations list
				Locations.removeLocationFromList(selectedLocation);
			}
			
			sc.close();
		}
		
	}
	
	// Displays money, energy, and completion statuses
	public static void displayStatistics() {
		System.out.printf("%nMoney:\t\t$%d%n", money);
		
		if (energy == 100) {
			System.out.printf("Energy:\t\t%s%n", "FULL");
		} else {
			System.out.printf("Energy:\t\t%d%%%n", energy);
		}
		
		System.out.printf("Completion:\t%d%%%n", completion);
	}
	
	// Add or remove items from inventory
	public static void alterInventory(int itemID, int amount) {
		
		String itemName = ItemsManager.availableItems.get(itemID).get(0);
		
		// Add or remove item from inventory, if it has a value ID
		if (ItemsManager.playerInventory.containsKey(itemID)) {
			
			int currentAmt = ItemsManager.playerInventory.get(itemID);
			
			// Make sure user has some of the selected item to remove
			if (currentAmt + amount < 0) {
				System.out.printf("You do not have enough %ss in your inventory to remove %d.%n", itemName, amount);
			} else {
				ItemsManager.playerInventory.replace(itemID, currentAmt + amount);
			}
			
		} else {
			
			// Make sure user has some of the selected item to remove
			if (amount < 0) {
				System.out.printf("You do not have enough %ss in your inventory to remove %d.%n", itemName, amount);
			} else {
				ItemsManager.playerInventory.put(itemID, amount);
			}
			
		}
	
		int inventoryAmount = ItemsManager.playerInventory.get(itemID);
		
		// Grammar fix if only one of item in inventory
		if (ItemsManager.playerInventory.get(itemID) == 1) {
			System.out.printf("You now have %d %s in your inventory.%n", inventoryAmount, itemName);
		} else {
			System.out.printf("You now have %d %ss in your inventory.%n", inventoryAmount, itemName);
		}
		
	}
	
	public static void alterMoney(int amount) {
		money += amount;
	}
	
	public static void alterEnergy(int value) {
		energy += value;
	}
	
}
