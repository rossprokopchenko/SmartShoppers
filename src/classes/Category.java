package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Category implements Comparable{
	private int id;
	private String name;
	private ArrayList<Item> items;
	private boolean isVisible;
	
	public Category(int id, String name, ArrayList<Item> items, boolean isVisible) {
		super();
		this.id = id;
		this.name = name;
		this.items = items;
		this.isVisible = isVisible;
	}
	
	public Category() {
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
	public String getItemsString() {
		String s = "";
		
		while (items.remove(null)) {
        }
		
		Collections.sort(items, new Comparator<Item>(){
		     public int compare(Item o1, Item o2){
		         if(o1.getId() == o2.getId())
		             return 0;
		         return o1.getId() < o2.getId() ? -1 : 1;
		     }
		});
		
		for(Item i : items) {
			if (i != null) {
				s += i.getId() + ",";
			}
		}
		
		if (!s.equals("")) {
			s = s.substring(0, s.length() - 1);
		}
		
		return s;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	public void addItem(Item item) {
		items.add(item);
	}
	public void removeItem(Item item) {
		items.remove(item);
	}
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", items=[" + getItemsString() + "], visible=" + isVisible + "]";
	}
	
	@Override
	public int compareTo(Object o) {
		int compareId = ((Category)o).getId();
		return this.id - compareId;
	}
}
