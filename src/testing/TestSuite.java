package testing;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.*;

import classes.Category;
import classes.Item;
import classes.MaintainCategory;
import classes.MaintainItem;
import classes.MaintainStore;
import classes.MaintainUser;
import classes.Store;
import classes.User;

public class TestSuite {
	
	MaintainItem maintainItem;
	MaintainCategory maintainCategory;
	MaintainStore maintainStore;
	MaintainUser maintainUser;
	
	@Before
	public void setUp() throws Exception {
		maintainItem = new MaintainItem("resources/csvs/items.csv", 0);
		maintainCategory = new MaintainCategory("resources/csvs/categories.csv", maintainItem, 0);
		maintainStore = new MaintainStore("resources/csvs/stores.csv", 0);
		
		maintainUser = new MaintainUser("resources/csvs/users.csv");
	}
	
	@Test
	public void testUpdate() throws Exception {
		maintainCategory.update(maintainCategory.getPath());
		maintainItem.update(maintainItem.getPath());
		maintainStore.update(maintainStore.getPath());
		maintainUser.update(maintainUser.getPath());
		
		maintainStore.reload();
	}
	
	@Test
	public void testItem() throws Exception {
		Item item = new Item();
		ArrayList<Integer> stores = new ArrayList<Integer>();
		stores.add(0);
		stores.add(1);
		
		item.setId(0);
		item.setName("Apple");
		item.setInventory(10);
		item.setAvailability(true);
		item.setPrice(4.55);
		item.setDescription("delicious apple (already sliced of course)");
		item.setSize(300);
		item.setOnSale(false);
		item.setStores(stores);
		item.setImagePath("resources/itemImages/apple.png");
		
		Item itemToCheck = maintainItem.getItemById(0);
		
		assertEquals(itemToCheck.getName(), item.getName());
	}
	
	@Test
	public void testItem2() throws Exception {
		
		ArrayList<Integer> stores = new ArrayList<Integer>();
		
		stores.add(0);
		stores.add(1);
		
		Item item = new Item(0, "Apple", 10, true, 4.55, "delicious apple (already sliced of course)", 300, false, stores, "resources/itemImages/apple.png");
		
		Item itemToCheck = maintainItem.getItemById(0);
		
		assertEquals(itemToCheck.getName(), item.getName());
	}
	
	@Test
	public void testCategoryId() throws Exception {
		Category category = new Category();
		ArrayList<Item> items = new ArrayList<Item>();
		
		items.add(maintainItem.getItemByName("Ritz Crackers"));
		items.add(maintainItem.getItemByName("Peanuts"));
		
		category.setId(maintainCategory.generateCategoryId());
		category.setName("pantry");
		category.setItems(items);
		category.setVisible(true);
		
		int size = maintainCategory.getCategories().size();
		
		assertEquals(size, category.getId());
	}
	
	@Test
	public void testCategory() throws Exception {
		Category category = new Category();
		ArrayList<Item> items = new ArrayList<Item>();
		
		items.add(maintainItem.getItemByName("Ritz Crackers"));
		items.add(maintainItem.getItemByName("Peanuts"));
		
		category.setId(2);
		category.setName("pantry");
		category.setItems(items);
		category.setVisible(true);
		
		Category categoryToCheck = maintainCategory.getCategoryById(2);
		
		assertEquals(categoryToCheck.getName(), category.getName());
	}
	
	@Test
	public void testCategoryAddRemove() throws Exception {
		ArrayList<Category> expected = new ArrayList<Category>();
		
		Category c0 = new Category(0, "produce", maintainCategory.getCategoryById(0).getItems(), true);
		Category c1 = new Category(1, "frozen", maintainCategory.getCategoryById(1).getItems(), true);
		Category c2 = new Category(2, "pantry", maintainCategory.getCategoryById(2).getItems(), true);
		Category c3 = new Category(3, "pharmacy", maintainCategory.getCategoryById(3).getItems(), true);
		Category c4 = new Category(4, "dairy", maintainCategory.getCategoryById(4).getItems(), true);
		Category c5 = new Category(5, "meat", maintainCategory.getCategoryById(5).getItems(), true);
		
		expected.add(c0);
		expected.add(c1);
		expected.add(c2);
		expected.add(c3);
		expected.add(c4);
		expected.add(c5);
		
		Category category = new Category();
		ArrayList<Item> items = new ArrayList<Item>();
		
		items.add(maintainItem.getItemByName("Ritz Crackers"));
		items.add(maintainItem.getItemByName("Ritz Crackers"));
		items.add(maintainItem.getItemByName("Ritz Crackers"));
		items.add(maintainItem.getItemByName("Ritz Crackers"));
		
		category.setId(2);
		category.setName("pantry");
		category.setItems(items);
		category.setVisible(true);
		
		maintainCategory.addItemToCategory(maintainItem.getItemByName("Peanuts"), "pantry");
		maintainCategory.removeItemFromCategory(maintainItem.getItemByName("Peanuts"), "pantry");
		
		assertEquals(expected.size(), maintainCategory.getCategories().size());
	}
	
	@Test
	public void testUserAdmin() throws Exception {
		User admin = new User();
		
		admin.setId(0);
		admin.setEmail("admin");
		admin.setPassword("admin");
		admin.setPermission("admin");
		admin.setStoreId(0);
		
		assertEquals(true, maintainUser.userExists(admin.getEmail(), admin.getPassword()));
	}
	
	@Test
	public void testUserId() {
		int size = maintainUser.getUsers().size();
		int id = maintainUser.generateUserId();
		
		assertEquals(size, id);
	}
	
	@Test
	public void testStoreId() {
		int size = maintainStore.getStores().size();
		int id = maintainStore.generateStoreId();
		
		assertEquals(size, id);
	}
	
	@Test
	public void testItemIds() {
		ArrayList<Integer> itemIds = new ArrayList<Integer>();
		
		for(int i = 0; i < 33; i++) {
			itemIds.add(i);
		}
		
		assertEquals(itemIds, maintainItem.getItemIds());
	}
	
	@Test
	public void testItemId() {
		int size = maintainItem.getItems().size();
		int id = maintainItem.generateItemId();
		
		assertEquals(size, id);
	}
	
	@Test
	public void testGetCurrentStore() {
		Category cStore = maintainStore.getCategoryById(0);
		Category cCategory = maintainCategory.getCategoryById(0);
		
		Store store = maintainStore.getCurrentStore();
		User user = maintainUser.getUserByEmail("admin");
		
		maintainStore.getCategoryByName("produce");
		maintainStore.getItemByName("Apple");
		maintainStore.getCurrentStore();
		maintainStore.getStores();
		
		assertEquals(store, maintainStore.getStore(user.getStoreId()));
	}
	
	@Test
	public void testGetCurrentStore2() {
		Store store = maintainStore.getCurrentStore();
		Store storeExpected = maintainStore.getStores().get(0);
		
		assertEquals(storeExpected, store);
	}
	
	@Test
	public void testGetCategory() {
		Item apple = maintainItem.getItemByName("Apple");
		
		Category c = maintainCategory.getCategoryByItem(apple);
		
		assertEquals(maintainCategory.getCategoryByName("produce"), c);
	}
	
	@Test
	public void testStoreItems() {
		ArrayList<Item> items = maintainItem.getItems();
		ArrayList<Item> storeItems = maintainStore.getCurrentStore().getStoreItems();
		
		assertEquals(items.size(), storeItems.size());
	}
}
