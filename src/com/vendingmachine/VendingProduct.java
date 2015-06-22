
public interface VendingProduct {

	public String getSelection();
	public float getCost();
	public int getInventory();
	public void refillInventory();
	public int getSold();
	public float getInventorySales();
	public void resetInventorySales();
	public void setInventory(int newInventory);
	public boolean reduceInventory();

}
