package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import classes.Category;
import classes.Item;
import classes.MaintainCategory;
import classes.MaintainItem;
import classes.MaintainStore;
import classes.MaintainUser;
import classes.User;

import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;

public class ItemFrame extends StoreFrame {

	private JPanel contentPane;
	private JFrame frame;
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	private JLabel lblItemImage;
	private JLabel lblItemName;
	private JLabel lblDescription;
	private JLabel lblPricePerSize;
	private JLabel lblAvailability;
	private JLabel lblOnSale;
	private JLabel lblCategory;
	
	private Item item;
	
	private JButton btnCancel;
	private JButton btnSave;
	private JButton btnEditItem;
	
	private JTextField imageField, nameField, descriptionField, priceField, sizeField, inventoryField;
	private JComboBox categoryCombo;
	private JCheckBox saleCheckbox;
	
	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public ItemFrame(User user, Item item, boolean editPermissions, MaintainUser maintainUser, MaintainCategory maintainCategory, MaintainItem maintainItem, 
			MaintainStore maintainStore, Map<Item, JSpinner> itemSpinners, JButton btnReset) throws Exception {
		super(user, maintainUser, maintainStore, itemSpinners);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame = this;
		this.item = item;
		setBounds(100, 100, 350, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(236, 228, 183));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel(null);
		panel.setBounds(0,0,334,276);
		panel.setBackground(new Color(236, 228, 183));
		
		renderItemInfo(panel, maintainCategory);
		
		contentPane.setLayout(null);
		contentPane.add(panel);
		
		if(user.getPermission().equals("manager") || user.getPermission().equals("admin")) {
			btnSave = new JButton("");
			btnSave.setBounds(224, 287, 30, 23);
			btnSave.setToolTipText("Save Changes");
			btnSave.setEnabled(false);
			btnSave.setIcon(new ImageIcon("resources/icons/checkmark.png"));
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String name = nameField.getText();
					String category = (String) categoryCombo.getSelectedItem();
					int inventory = Integer.valueOf(inventoryField.getText());
					boolean isAvailable = true;
					if(Integer.valueOf(inventoryField.getText()) == 0) isAvailable = false;
					double price = Double.valueOf(priceField.getText());
					String description = descriptionField.getText();
					int size = Integer.valueOf(sizeField.getText());
					boolean onSale = saleCheckbox.isSelected();
					String imagePath = imageField.getText();
					
					item.setName(name);
					item.setInventory(inventory);
					item.setAvailability(isAvailable);
					item.setPrice(price);
					item.setDescription(description);
					item.setSize(size);
					item.setOnSale(onSale);
					item.setImagePath(imagePath);
					
					try {
						maintainItem.updateItem(item.getId(), item);
						
						if(!category.equals(maintainCategory.getCategoryByItem(item).getName())) {
							maintainCategory.addItemToCategory(item, category);
							maintainCategory.removeItemFromCategory(item, lblCategory.getText());
						}
						
						loadStore();
						maintainCategory.update(maintainCategory.getPath());
						resetStoreCsv();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					panel.removeAll();
					renderItemInfo(panel, maintainCategory);
					btnCancel.setEnabled(false);
					btnSave.setEnabled(false);
					btnEditItem.setEnabled(true);
					
					panel.repaint();
					panel.revalidate();
				}
			});
			contentPane.add(btnSave);
			
			btnCancel = new JButton("");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panel.removeAll();
					renderItemInfo(panel, maintainCategory);
					btnCancel.setEnabled(false);
					btnSave.setEnabled(false);
					btnEditItem.setEnabled(true);
					
