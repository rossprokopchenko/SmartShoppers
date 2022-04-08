package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import classes.Category;
import classes.Item;
import classes.MaintainCategory;
import classes.MaintainStore;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;

public class EditStoreFrame extends JFrame {
	
	private JFrame frame;

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField addressField;
	private JTextField densityField;
	private JTextField openingField;
	private JTextField closingField;
	private JTextField categoryField;
	private JButton btnBack;
	private JButton btnSaveChanges;

	/**
	 * Create the frame.
	 */
	public EditStoreFrame(MaintainStore maintainStore, MaintainCategory maintainCategory, JButton btnReset) {
		frame = this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 540, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(new Color(236, 228, 183));
		contentPane.setLayout(null);
		
		String storeName = maintainStore.getCurrentStore().getName();
		String storeAddress = maintainStore.getCurrentStore().getAddress();
		int density = maintainStore.getCurrentStore().getDensity();
		String openingHours = maintainStore.getCurrentStore().getOpeningHours();
		String closingHours = maintainStore.getCurrentStore().getClosingHours();
		String categoriesString = maintainStore.getCurrentStore().getCategoriesString();
		
		JLabel lblStoreName = new JLabel("Store Name");
		lblStoreName.setBounds(10, 11, 100, 14);
		contentPane.add(lblStoreName);
		
		nameField = new JTextField();
		nameField.setText(storeName);
		nameField.setColumns(10);
		nameField.setBounds(10, 36, 240, 25);
		contentPane.add(nameField);
		
		JLabel lblCategories = new JLabel("Categories");
		lblCategories.setBounds(274, 11, 100, 14);
		contentPane.add(lblCategories);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(10, 72, 100, 14);
		contentPane.add(lblAddress);
		
		addressField = new JTextField();
		addressField.setText(storeAddress);
		addressField.setColumns(10);
		addressField.setBounds(10, 97, 240, 25);
		contentPane.add(addressField);
		
		JLabel lblDensity = new JLabel("Density");
		lblDensity.setBounds(10, 133, 100, 14);
		contentPane.add(lblDensity);
		
		densityField = new JTextField();
		densityField.setText("" + density);
		densityField.setColumns(10);
		densityField.setBounds(10, 158, 240, 25);
		contentPane.add(densityField);
		
		JLabel lblOpeningHours = new JLabel("Opening Hours");
		lblOpeningHours.setBounds(10, 194, 100, 14);
		contentPane.add(lblOpeningHours);
		
		openingField = new JTextField();
		openingField.setText(openingHours);
		openingField.setColumns(10);
		openingField.setBounds(10, 219, 240, 25);
		contentPane.add(openingField);
		
		JLabel lblClosingHours = new JLabel("Closing Hours");
		lblClosingHours.setBounds(10, 255, 100, 14);
		contentPane.add(lblClosingHours);
		
		closingField = new JTextField();
		closingField.setText(closingHours);
		closingField.setColumns(10);
		closingField.setBounds(10, 280, 240, 25);
		contentPane.add(closingField);
		
		categoryField = new JTextField();
		categoryField.setText(categoriesString);
		categoryField.setColumns(10);
		categoryField.setBounds(274, 282, 240, 25);
		contentPane.add(categoryField);
		
		DefaultListModel<String> list = new DefaultListModel<String>();
		
		for (Category c : maintainCategory.getCategories()) {
				list.addElement("[" + c.getId() + "] " + c.getName() + " - [" + c.getItemsString() + "]");
		}
		
		JList categoryList = new JList(list);
		categoryList.setLayoutOrientation(JList.VERTICAL);
		categoryList.setBackground(new Color(217, 221, 146));
		categoryList.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		categoryList.setBounds(274, 40, 240, 229);
		contentPane.add(categoryList);
		
		btnBack = new JButton("Close");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnBack.setForeground(Color.BLACK);
		btnBack.setBackground(new Color(221, 96, 49));
		btnBack.setBounds(10, 325, 100, 25);
		contentPane.add(btnBack);
		
		btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String storeName = nameField.getText();
				String storeAddress = addressField.getText();
				int density = Integer.valueOf(densityField.getText());
				String openingHours = openingField.getText();
				String closingHours = closingField.getText();
				String categoriesString = categoryField.getText();
				
				maintainStore.getCurrentStore().setName(storeName);
				maintainStore.getCurrentStore().setAddress(storeAddress);
				maintainStore.getCurrentStore().setDensity(density);
				maintainStore.getCurrentStore().setOpeningHours(openingHours);
				maintainStore.getCurrentStore().setClosingHours(closingHours);
				
				ArrayList<Category> categoriesList = new ArrayList<Category>();
				List<String> categoryIdList = Arrays.asList(categoriesString.split("\\s*,\\s*"));
				
				for (Category c : maintainCategory.getCategories()) {
					if(categoryIdList.contains(String.valueOf(c.getId()))) {
						categoriesList.add(c);
					}
				}
				
				maintainStore.getCurrentStore().setCategories(categoriesList);
				
				try {
					maintainStore.updateCurrentStore();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				btnReset.doClick();
			}
		});
		btnSaveChanges.setForeground(Color.BLACK);
		btnSaveChanges.setBackground(new Color(221, 96, 49));
		btnSaveChanges.setBounds(394, 325, 120, 25);
		contentPane.add(btnSaveChanges);
	}
}
