package me.mattgd;

import java.util.Map;
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

public class CapitalClaus {
	
	public static int money;
	public static int energy;
	public static int completion;
	public static boolean timeUp = false;
	
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
		
		getNextActivity(sc);
		
		//int totalSeconds = 100800;
	}
	
	// Method for getting next activity selection
	public static void getNextActivity(Scanner sc) {
		
		if (timeUp) {
			//TODO Create game end code
			
			sc.close();
		} else {
			
			System.out.println("\n*** Activity Selection ***");
			System.out.print("> 1 - Craft Gifts\n> 2 - Buy Gifts\n> 3 - Deliver Gifts\n");
			System.out.print("\nWhat would you like to do next?\n> ");
			int nextEventCode = sc.nextInt();
			
			switch (nextEventCode) {
				case 1: craftGifts(sc);
					break;
				case 2: purchaseGifts(sc);
					break;
				case 3: deliverGifts(sc);
					break;
				default: System.out.println("Invalid selection. Please try again.");
			}
			
			sc.close();
		}
		
	}
	
	// Method for crafting gifts
	public static void craftGifts(Scanner sc) {
		
		Map<Integer, Item> availableItems = ItemsManager.availableItems;
		
		System.out.println("\n*** Gift Crafting ***");
		
		for (Entry<Integer, Item> entry : availableItems.entrySet()) {
			int id = entry.getKey();
			Item item = entry.getValue();
			System.out.println(id + " - " + item.getName() + " ($" + item.getCraftingCost() + "/pc., " + item.getEnergyCost() + " energy)");
		}
		
		System.out.print("\nWhich gift would you like to craft?\n> ");
		int selection = sc.nextInt();
		
		if (availableItems.containsKey(selection)) {
			
			Item selectedItem = availableItems.get(selection);
			int amount = checkAmount(selectedItem, "craft", sc); // Asks for an amount until valid
			
			alterMoney(amount * -(selectedItem.getCraftingCost())); // Subtract the money from the user's account
			alterInventory(selection, amount, "crafted"); // Add the item to the user's inventory
			alterEnergy(selectedItem.getEnergyCost()); // Remove energy from the player
			
		}

		getNextActivity(sc);
	}
	
	// Method for buying gifts
	public static void purchaseGifts(Scanner sc) {
		
		Map<Integer, Item> availableItems = ItemsManager.availableItems;
		
		System.out.println("\n*** Gift Buying ***");
		
		for (Entry<Integer, Item> entry : availableItems.entrySet()) {
			int id = entry.getKey();
			Item item = entry.getValue();
			System.out.println(id + " - " + item.getName() + " ($" + item.getPurchasePrice() + "/pc.)");
		}
		
		System.out.print("\nWhich gift would you like to purchase?\n> ");
		int selection = sc.nextInt();
		
		while (!availableItems.containsKey(selection)) {
			System.out.println("Invalid selection.");
			System.out.print("\nWhich gift would you like to purchase?\n> ");
			selection = sc.nextInt();
		}
			
		Item selectedItem = ItemsManager.availableItems.get(selection);
		int amount = checkAmount(selectedItem, "purchase", sc); // Asks for an amount until valid
			
		alterMoney(amount * -(selectedItem.getPurchasePrice())); // Subtract the money from the user's account
		alterInventory(selection, amount, "purchased"); // Add the item to the user's inventory

		getNextActivity(sc);
	}

	// Method for delivering gifts
	public static void deliverGifts(Scanner sc) {
		
		// Make sure user has gifts to give
		boolean isInventoryEmpty = true;
		
		for (Entry<Integer, Item> entry : ItemsManager.availableItems.entrySet()) {
			Item item = entry.getValue();
			if (item.getQuantity() > 0) {
				isInventoryEmpty = false;
				break;
			}
		}
		
		if (isInventoryEmpty) {
			System.out.println("You don't have any gifts to deliver!");
		} else {
			
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
			while (!(Locations.locationList.contains(selectedLocation))) {
				System.out.println("Invalid location. Please try again.");
				System.out.print("\nWhere would you like to deliver to?\n> ");
				selectedLocation = sc.nextLine();
			}
			
			System.out.println("\nYour next delivery will be made in " + selectedLocation + ".");

			// Print a list of all of the items that the user has in their inventory
			Map<Integer, Item> availableItems = ItemsManager.availableItems;
			
			System.out.println("\nYour inventory: ");
			
			int count = 1;
			int selection = 0;
			Item item;
			for (Entry<Integer, Item> entry : availableItems.entrySet()) {

				item = entry.getValue();
				
				if (item.getQuantity() > 0) {
					System.out.printf("%d - %s (%d in inventory.)%n", entry.getKey(), item.getName(), item.getQuantity());
					selection = entry.getKey();
					count++;
				}
				
			}
			
			if (count > 2) {
				System.out.printf("%nWhich gift would you like to deliver in %s?%n> ", selectedLocation);
				selection = sc.nextInt();
			}
			
			if (availableItems.containsKey(selection)) {
				
				Item selectedItem = availableItems.get(selection);
				int amount = checkAmount(selectedItem, "deliver", sc); // Asks for an amount until valid
				
				alterMoney(amount * selectedItem.getDeliveryReward()); // Add the money from the user's account
				alterInventory(selection, -amount, "delivered"); // Remove the item from the user's inventory
				alterEnergy(selectedItem.getEnergyCost()); // Remove energy from the player
				Locations.removeLocationFromList(selectedLocation); // Remove the location from the possible locations list
				
			}
		}
		
		getNextActivity(sc);
		
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
	public static void alterInventory(int itemID, int amount, String action) {
		
		Item item = ItemsManager.availableItems.get(itemID);
		String itemName = item.getName();
		int currentAmount = item.getQuantity();
		
		// Add or remove item from inventory, if it has a value ID
		if (item.getQuantity() > 0) {
			
			// Make sure user has some of the selected item to remove
			if (currentAmount + amount < 0) {
				System.out.printf("You do not have enough %ss in your inventory to remove %d.%n", itemName, amount);
			} else {
				item.setQuantity(currentAmount + amount);
			}
			
		} else {
			
			// Make sure user has some of the selected item to remove
			if (amount < 0) {
				System.out.printf("You do not have enough %ss in your inventory to remove %d.%n", itemName, amount);
			} else {
				item.setQuantity(amount);
			}
			
		}
		
		currentAmount = item.getQuantity();
		
		// Grammar fix if only one of item in inventory
		String suffix = "s";
		String prefix = "";
		if (currentAmount == 1) {
			suffix = "";
			prefix = "a ";
		}
		
		System.out.printf("You just %s %s%s%s. You now have %d %s%s in your inventory.%n", action, prefix, itemName, suffix, currentAmount, itemName, suffix);
	}
	
	public static void alterMoney(int amount) {
		money += amount;
	}
	
	public static void alterEnergy(int value) {
		energy += value;
	}
	
	// Asks for an amount of an item until valid
	static int checkAmount(Item selectedItem, String action, Scanner sc) {
		
		System.out.printf("How many %ss would you like to %s?%n> ", selectedItem.getName(), action);
		int amount = sc.nextInt();
		
		while (amount <= 0) {
			System.out.println("Invalid amount specified.");
			System.out.print("How many " + selectedItem.getName() + "s would you like?\n> ");
			amount = sc.nextInt();
		}
		return amount;
		
	}
	
}
