import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;


public class VendingMachineRunner {


	
	private VendingMachine machine;
	private BufferedReader reader;
	NumberFormat format = NumberFormat.getCurrencyInstance();
	private boolean running = true;
	private boolean inventory = false;

	private VendingMachineRunner(VendingMachine machine) {
		this.machine = machine;
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public static void run(VendingMachine vm) throws IOException {
		VendingMachineRunner runner = new VendingMachineRunner(vm);
		runner.displayHeader();
		while (runner.isRunning())
			if (runner.usingInventory())
				runner.acceptInventoryCommand();
			else runner.acceptSalesCommand();

	}
	
	protected boolean usingInventory() {
		return inventory;
	}

	protected void displayBalance() {
		System.out.println("Current Balance : " + format.format(machine.getCurrentChange()));
	}
	
	protected void displaySales() {
		System.out.println("Total Sales : " + format.format(machine.getTotalSales()));
	}

	protected void acceptChange() throws IOException {
		displayBalance();
		System.out.print( "Please choose an amount to deposit: Nickel [N], Dime [D], Quarter [Q], Half-Dollar[H], or Dollar [1]: ");
		switch(reader.readLine().charAt(0)) {
			case 'N':
			case 'n': machine.depositChange(5); break;
			case 'D':
			case 'd': machine.depositChange(10); break;
			case 'Q':
			case 'q': machine.depositChange(25); break;
			case 'H':
			case 'h': machine.depositChange(50); break;
			case '1': machine.depositChange(100); break;
			default : reportError("Not a valid deposit selection! Exiting change deposit."); return;
		}
	}

	protected void returnChange() {
		System.out.println("Returning deposits of " + format.format(machine.returnChange()));
	}

	protected void displayHeader() {
		System.out.println("Welcome to " + machine.getVendingHeader());
		salesMenu();
	}

	protected void salesMenu() {
		System.out.println("|----- Please choose one of the following commands -----|");
		System.out.println("|    'menu'     : display this menu.                    |");
		System.out.println("|    'deposit'  : add change to the machine.            |");
		System.out.println("|    'purchase' : make a selection from the machine.    |");
		System.out.println("|    'exit'     : return change and exit the machine.   |");
		System.out.println("|-------------------------------------------------------|");

	}

	protected void inventoryMenu() {
		System.out.println("|------------- Please choose one of the following commands -------------|");
		System.out.println("|    'menu'                   : display this menu.                      |");
		System.out.println("|    'inventory'              : display inventory for all selections.   |");
		System.out.println("|    'sales'                  : display sales data for all selections.  |");
		System.out.println("|    'refill <selection>'     : refill the inventory of the selection.  |");
		System.out.println("|    'reset <selection>'      : reset the sales info from the selection.|");
		System.out.println("|    'exit'                   : return change and exit the machine.     |");
		System.out.println("|-----------------------------------------------------------------------|");
	}
	
	protected void acceptInventoryCommand() throws IOException {
		displaySales();
		System.out.print("Inventory command : ");
		String command = reader.readLine().trim();
		if (command.equals("")) return;
		String selection = "";
		if (command.indexOf(" ") > 4) {
			selection = command.substring(command.indexOf(" ") + 1);
			command = command.substring(0, command.indexOf(" "));
		}
		if (command.equals("")) return;
		else if (command.equalsIgnoreCase("sales"))
			displaySelections(DisplayOptions.SALES);
		else if (command.equalsIgnoreCase("menu"))
			inventoryMenu();
		else if (command.equalsIgnoreCase("inventory"))
			displaySelections(DisplayOptions.INVENTORY);
		else if (command.equalsIgnoreCase("refill"))
			machine.refillInventory(selection);
		else if (command.equalsIgnoreCase("reset"))
			machine.resetInventorySales(selection);
		else if (command.equalsIgnoreCase("exit"))
			inventory = false;
		else reportError("Not a valid command! Please try again or enter \"menu\" for a list of commands!");
	}
	
	protected void acceptSalesCommand() throws IOException {
		displayBalance();
		System.out.print("Enter command : ");
		String command = reader.readLine().trim();
		if (command.equals("")) return;
		else if (command.equalsIgnoreCase("menu"))
			salesMenu();
		else if (command.equalsIgnoreCase("inventory"))
			inventory = true;
		else if (command.equalsIgnoreCase("exit"))
			exit();	
		else if (command.equalsIgnoreCase("deposit"))
			acceptChange();
		else if (command.equalsIgnoreCase("purchase"))
			handlePurchase();
		else reportError("Not a valid command! Please try again or enter \"menu\" for a list of commands!");
	}

	protected void reportError(String errorMessage) {
		System.out.println(errorMessage);
	}

	private enum DisplayOptions {
		COST,
		INVENTORY,
		SALES,
		;
	}
	
	protected void displaySelections(DisplayOptions option) {
		System.out.println("---------- Selections ----------");
		for (VendingProduct product : machine.getProducts())
		{
			System.out.print("\t " + product.getSelection());
				if (option == DisplayOptions.COST)
					System.out.println("\t [" + format.format(product.getCost()) + "]");
				else if (option == DisplayOptions.INVENTORY)
					System.out.println("\t " + product.getInventory() + " [" + product.getSold() + " sold ]");
				else if (option == DisplayOptions.SALES)
					System.out.println("\t " + format.format(product.getInventorySales()) + " [" + product.getSold() + " sold ]");
		}
		System.out.println("--------------------------------");
	}

	protected void handlePurchase() throws IOException {
		displaySelections(DisplayOptions.COST);
		System.out.print("Please make a selection from above ["+ format.format(machine.getCurrentChange()) + "]: ");
		String selection = reader.readLine();
		System.out.println(machine.purchase(selection));
		if (machine.getCurrentChange() > 0.10f)
			requestAnotherSelection();
	}

	protected void requestAnotherSelection() throws IOException {
		System.out.print("Would you like to make another selection? [Y/n]: ");
		switch(reader.readLine().charAt(0)) {
			case 'n':
			case 'N': return;
			default: handlePurchase();
		}
	}

	protected void exit() {
		returnChange();
		machine.displayExitMessage();
		System.out.println("Exiting . . .");
		running = false;
	}

	protected boolean isRunning() {
		return running;
	}

}
