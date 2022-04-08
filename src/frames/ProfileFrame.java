package frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import classes.Item;
import classes.MaintainStore;
import classes.MaintainUser;
import classes.Store;
import classes.User;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

public class ProfileFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame frame;
	private JTextField emailField;
	private JTextField passwordField;
	private JTextField storeField;
	
	private MaintainUser maintainUser;
	private MaintainStore maintainStore;
	
	/**
	 * Create the frame.
	 */
	public ProfileFrame(User user, MaintainUser maintainUser, MaintainStore maintainStore, Map<Item, JSpinner> itemSpinners) throws Exception{
		
		frame = this;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.maintainUser = maintainUser;
		this.maintainStore = maintainStore;
		
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(236, 228, 183));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 11, 86, 14);
		contentPane.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 67, 86, 14);
		contentPane.add(lblPassword);
		
		JLabel lblAvailableStores = new JLabel("Available Stores:");
		lblAvailableStores.setBounds(193, 11, 104, 14);
		contentPane.add(lblAvailableStores);
		
		JLabel lblStore = new JLabel("Default Store");
		lblStore.setBounds(10, 123, 86, 14);
		contentPane.add(lblStore);
		
		DefaultListModel<String> list = new DefaultListModel<String>();
		
		ArrayList<Integer> storeIds = new ArrayList<Integer>();
		
		String storeListString = "";
		for (Store store : maintainStore.getStores()) {
			storeListString = "";
			storeListString += "<html>[" + store.getId() + "] " + store.getName() + ": <br/>" + store.getAddress() + "</html>";
			list.addElement(storeListString);
			storeIds.add(store.getId());
		}
		
		if(storeListString == "") {
			storeListString += "Empty";
		}
		
		JList<String> storesList = new JList<String>(list);
		storesList.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		storesList.setSelectedIndex(user.getStoreId());
		storesList.setBackground(new Color(217, 221, 146));
		storesList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				storeField.setText("" + storesList.getSelectedIndex());
			}
		});
		storesList.setBounds(193, 40, 231, 209);
		contentPane.add(storesList);
		
		emailField = new JTextField(user.getEmail());
		emailField.setColumns(10);
		emailField.setBounds(10, 36, 160, 25);
		contentPane.add(emailField);
		
		passwordField = new JTextField(user.getPassword());
		passwordField.setColumns(10);
		passwordField.setBounds(10, 92, 160, 25);
		contentPane.add(passwordField);
		
		storeField = new JTextField(String.valueOf(user.getStoreId()));
		storeField.setColumns(10);
		storeField.setBounds(10, 148, 35, 25);
		contentPane.add(storeField);
		
		JButton btnBack = new JButton("Close");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
				try {
					StoreFrame newFrame = new StoreFrame(user, maintainUser, maintainStore, itemSpinners);
					
					BufferedImage myPicture = ImageIO.read(new File("resources/logo.png"));
					newFrame.setIconImage(myPicture);
					newFrame.setTitle("Shop | SmartShoppers");
					
					// centers window
					newFrame.setLocationRelativeTo(null);
					newFrame.setResizable(false);
					newFrame.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBack.setForeground(Color.BLACK);
		btnBack.setBackground(new Color(221, 96, 49));
		btnBack.setBounds(10, 275, 100, 25);
		contentPane.add(btnBack);
		
		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User newUser = new User(user.getId(), emailField.getText(), passwordField.getText(), user.getPermission(), Integer.valueOf(storeField.getText()));
				
				JOptionPane.showMessageDialog(frame, "Your profile changes have been saved");
				
				frame.dispose();
				
				try {
					maintainUser.updateUser(user.getId(), newUser);
					maintainStore.setCurrentStore(newUser.getStoreId());
					StoreFrame newFrame = new StoreFrame(newUser, maintainUser, maintainStore, itemSpinners);
					
					BufferedImage myPicture = ImageIO.read(new File("resources/logo.png"));
					newFrame.setIconImage(myPicture);
					newFrame.setTitle("Shop | SmartShoppers");
					
					// centers window
					newFrame.setLocationRelativeTo(null);
					newFrame.setResizable(false);
					newFrame.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnSaveChanges.setForeground(Color.BLACK);
		btnSaveChanges.setBackground(new Color(221, 96, 49));
		btnSaveChanges.setBounds(159, 260, 120, 40);
		contentPane.add(btnSaveChanges);
		
		JButton btnDeleteAccount = new JButton("Delete");
		btnDeleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(frame, 
		                "You are about to delete your account from the database\n(You will be logged out of your account)", "Delete Confirmation", JOptionPane.OK_CANCEL_OPTION);
				
				if(input == 0) {
					try {
						maintainUser.deleteUser(user.getId());
						
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
			}
		});
		btnDeleteAccount.setBackground(Color.RED);
		btnDeleteAccount.setBounds(324, 275, 100, 25);
		contentPane.add(btnDeleteAccount);
		
		JLabel lblUserRole = new JLabel("User status: " + user.getPermission());
		lblUserRole.setFont(new Font("Dialog", Font.BOLD, 13));
		lblUserRole.setBounds(10, 184, 160, 25);
		contentPane.add(lblUserRole);
	}
}
