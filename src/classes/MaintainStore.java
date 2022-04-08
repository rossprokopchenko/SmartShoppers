package classes;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class MaintainStore {
	private ArrayList<Store> stores = new ArrayList<Store>();
	private Store store = new Store();
	private String path;
	private int selectedStore;
	
	public MaintainCategory maintainCategory;
	public MaintainItem maintainItem;
	
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public MaintainStore(String path, int selectedStore) throws Exception {
		this.path = path;
		this.selectedStore = selectedStore;
		
		maintainItem = new MaintainItem("resources/csvs/items.csv", selectedStore);
		maintainCategory = new MaintainCategory("resources/csvs/categories.csv", maintainItem, selectedStore);
		
		load(path);
	}
	
	public void reload() throws Exception {
		maintainItem = new MaintainItem("resources/csvs/items.csv", selectedStore);
		maintainCategory = new MaintainCategory("resources/csvs/categories.csv", maintainItem, selectedStore);
		
		load(path);
	}
	
	public String getPath() {
		return path;
	}
	
	public void updateCurrentStore() throws Exception {
		for(Store s : stores) {
			if(s.getId() == store.getId()) {
				s = store;
				
			}
		}
		
		update(path);
	}
	
	public void add(Store store) throws Exception {
		this.stores.add(store);
		this.update(path);
	}
	
	public ArrayList<Store> getStores() {
		return stores;
	}
	
	public int generateStoreId() {
		ArrayList<Integer> storeIds = new ArrayList<Integer>();
		int id = 0;
		
		for(Store s : stores) {
			storeIds.add(s.getId());
		}
		
		while (storeIds.contains(id)) {
			id++;
		}
		
		return id;
	}
	
	public Store getStore(int id) {
		return stores.get(id);
	}
	
	public Store getCurrentStore() {
		return store;
	}
	
	public void setCurrentStore(int storeId) throws Exception {
		this.selectedStore = storeId;
		store = stores.get(storeId);
		load(path);
	}
	
	public Category getCategoryById(int id) {
		for(Category c : store.getCategories()) {
			if(c.getId() == id) {
				return c;
			}
		}
		
		return null;
	}
	
	public Category getCategoryByName(String name) {
		for(Category c : store.getCategories()) {
			if(c.getName().equals(name)) {
				return c;
			}
		}
		
		return null;
	}
	
	public Item getItemByName(String name) {
		for (Category c : store.getCategories()) {
			for (Item i : c.getItems()) {
				if(i.getName().equals(name)) {
					return i;
				}
			}
		}
		
		return null;
	}
	
	public void addCategoryToCurrentStore(Category category) throws Exception {
		for(Store s : stores) {
			if(s.getId() == store.getId()) {
				s.getCategories().add(category);
			}
		}
		
		update(path);
	}
	
	public void load(String path) throws Exception{
		CsvReader reader = new CsvReader(path); 
		reader.readHeaders();
		stores = new ArrayList<Store>();
		
		while(reader.readRecord()){ 
			ArrayList<Category> categoriesList = new ArrayList<Category>();
			List<String> categoryIdList = Arrays.asList(reader.get("categories").split("\\s*,\\s*"));
			
			for (Category c : maintainCategory.getCategories()) {
				if(categoryIdList.contains(String.valueOf(c.getId()))) {
					categoriesList.add(c);
				}
			}
			
			Store store = new Store(Integer.valueOf(reader.get("id")), reader.get("name"), reader.get("address"), 
					categoriesList, Integer.valueOf(reader.get("density")), reader.get("openingHours"), reader.get("closingHours"));
			
			stores.add(store);
			
			if(store.getId() == selectedStore) {
				this.store = store;
			}
		}
	}
	
	public void update(String path) throws Exception{
		try {		
				CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
				//id,email,password,permission,storeId
				csvOutput.write("id");
		    	csvOutput.write("name");
				csvOutput.write("address");
				csvOutput.write("categories");
				csvOutput.write("density");
				csvOutput.write("openingHours");
				csvOutput.write("closingHours");
				csvOutput.endRecord();

				// else assume that the file already has the correct header line
				// write out a few records
				for(Store store: stores){
					csvOutput.write(String.valueOf(store.getId()));
					csvOutput.write(store.getName());
					csvOutput.write(store.getAddress());
					csvOutput.write(store.getCategoriesString());
					csvOutput.write(String.valueOf(store.getDensity()));
					csvOutput.write(store.getOpeningHours());
					csvOutput.write(store.getClosingHours());
					csvOutput.endRecord();
				}
				csvOutput.close();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
}
