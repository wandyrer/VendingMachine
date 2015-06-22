import java.util.List;
import java.util.Set;


public interface VendingMachine {

	public String getVendingHeader();
	public String displayExitMessage();
	public List<String> getSelections();
	public void depositChange(int cents);
	public double returnChange();
	public double getCurrentChange();
	public String purchase(String selection);
	public float getCost(String selection); // in cents.
	public int getInventory(String selection);
	public float getTotalSales();
	public float getInventorySales(String selection);
	public void resetInventorySales(String selection);
	public List<VendingProduct> getProducts();
	public void refillInventory(String string);
	

}
