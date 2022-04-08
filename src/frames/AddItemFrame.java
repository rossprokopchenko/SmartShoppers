package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import classes.Category;
import classes.Item;
import classes.MaintainCategory;
import classes.MaintainItem;
import classes.MaintainStore;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.JButton;

public class AddItemFrame extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	private JTextField newCategoryField;
	
	private JComboBox categoryCombo;

	/**
	 * Create the frame.
	 */
	public AddItemFrame(MaintainStore maintainStore, MaintainCategory maintainCategory, MaintainItem maintainItem, JButton btnReset) {
		frame = this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(new Color(236, 228, 183));
		contentPane.setLayout(null);
		
		String[] categoryStrings = new String[maintainStore.getCurrentStore().getCategories().size()];
		
		for(int i = 0; i < maintainStore.getCurrentStore().getCategories().size(); i++) {
			categoryStrings[i] = maintainStore.getCurrentStore().getCategories().get(i).getName();
		}
		
		JTextField imageField = new JTextField();
		imageField.setBounds(50, 141, 240, 25);
		imageField.setColumns(10);
		imageField.setToolTipText("Item Image Path");
		contentPane.add(imageField);
		
		JTextField nameField = new JTextField();
		nameField.setBounds(50,260,240, 25);
		nameField.setColumns(10);
		nameField.setToolTipText("Item Name");
		contentPane.add(nameField);
		
		categoryCombo = new JComboBox(categoryStrings);
		categoryCombo.setBounds(50, 202, 240, 22);
		contentPane.add(categoryCombo);
		
		JTextField descriptionField = new JTextField();
		descriptionField.setBounds(50,321,240, 25);
		descriptionField.setColumns(10);
		descriptionField.setToolTipText("Item Description");
		contentPane.add(descriptionField);
		
		JTextField priceField = new JTextField();
		priceField.setBounds(50,382,60, 25);
		priceField.setColumns(10);
		priceField.setToolTipText("Item Price");
		contentPane.add(priceField);
		
		JTextField sizeField = new JTextField();
		sizeField.setBounds(230,382,60, 25);
		sizeField.setColumns(10);
		sizeField.setToolTipText("Item Size");
		contentPane.add(sizeField);
		
		JTextField inventoryField = new JTextField();
		inventoryField.setBounds(135,382,70, 25);
		inventoryField.setColumns(10);
		inventoryField.setToolTipText("Item Inventory");
		contentPane.add(inventoryField);
		
		JCheckBox saleCheckbox = new JCheckBox();
		saleCheckbox.setText("On Sale?");
		saleCheckbox.setBounds(135,414,121, 25);
		saleCheckbox.setBackground(new Color(236, 228, 183));
		contentPane.add(saleCheckbox);
		
		JLabel lblImagePath = new JLabel("Image Path");
		lblImagePath.setBounds(50, 116, 100, 14);
		contentPane.add(lblImagePath);
		
		JLabel lblCategory = new JLabel("Category");
		lblCategory.setBounds(50, 177, 100, 14);
		contentPane.add(lblCategory);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(50, 235, 100, 14);
		contentPane.add(lblName);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(50, 296, 100, 14);
		contentPane.add(lblDescription);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(50, 357, 100, 14);
		contentPane.add(lblPrice);
		
		JLabel lblSize = new JLabel("Size");
		lblSize.setBounds(230, 357, 60, 14);
		contentPane.add(lblSize);
		
		JLabel lblInventory = new JLabel("Inventory");
		lblInventory.setBounds(135, 357, 85, 14);
		contentPane.add(lblInventory);
		
		JButton btnBack = new JButton("Close");
		btnBack.setForeground(Color.BLACK);
		btnBack.setBackground(new Color(221, 96, 49));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnBack.setBounds(10, 456, 100, 25);
		contentPane.add(btnBack);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String imagePath = imageField.getText();
				if(imagePath.equals("")) imagePath = "resources/itemImages/question.png";
				String name = nameField.getText();
				String category = String.valueOf(categoryCombo.getSelectedItem());
				String description = descriptionField.getText();
				double price = Double.valueOf(priceField.getText());
				int size = Integer.valueOf(sizeField.getText());
				int inventory = Integer.valueOf(inventoryField.getText());
				boolean onSale = saleCheckbox.isSelected();
				
				ArrayList<Integer> storeList = new ArrayList<Integer>();
				storeList.add(maintainStore.getCurrentStore().getId());
				
				Item newItem = new Item(maintainItem.generateItemId(), name, inventory, true, price, description, size, onSale, storeList, imagePath);
				
				try {
					maintainItem.add(newItem);
					maintainCategory.addItemToCategory(newItem, category);
					maintainCategory.update(maintainCategory.getPath());
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				btnReset.doClick();
			}
		});
		btnAddItem.setForeground(Color.BLACK);
		btnAddItem.setBackground(new Color(221, 96, 49));
		btnAddItem.setBounds(234, 456, 100, 25);
		contentPane.add(btnAddItem);
		
		JLabel lblNewCategory = new JLabel("New Category");
		lblNewCategory.setBounds(50, 11, 100, 14);
		contentPane.add(lblNewCategory);
		
		newCategoryField = new JTextField();
		newCategoryField.setToolTipText("Category to add");
		newCategoryField.setColumns(10);
		newCategoryField.setBounds(50, 36, 240, 25);
		contentPane.add(newCategoryField);
		
		JButton btnAddCategory = new JButton("Add Category");
		btnAddCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newCategoryName = newCategoryField.getText();
				int newCategoryId = maintainCategory.generateCategoryId();
				
				Category newCategory = new Category(newCategoryId, newCategoryName, new ArrayList<Item>(), true);
				
				try {
					maintainCategory.add(newCategory);
					maintainStore.addCategoryToCurrentStore(newCategory);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				String[] categoryStrings = new String[maintainStore.getCurrentStore().getCategories().size()];
				
				for(int i = 0; i < maintainStore.getCurrentStore().getCategories().size(); i++) {
					categoryStrings[i] = maintainStore.getCurrentStore().getCategories().get(i).getName();
				}
				
				categoryCombo = new JComboBox(categoryStrings);
				
				btnReset.doClick();
				
				contentPane.repaint();
				contentPane.revalidate();
			}
		});
		btnAddCategory.setForeground(Color.BLACK);
		btnAddCategory.setBackground(new Color(221, 96, 49));
		btnAddCategory.setBounds(110, 72, 115, 25);
		contentPane.add(btnAddCategory);
		
		
	}
}