					panel.repaint();
					panel.revalidate();
				}
			});
			btnCancel.setBounds(80, 287, 30, 23);
			btnCancel.setToolTipText("Cancel Changes");
			btnCancel.setEnabled(false);
			btnCancel.setIcon(new ImageIcon("resources/icons/x.png"));
			contentPane.add(btnCancel);
			
			btnEditItem = new JButton("Edit Item");
			btnEditItem.setBounds(120, 287, 94, 23);
			btnEditItem.setFocusable(false);
			btnEditItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					imageField = new JTextField();
					imageField.setBounds(50, 40, 240, 25);
					imageField.setColumns(10);
					imageField.setText(item.getImagePath());
					imageField.setToolTipText("Item Image Path");
					panel.add(imageField);
					
					panel.remove(lblItemImage);
					
					nameField = new JTextField();
					nameField.setBounds(50,80,240, 25);
					nameField.setColumns(10);
					nameField.setText(item.getName());
					nameField.setToolTipText("Item Name");
					panel.add(nameField);
					
					panel.remove(lblItemName);
					
					String[] categoryStrings = new String[maintainStore.getCurrentStore().getCategories().size()];
					
					for(int i = 0; i < maintainStore.getCurrentStore().getCategories().size(); i++) {
						categoryStrings[i] = maintainStore.getCurrentStore().getCategories().get(i).getName();
					}
					
					categoryCombo = new JComboBox(categoryStrings);
					categoryCombo.setBounds(50, 110, 240, 25);
					categoryCombo.setSelectedItem(lblCategory.getText());
					panel.add(categoryCombo);
					
					panel.remove(lblCategory);
					
					descriptionField = new JTextField();
					descriptionField.setBounds(50,140,240, 25);
					descriptionField.setColumns(10);
					descriptionField.setText(item.getDescription());
					descriptionField.setToolTipText("Item Description");
					panel.add(descriptionField);
					
					panel.remove(lblDescription);
					
					priceField = new JTextField();
					priceField.setBounds(50,170,60, 30);
					priceField.setColumns(10);
					priceField.setText("" + item.getPrice());
					priceField.setToolTipText("Item Price");
					panel.add(priceField);
					
					sizeField = new JTextField();
					sizeField.setBounds(230,170,60, 30);
					sizeField.setColumns(10);
					sizeField.setText("" + item.getSize());
					sizeField.setToolTipText("Item Size");
					panel.add(sizeField);
					
					panel.remove(lblPricePerSize);
					
					inventoryField = new JTextField();
					inventoryField.setBounds(140,200,60, 30);
					inventoryField.setColumns(10);
					inventoryField.setText("" + item.getInventory());
					inventoryField.setToolTipText("Item Inventory");
					panel.add(inventoryField);
					
					panel.remove(lblAvailability);
					
					saleCheckbox = new JCheckBox();
					saleCheckbox.setBounds(130,230,100, 30);
					saleCheckbox.setText("On Sale");
					saleCheckbox.setBackground(new Color(236, 228, 183));
					if(item.isOnSale()) saleCheckbox.setSelected(true);
					else saleCheckbox.setSelected(false);
					panel.add(saleCheckbox);
					
					panel.remove(lblOnSale);
					
					btnCancel.setEnabled(true);
					btnSave.setEnabled(true);
					btnEditItem.setEnabled(false);
					
					panel.repaint();
					panel.revalidate();
				}
			});
		
			contentPane.add(btnEditItem);
			
			JButton btnDelete = new JButton("Delete Item");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Item item = maintainItem.getItemByName(lblItemName.getText().substring(0,lblItemName.getText().length()-1));
					
					try {
						maintainItem.deleteItem(item);
						maintainCategory.removeItemFromCategory(item, maintainCategory.getCategoryByItem(item).getName());
						
						btnReset.doClick();
						frame.dispose();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnDelete.setBounds(224, 327, 100, 23);
			btnDelete.setFocusable(false);
			btnDelete.setBackground(Color.RED);
			contentPane.add(btnDelete);
		}
		
		JButton btnBack = new JButton("Close");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnBack.setFocusable(false);
		btnBack.setForeground(Color.BLACK);
		btnBack.setBackground(new Color(221, 96, 49));
		btnBack.setBounds(10, 327, 100, 23);
		contentPane.add(btnBack);
	}
	
	public void renderItemInfo(JPanel panel, MaintainCategory maintainCategory) {
		lblItemImage = new JLabel(new ImageIcon(item.getImagePath()));
		lblItemImage.setBounds(10,11,314, 60);
		panel.add(lblItemImage);
		
		lblItemName = new JLabel(item.getName() + ":");
		lblItemName.setHorizontalAlignment(SwingConstants.CENTER);
		lblItemName.setBounds(10,82,314, 30);
		lblItemName.setFont(new Font("Dialog", Font.BOLD, 14));
		panel.add(lblItemName);
		
		lblCategory = new JLabel(maintainCategory.getCategoryByItem(item).getName());
		lblCategory.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategory.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblCategory.setBounds(10, 117, 314, 30);
		panel.add(lblCategory);
		
		lblDescription = new JLabel("");
		lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescription.setFont(new Font("Dialog", Font.ITALIC, 12));
		if(item.getDescription().equals("")) lblDescription.setText("No description set");
		else lblDescription.setText(item.getDescription());
		lblDescription.setBounds(10,148,314, 30);
		panel.add(lblDescription);
		
		lblPricePerSize = new JLabel("$" + df.format(item.getPrice()) + " per " + item.getSize() + "g");
		lblPricePerSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblPricePerSize.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblPricePerSize.setBounds(10,177,314, 30);
		panel.add(lblPricePerSize);
		
		lblAvailability = new JLabel("");
		lblAvailability.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvailability.setFont(new Font("Dialog", Font.PLAIN, 12));
		if(item.isAvailabile()) lblAvailability.setText("Item is in stock (" + item.getInventory() + ")");
		else lblAvailability.setText("Item is NOT in stock.");
		lblAvailability.setBounds(10,206,314, 30);
		panel.add(lblAvailability);
		
		lblOnSale = new JLabel("");
		lblOnSale.setHorizontalAlignment(SwingConstants.CENTER);
		lblOnSale.setFont(new Font("Dialog", Font.ITALIC, 13));
		lblOnSale.setBounds(10,235,314, 30);
		if(item.isOnSale()) lblOnSale.setText("Item is on sale");
		else lblOnSale.setText("Item is NOT on sale.");
		panel.add(lblOnSale);
	}
	
	public void loadStore() throws Exception {
		super.loadStore();
	}
	
	public void resetStoreCsv() throws Exception {
		super.resetStoreCsv();
	}
}
