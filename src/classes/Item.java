package classes;

import java.util.ArrayList;

public class Item implements Comparable{
	
	private int id;
	private String name;
	private int inventory;
	private boolean availability;
	private Double price;
	private String description;
	private int size;
	private boolean onSale;
	private ArrayList<Integer> stores;
	private String imagePath;
	
	public Item(int id, String name, int inventory, boolean availability, Double price, String description,
			int size, boolean onSale, ArrayList<Integer> stores, String imagePath) {
		super();
		this.id = id;
		this.name = name;
		this.inventory = inventory;
		this.availability = availability;
		this.price = price;
		this.description = description;
		this.size = size;
		this.onSale = onSale;
		this.stores = stores;
		this.imagePath = imagePath;
	}
	
	public Item() {
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

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public boolean isAvailabile() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isOnSale() {
		return onSale;
	}

	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}

	public ArrayList<Integer> getStores() {
		return stores;
	}
	
	public String getStoresString() {
		String s = "";
		
		for (int storeId : stores) {
			s += storeId + ",";
		}
		
		s = s.substring(0, s.length() - 1);
		
		return s;
	}

	public void setStores(ArrayList<Integer> stores) {
		this.stores = stores;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String s) {
		this.imagePath = s;
	}
	
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", inventory=" + inventory + ", available=" + availability + ", price=" + price + ", onSale=" + onSale + ", stores=[" + getStoresString() + "]";
	}
	
	@Override
	public int compareTo(Object o) {
		int compareId = ((Category)o).getId();
		return this.id - compareId;
	}
}
