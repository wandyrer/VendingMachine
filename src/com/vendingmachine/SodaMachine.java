import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class SodaMachine implements VendingMachine {

	private float change = 0.0f;
	private float totalSales = 0.0f;
	private List<VendingProduct> sodas;

	public SodaMachine(Soda...sodas)
	{
		this.change = 0;
		this.sodas = new ArrayList<VendingProduct>();
		for (VendingProduct vp : sodas)
			this.sodas.add(vp);
	}

	public double getCurrentChange()
	{
		return change;
	}

	public void depositChange(int cents)
	{
		change += cents/100.0f;
	}

	public double returnChange()
	{
		double curChange = change;
		change = 0.0f;
		return curChange;
	}

	public float getCost(String selection)
	{
		VendingProduct selected = getSelection(selection);
		if (selected != null)
			return selected.getCost();
		return 0;
	}

	private VendingProduct getSelection(String selection) {
		for (VendingProduct soda: sodas)
			if (soda.getSelection().equalsIgnoreCase(selection))
				return soda;
		return null;
	}
	
	
	public List<String> getSelections()
	{
		List<String> selections = new ArrayList<String>();
		for (VendingProduct soda : sodas)
			selections.add(soda.getSelection());
		return selections;
	}

	public int getInventory(String selection)
	{
		int inventory = 0;
		for (VendingProduct soda : sodas)
			if (soda.getSelection().equalsIgnoreCase(selection))
				inventory += soda.getInventory();
		return inventory;
	}

	public String purchase(String selection)
	{
		VendingProduct selected = getSelection(selection);
		if (selected == null)
			return "Invalid selection!";
		if (change < selected.getCost())
			return "You have not deposited enough change for the requested selection!";
		if (selected.reduceInventory()) {
			change -= selected.getCost();
			totalSales += selected.getCost();
			return "Thanks for buying a " + selected.getSelection();
		}
		else return "Out of stock for the requested selection!";
	}

	@Override
	public String getVendingHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String displayExitMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		Soda coke = new Soda(15, 50, "Coke");
		Soda pepsi = new Soda(15, 50, "Pepsi");
		Soda sprite = new Soda(15, 50, "Sprite");
		Soda arizona = new Soda(10, 75, "Arizona Iced Tea");
		Soda monster = new Soda(10, 100, "Monster!");
		Soda dietCoke = new Soda(15, 50, "Diet Coke");
		try {
			VendingMachineRunner.run(new SodaMachine(coke, pepsi, sprite, arizona, monster, dietCoke));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public float getInventorySales(String selection) {
		float invSales = 0.0f;
		for (VendingProduct vp : sodas)
			if (vp.getSelection().equalsIgnoreCase(selection))
				invSales += vp.getInventorySales();
		return invSales;
	}

	@Override
	public List<VendingProduct> getProducts() {
		return sodas;
	}

	@Override
	public float getTotalSales() {
		return totalSales;
	}

	@Override
	public void resetInventorySales(String selection) {
		for (VendingProduct vp : sodas)
			if (vp.getSelection().equalsIgnoreCase(selection))
			{
				totalSales -= vp.getInventorySales();
				if (totalSales < 0)
					totalSales = 0.0f;
				vp.resetInventorySales();
			}
	}

	@Override
	public void refillInventory(String selection) {
		for (VendingProduct vp : sodas)
			if (vp.getSelection().equalsIgnoreCase(selection))
				vp.refillInventory();
	}


}
