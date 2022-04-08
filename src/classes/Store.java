package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Store {
	private int id;
	private String name;
	private String address;
	private ArrayList<Category> categories;
	private int density;
	private String openingHours;
	private String closingHours;
	
	public Store(int id, String name, String address, ArrayList<Category> categories, int density, String openingHours, String closingHours) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.categories = categories;
		this.density = density;
		this.setOpeningHours(openingHours);
		this.setClosingHours(closingHours);
	}
	
	public Store() {
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCategoriesString() {
		String s = "";
		
		for(Category c : categories) {
			s += c.getId() + ",";
		}
		
		s = s.substring(0, s.length() - 1);
		
		return s;
	}
	public ArrayList<Category> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}
	
	public ArrayList<Item> getStoreItems() {
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		for (Category c : categories) {
			for (Item i : c.getItems()) {
				if (i != null)
				itemList.add(i);
			}
		}
		
		Collections.sort(itemList, (o1, o2) -> o1.getId() - o2.getId());
		
		return itemList;
	}
	
	public int getDensity() {
		return density;
	}
	public void setDensity(int density) {
		this.density = density;
	}
	
	@Override
	public String toString() {
		String itemString = "";
		
		for (Category c : categories) {
			itemString += c.getItemsString();
		}
		
		return "Store [id=" + id + ", name=" + name + ", address=[" + address + "], categories=[" + getCategoriesString() + "]";
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

	public String getClosingHours() {
		return closingHours;
	}

	public void setClosingHours(String closingHours) {
		this.closingHours = closingHours;
	}
}
