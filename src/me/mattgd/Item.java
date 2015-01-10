package me.mattgd;

public class Item {
	
	String name;
	int craftingCost, purchasePrice, energyCost, deliveryReward;
	int quantity = 0;
	
	Item(String name, int craftingCost, int purchasePrice, int energyCost, int deliveryReward) {
		this.name = name;
		this.craftingCost = craftingCost;
		this.purchasePrice = purchasePrice;
		this.energyCost = energyCost;
		this.deliveryReward = deliveryReward;
	}
	
	// Returns the quantity of the item in the user's inventory
	int getQuantity() {
		return quantity;
	}
	
	// Sets the quantity of the item in the user's inventory
	void setQuantity(int newQuantity) {
		quantity = newQuantity;
	}
	
	// Returns the item's name
	String getName() {
		return name;
	}
	
	// Returns the item's crafting cost
	Integer getCraftingCost() {
		return craftingCost;
	}
	
	// Returns the item's purchase price
	Integer getPurchasePrice() {
		return purchasePrice;
	}
	
	// Returns the item's energy cost
	Integer getEnergyCost() {
		return energyCost;
	}
	
	// Returns the item's delivery reward
	Integer getDeliveryReward() {
		return deliveryReward;
	}
	
}
