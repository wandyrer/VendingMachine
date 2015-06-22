public class Soda implements VendingProduct {

	private int sodaInv;
	private final float cost;
	private final String name;
	private int maxInventory;
	private float inventorySales = 0;
	private int soldInventory = 0;

	public Soda (int sodaInv, int price, String name)
	{
		this.name = name;
		this.cost = price/100.0f;
		this.sodaInv = sodaInv;
		this.maxInventory = sodaInv;
	}
	
	public Soda (int currentInv, int maxInv, int price, String name) 
	{
		this.name = name;
		this.cost = price/100.0f;
		this.sodaInv = currentInv;
		this.maxInventory = maxInv;
	}

	public String getSelection()
	{
		return name;
	}

	public int getInventory()
	{
		return sodaInv;
	}

	public float getCost()
	{
		return cost;
	}

	public boolean reduceInventory()
	{
		if (sodaInv > 0) {
			soldInventory += 1;
			inventorySales += cost;
			sodaInv  -= 1;
			return true;
		} else return false;
	}

	public void setInventory(int inventory)
	{
		if (inventory > 0)
			if (inventory <= maxInventory)
				sodaInv = inventory;
			else {
				sodaInv = inventory;
				maxInventory = inventory;
			}
	}

	@Override
	public void refillInventory() {
		this.sodaInv = maxInventory;
		
	}

	@Override
	public int getSold() {
		return soldInventory;
		
	}

	@Override
	public float getInventorySales() {
		return inventorySales;
	}

	@Override
	public void resetInventorySales() {
		this.inventorySales = 0;
		this.soldInventory = 0;
		
	}

}
