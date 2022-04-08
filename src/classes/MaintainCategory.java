package classes;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class MaintainCategory  {
	private ArrayList<Category> categories = new ArrayList<Category>();
	private String path;
	private int selectedStore;
	
	private MaintainItem maintainItem;
	
	public MaintainCategory(String path, MaintainItem maintainItem, int selectedStore) throws Exception {
		this.path = path;
		this.selectedStore = selectedStore;
		
		this.maintainItem = maintainItem; 
		
		load(path);
	}
	
	public void add(Category category) throws Exception {
		for(Category c : categories) {
			if(c.getName().equals(category.getName())) {
				throw new Exception("Category with this name already exists");
			}
		}
		
		this.categories.add(category);
		this.update(path);
	}
	
	public ArrayList<Category> getCategories() {
		return categories;
	}
	
	public String getPath() {
		return path;
	}
	
	public Category getCategoryById(int id) {
		for (Category c : categories) {
			if (c.getId() == id) {
				return c;
			}
		}
		
		return null;
	}
	
	public Category getCategoryByItem(Item item) {
		for (Category c : categories) {
			for (Item i : c.getItems()) {
				if(i == null) continue;
				if (i.getId() == item.getId()) {
					return c;
				}
			}
		}
		
		return null;
	}
	
	public int generateCategoryId() {
		ArrayList<Integer> categoryIds = new ArrayList<Integer>();
		int id = 0;
		
		for(Category c : categories) {
			categoryIds.add(c.getId());
		}
		
		while (categoryIds.contains(id)) {
			id++;
		}
		
		return id;
	}
	
	public void addItemToCategory(Item item, String category) throws Exception {
		for (Category c : categories) {
			if (c.getName().equals(category)) {
				if(!c.getItems().contains(item)) c.addItem(item);
			}
		}
	}
	
	public void removeItemFromCategory(Item item, String category) throws Exception {
		for (Category c : categories) {
			if (c.getName().equals(category)) {
				c.removeItem(item);
			}
		}
	}
	
	public Category getCategoryByName(String name) {
		for (Category c : categories) {
			if(c.getName().equals(name)) {
				return c;
			}
		}
		
		return null;
	}
	
	public void load(String path) throws Exception{
		CsvReader reader = new CsvReader(path); 
		reader.readHeaders();
		
		while(reader.readRecord()){ 
			List<String> itemIdList = Arrays.asList(reader.get("items").split("\\s*,\\s*"));
			ArrayList<Item> itemList = new ArrayList<Item>();
			
			for (String id : itemIdList) {
				if(id.equals("")) continue;
				Item item = maintainItem.getItemById(Integer.valueOf(id));
				
				itemList.add(item);
			}
			
			Category category = new Category(Integer.valueOf(reader.get("id")), reader.get("name"), itemList, Boolean.valueOf(reader.get("isVisible")));
			
			categories.add(category);
		}
	}
	
	public void update(String path) throws Exception{
		try {		
				CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
				//id,email,password,permission,storeId
				csvOutput.write("id");
		    	csvOutput.write("name");
				csvOutput.write("items");
				csvOutput.write("isVisible");
				csvOutput.endRecord();

				// else assume that the file already has the correct header line
				// write out a few records
				for(Category category: categories){
					csvOutput.write(String.valueOf(category.getId()));
					csvOutput.write(category.getName());
					csvOutput.write(category.getItemsString());
					csvOutput.write(String.valueOf(category.isVisible()));
					csvOutput.endRecord();
				}
				csvOutput.close();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
}
