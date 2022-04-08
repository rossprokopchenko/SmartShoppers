package frames;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.border.LineBorder;

import classes.MaintainStore;
import classes.MaintainUser;
import classes.User;
import classes.Category;
import classes.Item;
import classes.MaintainCategory;
import classes.MaintainItem;

import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.SystemColor;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class StoreFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame frame;
	private JTextField txtSearch;
	
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	private final int CATEGORY_WIDTH = 100;
	private final int CATEGORY_HEIGHT = 25;
	private final int CATEGORY_GAP_Y = 30;
	
	private final int ITEM_WIDTH = 95;
	private final int ITEM_HEIGHT = 130;
	
	private MaintainUser maintainUser;
	private MaintainStore maintainStore;
	private MaintainCategory maintainCategory;
	private MaintainItem maintainItem;
	
	private Category onSale;
	private ArrayList<Item> onSaleItems;
	
	private User user;
	
	private Map<Item, JSpinner> itemSpinners;
	private ArrayList<Category> selectedCategories;
	
	private JButton btnCart;
	private JButton btnReset;
	private JCheckBox checkboxSelectAll;
	private JPanel itemsPanel;
	private JPanel storePanel;
	private JPanel categoryPanel;
	private JScrollPane scroll;
	
	private int numItems;
	private int numCategories;
	private JCheckBox[] categoryCheckboxes;
	
	/**
	 * Create the frame.
	 */
	public StoreFrame(User user, MaintainUser maintainUser, MaintainStore maintainStore, Map<Item, JSpinner> itemSpinners) throws Exception{
		frame = this;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.user = user;
		this.maintainStore = maintainStore;
		this.maintainCategory = maintainStore.maintainCategory;
		this.maintainItem = maintainStore.maintainItem;
		this.maintainUser = maintainUser;
		this.itemSpinners = itemSpinners;
		
		setBounds(100, 100, 780, 620);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(236, 228, 183));
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		categoryPanel = new JPanel();
		categoryPanel.setBackground(new Color(217, 221, 146));
		categoryPanel.setBorder(new LineBorder(new Color(64, 64, 64), 2, true));
		categoryPanel.setBounds(10, 67, 160, 449);
		contentPane.add(categoryPanel);
		categoryPanel.setLayout(null);
		
		storePanel = new JPanel(null);
		storePanel.setBackground(new Color(217, 221, 146));
		storePanel.setBorder(new LineBorder(new Color(64, 64, 64), 2, true));
		storePanel.setBounds(180, 67, 574, 503);
		contentPane.add(storePanel);
		
		JPanel storeItemPanel = new JPanel();
		storeItemPanel.setBounds(10, 70, 555, 420);
		
		itemsPanel = new JPanel(new FlowLayout());
		itemsPanel.setBackground(new Color(236, 228, 183));
		itemsPanel.setForeground(new Color(51, 204, 255));
		scroll = new JScrollPane(itemsPanel, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(10);
        
		loadStore();
        addStoreItems(selectedCategories, itemsPanel, scroll, btnReset);
        
        storeItemPanel.setLayout(new BorderLayout());
        storeItemPanel.add(scroll, BorderLayout.CENTER);
		
        storePanel.add(storeItemPanel);
		
		JPanel navbarPanel = new JPanel();
		navbarPanel.setBackground(new Color(217, 221, 146));
		navbarPanel.setBorder(new LineBorder(new Color(64, 64, 64), 2, true));
		navbarPanel.setBounds(10, 11, 744, 45);
		contentPane.add(navbarPanel);
		navbarPanel.setLayout(null);
		
		JLabel lblCompanyTag = new JLabel("ShoppersLand Inc.");
		lblCompanyTag.setHorizontalAlignment(SwingConstants.CENTER);
		lblCompanyTag.setForeground(new Color(49, 30, 16));
		lblCompanyTag.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblCompanyTag.setBounds(0, 0, 260, 45);
		navbarPanel.add(lblCompanyTag);
		
		JButton btnProfile = new JButton("Profile");
		btnProfile.setFocusable(false);
		btnProfile.setRequestFocusEnabled(false);
		btnProfile.setForeground(Color.white);
		btnProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ProfileFrame newFrame = new ProfileFrame(user, maintainUser, maintainStore, itemSpinners);
					
					BufferedImage myPicture = ImageIO.read(new File("resources/logo.png"));
					newFrame.setIconImage(myPicture);
					newFrame.setTitle("Profile | SmartShoppers");
					
					newFrame.setLocationRelativeTo(null);
					newFrame.setResizable(false);
					newFrame.setVisible(true);
					
					frame.dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnProfile.setBackground(new Color(49, 30, 16));
		btnProfile.setBounds(546, 11, 89, 25);
		navbarPanel.add(btnProfile);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setFocusable(false);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AuthenticationFrame newFrame = new AuthenticationFrame();
					
					BufferedImage myPicture = ImageIO.read(new File("resources/logo.png"));
					newFrame.frame.setIconImage(myPicture);
					newFrame.frame.setTitle("Login | SmartShoppers");
					
					frame.dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLogout.setBackground(Color.RED);
		btnLogout.setBounds(645, 11, 89, 25);
		navbarPanel.add(btnLogout);
		
		btnReset = new JButton("");
		JButton btnSystem = new JButton("System");
		btnSystem.setRequestFocusEnabled(false);
		btnSystem.setForeground(Color.white);
		btnSystem.setFocusable(false);
		btnSystem.setBackground(new Color(49, 30, 16));
		btnSystem.setBounds(447, 11, 89, 25);
		btnSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SystemFrame newFrame = new SystemFrame(maintainUser);
				
				try {
					BufferedImage myPicture = ImageIO.read(new File("resources/logo.png"));
					newFrame.setIconImage(myPicture);
					newFrame.setTitle("System | SmartShoppers");
					
					// centers window
					newFrame.setLocationRelativeTo(null);
					newFrame.setVisible(true);
					newFrame.setResizable(false);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		if(user.getPermission().equals("admin")) navbarPanel.add(btnSystem);
		
		btnCart = new JButton("Cart (0)");
		btnCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CartFrame newFrame = new CartFrame(user, maintainUser, maintainStore, itemSpinners);
				
				try {
					BufferedImage myPicture = ImageIO.read(new File("resources/logo.png"));
					newFrame.setIconImage(myPicture);
					newFrame.setTitle("Cart | SmartShoppers");
					
					// centers window
					newFrame.setLocationRelativeTo(null);
					newFrame.setVisible(true);
					newFrame.setResizable(false);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCart.setFocusable(false);
		btnCart.setForeground(Color.WHITE);
		btnCart.setBackground(new Color(49, 30, 16));
		btnCart.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCart.setBounds(10, 527, 160, 43);
		contentPane.add(btnCart);
		
		// CATEGORIES
		
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JCheckBox c : categoryCheckboxes) {
					if(c == null) continue;
					c.setSelected(true);
				}
				
				for(JSpinner spinner : itemSpinners.values()) {
					spinner.setValue(0);
				}
				
				checkboxSelectAll.setSelected(true);
				txtSearch.setText("");
				btnCart.setText("Cart (0)");
				
				try {
					resetStoreCsv();
					loadStore();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				itemsPanel.removeAll();
				addStoreItems(selectedCategories, itemsPanel, scroll, btnReset);
			}
		});
		btnReset.setFocusable(false);
		btnReset.setToolTipText("Reset Store Items and Categories");
		btnReset.setIcon(new ImageIcon("resources/icons/reset.png"));
		btnReset.setBounds(540, 39, 25, 25);
		storePanel.add(btnReset);
		
		JButton btnSearch = new JButton("");
		btnSearch.setToolTipText("Click to Search (or press Enter)");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtSearch.getText().equals("")) {
					btnReset.doClick();
					return;
				}
				
				boolean found = false;
				
				ArrayList<Category> foundCategories = new ArrayList<Category>();
				
				for(Category c : selectedCategories) {
					ArrayList<Item> foundItems = new ArrayList<Item>();
					
					for (Item i : c.getItems()) {
						if(i.getName().toLowerCase().contains(txtSearch.getText().toLowerCase())) {
							foundItems.add(i);
							found = true;
						}
					}
					Category foundCategory = new Category(c.getId(), c.getName(), foundItems, true);
					
					if(foundCategory.getItems().size() > 0) foundCategories.add(foundCategory);
				}
				
				if(found) {
					itemsPanel.removeAll();
					addStoreItems(foundCategories, itemsPanel, scroll, btnReset);
				} else {
					System.err.println("Item \"" + txtSearch.getText() + "\" not found");
				}
			}
		});
		btnSearch.setFocusable(false);
		btnSearch.setIcon(new ImageIcon("resources/icons/magnifyingGlass.png"));
		btnSearch.setBounds(140, 39, 25, 25);
		storePanel.add(btnSearch);
		
		txtSearch = new JTextField();
		txtSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(txtSearch.getText().equals("")) {
					txtSearch.setForeground(Color.LIGHT_GRAY);
					txtSearch.setFont(new Font("Tahoma", Font.ITALIC, 11));
					txtSearch.setText("Search...");
				}
			}
		});
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(txtSearch.getText().equals("Search...")) {
					txtSearch.setText("");
					txtSearch.setForeground(Color.BLACK);
					txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 11));
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnSearch.doClick();
				}
			}
		});
		txtSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(txtSearch.getText().equals("Search...")) {
					txtSearch.setText("");
					txtSearch.setForeground(Color.BLACK);
					txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 11));
				}
			}
		});
		txtSearch.setForeground(Color.LIGHT_GRAY);
		txtSearch.setFont(new Font("Tahoma", Font.ITALIC, 11));
		txtSearch.setText("Search...");
		txtSearch.setToolTipText("Item Search");
		txtSearch.setBounds(10, 42, 120, 20);
		storePanel.add(txtSearch);
		txtSearch.setColumns(10);
		
		if(user.getPermission().equals("manager") || user.getPermission().equals("admin")) {
			JButton btnAddItem = new JButton("");
			btnAddItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						AddItemFrame newFrame = new AddItemFrame(maintainStore, maintainCategory, maintainItem, btnReset);
						
						BufferedImage myPicture = ImageIO.read(new File("resources/logo.png"));
						newFrame.setIconImage(myPicture);
						newFrame.setTitle("Add New Item | SmartShoppers");
						
						// centers window
						newFrame.setLocationRelativeTo(null);
						newFrame.setVisible(true);
						newFrame.setResizable(false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnAddItem.setToolTipText("Add Category / Item");
			btnAddItem.setFocusable(false);
			btnAddItem.setIcon(new ImageIcon("resources/icons/plus.png"));
			btnAddItem.setBounds(505, 39, 25, 25);
			storePanel.add(btnAddItem);
			
			JButton btnEditStore = new JButton("");
			btnEditStore.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						EditStoreFrame newFrame = new EditStoreFrame(maintainStore, maintainCategory, btnReset);
						
						BufferedImage myPicture = ImageIO.read(new File("resources/logo.png"));
						newFrame.setIconImage(myPicture);
						newFrame.setTitle("Edit Store | SmartShoppers");
						
						// centers window
						newFrame.setLocationRelativeTo(null);
						newFrame.setVisible(true);
						newFrame.setResizable(false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnEditStore.setToolTipText("Edit Store");
			btnEditStore.setIcon(new ImageIcon("resources/icons/edit.png"));
			btnEditStore.setFocusable(false);
			btnEditStore.setBounds(470, 39, 25, 25);
			storePanel.add(btnEditStore);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addStoreItems(ArrayList<Category> categories, JPanel storeItemPanel, JScrollPane scroll, JButton btnReset) {
		int itemRows = 0;
		int catNum = 0;
		
		Collections.sort(categories);
		
		for(Category category : categories) {
			//System.out.println(category);
			if(category.getItems().contains(null) || category.getItems().size() == 0) {
				boolean allNull = true;
				for(int i = 0; i < category.getItems().size(); i++) {
					if (category.getItems().get(i) != null) allNull = false;
				}
				
				if(allNull) continue;
			}
			
			ArrayList<Item> items = category.getItems();
			itemRows += (int) Math.ceil((double) items.size() / 5);
			
			String categoryName = category.getName().substring(0, 1).toUpperCase() + category.getName().substring(1);
			JLabel lblCategoryName = new JLabel(categoryName + " \u2193");
			
			if (catNum != 0) lblCategoryName.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Color.black));
			lblCategoryName.setHorizontalAlignment(SwingConstants.CENTER);
			lblCategoryName.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblCategoryName.setPreferredSize(new Dimension(500, 25));
			storeItemPanel.add(lblCategoryName);
			catNum++;
			
			for(Item item : items) {
				if(item == null) {
					continue;
				}
				
				JPanel itemPanel = new JPanel();
				itemPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
				itemPanel.setBackground(new Color(234, 190, 124));
				itemPanel.setPreferredSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
				itemPanel.setLayout(null);
				itemPanel.addMouseListener(new MouseAdapter() {
					@Override 
					public void mousePressed(MouseEvent e) {
						boolean editPermissions = false;
						
						if(user.getPermission().equals("manager") || user.getPermission().equals("admin")) {
							editPermissions = true;
						}
						
						try {
							ItemFrame newFrame = new ItemFrame(user, item, editPermissions, maintainUser, maintainCategory, maintainItem, maintainStore, itemSpinners, btnReset);
							
							BufferedImage myPicture = ImageIO.read(new File("resources/logo.png"));
							newFrame.setIconImage(myPicture);
							newFrame.setTitle(item.getName() + " Info | SmartShoppers");
							
							// centers window
							newFrame.setLocationRelativeTo(null);
							newFrame.setVisible(true);
							newFrame.setResizable(false);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						itemPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
						frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						itemPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
						frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}
				});
				
				JLabel lblItemImage = new JLabel(new ImageIcon(item.getImagePath()));
				lblItemImage.setBounds(22, 5, 50, 50);
				
				itemPanel.add(lblItemImage);
				
				JLabel lblItemName = new JLabel("<html><div style=\"text-align: center\">" + item.getName() + "</div></html>", SwingConstants.CENTER);
				lblItemName.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblItemName.setBounds(10, 64, 75, 25);
				itemPanel.add(lblItemName);
				
				JLabel lblItemPrice = new JLabel();
				lblItemPrice.setText("$" + df.format(item.getPrice()));
				lblItemPrice.setBounds(10, 105, 40, 14);
				itemPanel.add(lblItemPrice);
				
				if(!category.getName().equals("On Sale")) {
					JSpinner spinner = itemSpinners.get(item);
					
					if(spinner == null) continue;
					
					spinner.setRequestFocusEnabled(false);
					spinner.setFocusable(false);
					spinner.setModel(new SpinnerNumberModel((int) spinner.getValue(), 0, item.getInventory(), 1));
					spinner.setBounds(50, 102, 33, 20);
					spinner.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent e) {
							int sum = 0;
							itemSpinners.put(item, spinner);
							
							for (JSpinner spinner : itemSpinners.values()) {
								sum += (Integer) spinner.getValue();
							}
							
							setNumItems(sum);
						}
					});
					
					itemPanel.add(spinner);
				}
				
				itemPanel.validate();
				storeItemPanel.add(itemPanel);
				storeItemPanel.revalidate();
				storeItemPanel.repaint();
			}
		}
		
		int panelHeight = 10 + ((itemRows * (ITEM_HEIGHT + 5)) + (25 + 5) * categories.size());
		
		storeItemPanel.setPreferredSize(new Dimension(scroll.getWidth(), panelHeight));
	}
	
	public void setNumItems(int num) {
		btnCart.setText("Cart (" + num + ")");
	}
	
	public void loadStore() throws Exception {
		selectedCategories = new ArrayList<Category>();
		
		JLabel lblStoreName = new JLabel(maintainStore.getStore(user.getStoreId()).getName() + " - " + maintainStore.getStore(user.getStoreId()).getAddress());
		lblStoreName.setForeground(new Color(221, 96, 49));
		lblStoreName.setHorizontalAlignment(SwingConstants.LEFT);
		lblStoreName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblStoreName.setBounds(10, 0, 410, 31);
		storePanel.add(lblStoreName);
		
		
		for(Category s : maintainStore.getCurrentStore().getCategories()) {
			selectedCategories.add(s);
		}
		
		numCategories = maintainStore.getCurrentStore().getCategories().size();
		numItems = maintainStore.getCurrentStore().getStoreItems().size();
		
		onSale = new Category(-1, "On Sale", new ArrayList<Item>(), true);
		onSaleItems = new ArrayList<Item>();
		
		for(Category c : maintainStore.getCurrentStore().getCategories()) {
			for(Item i : maintainStore.getCurrentStore().getStoreItems()) {
				if (i.isOnSale() && !onSaleItems.contains(i)) {
					onSaleItems.add(i);
				}
			}
		}
		
		for(Item i : maintainStore.getCurrentStore().getStoreItems()) {
			itemSpinners.put(i, new JSpinner());
		}
		
		onSale.setItems(onSaleItems);
		if(onSaleItems.size() > 0)
		selectedCategories.add(onSale);
		Collections.sort(selectedCategories);
		
		JLabel lblStoreHours = new JLabel(maintainStore.getStore(user.getStoreId()).getOpeningHours() + " - " + maintainStore.getStore(user.getStoreId()).getClosingHours());
		lblStoreHours.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStoreHours.setForeground(new Color(221, 96, 49));
		lblStoreHours.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStoreHours.setBounds(428, 0, 137, 31);
		storePanel.add(lblStoreHours);

		loadCategories();
	}
	
	public void loadCategories() {
		categoryPanel.removeAll();
		
		JLabel lblCategories = new JLabel("Categories");
		lblCategories.setForeground(new Color(221, 96, 49));
		lblCategories.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategories.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCategories.setBounds(10, 0, 140, 31);
		categoryPanel.add(lblCategories);
		
		categoryCheckboxes = new JCheckBox[numCategories+1];
		
		for (int i = 0; i <= numCategories; i++) {
			Category c;
			if(i == numCategories) c = onSale;
			else c = maintainStore.getCurrentStore().getCategories().get(i);
			
			final int index = i;
			
			categoryCheckboxes[i] = new JCheckBox(c.getName());
			categoryCheckboxes[i].setBackground(new Color(217, 221, 146));
			categoryCheckboxes[i].setBounds(10, 38 + (CATEGORY_GAP_Y * i), CATEGORY_WIDTH, CATEGORY_HEIGHT);
			if (i == numCategories && onSaleItems.size() == 0 || c.getItemsString().equals("")) categoryCheckboxes[i].setSelected(false);
			else categoryCheckboxes[i].setSelected(true);
			categoryCheckboxes[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!categoryCheckboxes[index].isSelected()) {
						Category category;
						if(index == numCategories && onSaleItems.size() > 0) category = onSale;
						else if(index == numCategories && onSaleItems.size() == 0) return;
						else category = maintainStore.getCategoryById(index);
						
						selectedCategories.remove(category);
						
						itemsPanel.removeAll();
						
						addStoreItems(selectedCategories, itemsPanel, scroll, btnReset);
						itemsPanel.revalidate();
						itemsPanel.repaint();
						
					} else if (categoryCheckboxes[index].isSelected()) {
						Category category;
						if(index == numCategories && onSaleItems.size() > 0) category = onSale;
						else if(index == numCategories && onSaleItems.size() == 0) return;
						else category = maintainStore.getCategoryById(index);
						
						selectedCategories.add(category);
						
						itemsPanel.removeAll();
						
						addStoreItems(selectedCategories, itemsPanel, scroll, btnReset);
					}
				}
			});
			categoryPanel.add(categoryCheckboxes[i]);
		}
		
		checkboxSelectAll = new JCheckBox("Select All");
		checkboxSelectAll.setBackground(new Color(217, 221, 146));
		checkboxSelectAll.setFocusable(false);
		checkboxSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkboxSelectAll.isSelected()) {
					for (JCheckBox c : categoryCheckboxes) {
						if(c != null) c.setSelected(true);
					}
					
					selectedCategories = new ArrayList<Category>(maintainStore.getCurrentStore().getCategories());
					selectedCategories.add(onSale);
					Collections.sort(selectedCategories);
					
					itemsPanel.removeAll();
					addStoreItems(selectedCategories, itemsPanel, scroll, btnReset);
					
				} else if (!checkboxSelectAll.isSelected()) {
					for (JCheckBox c : categoryCheckboxes) {
						if(c != null) c.setSelected(false);
					}
					
					selectedCategories.removeAll(selectedCategories);
					
					addStoreItems(selectedCategories, itemsPanel, scroll, btnReset);
					
					itemsPanel.removeAll();
					itemsPanel.revalidate();
					itemsPanel.repaint();
				}
			}
		});
		checkboxSelectAll.setSelected(true);
		checkboxSelectAll.setBounds(10, 419, 100, 25);
		categoryPanel.add(checkboxSelectAll);
	}
	
	public void resetStoreCsv() throws Exception{
		maintainStore = new MaintainStore("resources/csvs/stores.csv", user.getStoreId());
		maintainItem = maintainStore.maintainItem;
		maintainCategory = maintainStore.maintainCategory;
	}
}