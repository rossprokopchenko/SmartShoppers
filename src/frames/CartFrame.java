package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import classes.Category;
import classes.Item;
import classes.MaintainStore;
import classes.MaintainUser;
import classes.Store;
import classes.User;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.swing.JList;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class CartFrame extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	private JSpinner itemSpinner;
	private JButton itemRemove;
	
	private JLabel lblTotalPrice;
	private Map<Item, JSpinner> itemSpinners;
	private DefaultListModel<String> list;
	
	/**
	 * Create the frame.
	 */
	public CartFrame(User user, MaintainUser maintainUser, MaintainStore maintainStore, Map<Item, JSpinner> itemSpinners) {
		frame = this;
		this.itemSpinners = itemSpinners;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(236, 228, 183));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Store store = maintainStore.getCurrentStore();
		
		JLabel lblSummary = new JLabel("Cart summary:");
		lblSummary.setBounds(10, 36, 150, 20);
		contentPane.add(lblSummary);
		
		JLabel lblStore = new JLabel(store.getName() + " - " + store.getAddress());
		lblStore.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblStore.setHorizontalAlignment(SwingConstants.CENTER);
		lblStore.setBounds(10, 11, 414, 14);
		contentPane.add(lblStore);
		
		list = new DefaultListModel<String>();
		double totalPrice = 0;
		
		String itemListString = "";
		for (Item item : itemSpinners.keySet()) {
			if((int) itemSpinners.get(item).getValue() > 0) {
				double itemPrice = item.getPrice() * (int) itemSpinners.get(item).getValue();
				
				list.addElement("<html>" + item.getName() + " [x" + itemSpinners.get(item).getValue() + 
						"] - $" + df.format(itemPrice) + "<br/>($" + df.format(item.getPrice()) + " per item)</html>");
				totalPrice += itemPrice;
			}
		}
		
		if(itemListString == "") {
			itemListString += "Empty";
		}
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBounds(10, 67, 334, 180);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		
		JList itemList = new JList(list);
		itemList.setBackground(new Color(217, 221, 146));
		itemList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				Rectangle r = panel.getBounds();
				if(itemList.getSelectedValue() == null) {
					return;
				}
				
				String str = "" + itemList.getSelectedValue();
				String val = "";
				for(String s : str.split(" ")) {
					if(s.contains("[")) break;
					if(s.contains("<html>")) s = s.substring(6);
					val += s;
					val += " ";
				} val = val.substring(0, val.length()-1);
				
				Item item = maintainStore.getItemByName(val);
				
				int offset = itemList.getSelectedIndex();
				if (offset > 4) offset = 4;
				
				if (itemSpinner != null) contentPane.remove(itemSpinner);
				itemSpinner = new JSpinner();
				itemSpinner.setBounds(r.x + r.width + 5, r.y + 39 * offset, 35, 25);
				itemSpinner.setModel(new SpinnerNumberModel((int) itemSpinners.get(item).getValue(), 1, item.getInventory(), 1));
				itemSpinner.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						JSpinner spinner = itemSpinners.get(item);
						spinner.setValue(itemSpinner.getValue());
						itemSpinners.put(item, spinner);
						updateTotalPrice();
						
						double itemPrice = item.getPrice() * (int) itemSpinners.get(item).getValue();
						String str = "<html>" + item.getName() + " [x" + itemSpinners.get(item).getValue() + 
								"] - $" + df.format(itemPrice) + "<br/>($" + df.format(item.getPrice()) + " per item)</html>";
						list.setElementAt(str, itemList.getSelectedIndex());
						itemList.repaint();
						itemList.revalidate();
					}
				});
				contentPane.add(itemSpinner);
				
				if (itemRemove != null) contentPane.remove(itemRemove);
				itemRemove = new JButton("");
				itemRemove.setIcon(new ImageIcon("resources/icons/x.png"));
				itemRemove.setFont(new Font("Tahoma", Font.PLAIN, 9));
				itemRemove.setBackground(Color.white);
				itemRemove.setBounds(r.x + r.width + 50, r.y + 39 * offset, 30, 25);
				itemRemove.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JSpinner spinner = itemSpinners.get(item);
						spinner.setValue(0);
						itemSpinners.put(item, spinner);
						contentPane.remove(itemRemove);
						contentPane.remove(itemSpinner);
						list.removeElementAt(itemList.getSelectedIndex());
						updateTotalPrice();
						
						contentPane.repaint();
						contentPane.revalidate();
					}
				});
				contentPane.add(itemRemove);
				
				contentPane.repaint();
				contentPane.revalidate();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setViewportView(itemList);
	    itemList.setLayoutOrientation(JList.VERTICAL);
	    panel.add(scrollPane);
	    contentPane.add(panel);
		
		JButton btnBack = new JButton("Close");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		btnBack.setForeground(Color.BLACK);
		btnBack.setBackground(new Color(221, 96, 49));
		btnBack.setBounds(10, 327, 100, 23);
		contentPane.add(btnBack);
		
		JButton btnGenerate = new JButton("<html><div style=\"text-align: center\">Generate Shopping List</div></html>");
		btnGenerate.setBounds(171, 300, 100, 50);
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
				OptimalFrame newFrame = new OptimalFrame();
				
				try {
					BufferedImage myPicture = ImageIO.read(new File("resources/logo.png"));
					newFrame.setIconImage(myPicture);
					newFrame.setTitle("Optimal Order | SmartShoppers");
					
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
		contentPane.add(btnGenerate);
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotal.setBounds(334, 329, 50, 18);
		contentPane.add(lblTotal);
		
		lblTotalPrice = new JLabel("$" + df.format(totalPrice));
		lblTotalPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalPrice.setBounds(374, 329, 50, 18);
		contentPane.add(lblTotalPrice);
		
		JLabel lblDensity = new JLabel("Density: " + store.getDensity());
		lblDensity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDensity.setBounds(334, 39, 90, 14);
		contentPane.add(lblDensity);
		
		JLabel lblClearAll = new JLabel("Clear All");
		lblClearAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				for(Item item : itemSpinners.keySet()) {
					JSpinner spinner = itemSpinners.get(item);
					spinner.setValue(0);
					itemSpinners.put(item, spinner);
				}
				
				if(itemRemove != null && itemSpinner != null) {
					contentPane.remove(itemRemove);
					contentPane.remove(itemSpinner);
				}
				
				list.removeAllElements();
				
				updateTotalPrice();
				
				contentPane.repaint();
				contentPane.revalidate();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
				lblClearAll.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				lblClearAll.setFont(new Font("Tahoma", Font.ITALIC, 11));
			}
		});
		lblClearAll.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblClearAll.setBounds(10, 248, 80, 14);
		contentPane.add(lblClearAll);
	}
	
	private void updateTotalPrice() {
		double totalPrice = 0;
		
		for (Item item : itemSpinners.keySet()) {
			if((int) itemSpinners.get(item).getValue() > 0) {
				double itemPrice = item.getPrice() * (int) itemSpinners.get(item).getValue();
				
				totalPrice += itemPrice;
			}
		}
		
		this.lblTotalPrice.setText("$" + df.format(totalPrice));
	}
}
