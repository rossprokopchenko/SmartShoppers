package classes;

import java.io.FileWriter;
import java.util.ArrayList;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class MaintainItem {
	private ArrayList<Item> items = new ArrayList<Item>();
	private String path;
	private int selectedStore;
	
	public MaintainItem(String path, int selectedStore) throws Exception {
		this.path = path;
		this.selectedStore = selectedStore;
		
		load(path);
	}
	
	public String getPath() {
		return path;
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public ArrayList<Integer> getItemIds() {
		ArrayList<Integer> itemIds = new ArrayList<Integer>();
		
		for(Item item : items) {
			itemIds.add(item.getId());
		}
		
		return itemIds;
	}
	
	public Item getItemById(int id) {
		for (Item i : items) {
			if (i.getId() == id) {
				return i;
			}
		}
		
		return null;
	}
	
	public Item getItemByName(String name) {
		for (Item i : items) {
			if(i.getName().equals(name)) {
				return i;
			}
		}
		
		return null;
	}
	
	public void add(Item item) throws Exception {
		for(Item i : items) {
			if (i.getName().equals(item.getName())) {
				throw new Exception("Item Already Exists");
			}
		}
		
		this.items.add(item);
		this.update(path);
	}
	
	public void updateItem(int itemId, Item newItem) throws Exception {
		this.items.set(itemId, newItem);
		this.update(path);
	}
	
	public void deleteItem(Item item) throws Exception {
		this.items.remove(item);
		this.update(path);
	}
	
	public int generateItemId() {
		ArrayList<Integer> itemIds = new ArrayList<Integer>();
		int id = 0;
		
		for(Item i : items) {
			itemIds.add(i.getId());
		}
		
		while (itemIds.contains(id)) {
			id++;
		}
		
		return id;
	}
	
	public void load(String path) throws Exception{
		CsvReader reader = new CsvReader(path); 
		reader.readHeaders();
		
		while(reader.readRecord()){
			ArrayList<Integer> storeIdList = new ArrayList<Integer>();
			
			for (char store : reader.get("store").toCharArray()) {
				if (store != ',') {
					storeIdList.add(store - '0');
				}
			}
			
			Item item = new Item(Integer.valueOf(reader.get("id")), reader.get("name"), Integer.valueOf(reader.get("inventory")), 
			Boolean.valueOf(reader.get("availability")), Double.valueOf(reader.get("price")), reader.get("description"), Integer.valueOf(reader.get("size")),
			Boolean.valueOf(reader.get("onSale")), storeIdList, reader.get("imagePath"));
			
			if(reader.get("store").contains("" + selectedStore)) {
				items.add(item);
			}
		}
	}
	
	public void update(String path) throws Exception{
		try {		
				CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
				//id,email,password,permission,storeId
				csvOutput.write("id");
		    	csvOutput.write("name");
				csvOutput.write("inventory");
				csvOutput.write("availability");
				csvOutput.write("price");
				csvOutput.write("description");
				csvOutput.write("size");
				csvOutput.write("onSale");
				csvOutput.write("store");
				csvOutput.write("imagePath");
				csvOutput.endRecord();

				// else assume that the file already has the correct header line
				// write out a few records
				for(Item item: items){
					csvOutput.write(String.valueOf(item.getId()));
					csvOutput.write(item.getName());
					csvOutput.write(String.valueOf(item.getInventory()));
					csvOutput.write(String.valueOf(item.isAvailabile()));
					csvOutput.write(String.valueOf(item.getPrice()));
					csvOutput.write(item.getDescription());
					csvOutput.write(String.valueOf(item.getSize()));
					csvOutput.write(String.valueOf(item.isOnSale()));
					csvOutput.write(item.getStoresString());
					csvOutput.write(String.valueOf(item.getImagePath()));
					csvOutput.endRecord();
				}
				csvOutput.close();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
}
