package frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import classes.Item;
import classes.MaintainStore;
import classes.MaintainUser;
import classes.Store;
import classes.User;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class SignUpFrame extends JFrame {

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
	public SignUpFrame(String email, MaintainUser maintainUser, MaintainStore maintainStore) throws Exception {
		frame = this;
		
		this.maintainUser = maintainUser;
		this.maintainStore = maintainStore;
		
		setBounds(100, 100, 450, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(236, 228, 183));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		emailField = new JTextField(email);
		emailField.setBounds(10, 36, 160, 25);
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 11, 86, 14);
		contentPane.add(lblEmail);
		
		passwordField = new JTextField();
		passwordField.setColumns(10);
		passwordField.setBounds(10, 92, 160, 25);
		contentPane.add(passwordField);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 67, 86, 14);
		contentPane.add(lblPassword);
		
		JLabel lblAvailableStores = new JLabel("Available Stores:");
		lblAvailableStores.setBounds(193, 11, 104, 14);
		contentPane.add(lblAvailableStores);
		
		storeField = new JTextField();
		storeField.setColumns(10);
		storeField.setBounds(10, 148, 35, 25);
		contentPane.add(storeField);
		
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
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(emailField.getText().isEmpty() || passwordField.getText().isEmpty() || storeField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(frame, "Please fill in your email, password and select your store preference");
						
					} else if (!storeIds.contains(Integer.valueOf(storeField.getText()))) {
						JOptionPane.showMessageDialog(frame, "Entered store is not in the system");
						
					} else if (maintainUser.getUserEmails().contains(emailField.getText())) {
						JOptionPane.showMessageDialog(frame, "Entered email is already in the system");
						
					} else {
						createAccount();
						JOptionPane.showMessageDialog(frame, "Your account has been created! Welcome to SmartShoppers");
						
						frame.dispose();
						authorize(maintainUser.getUserByEmail(emailField.getText()));
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnSignUp.setForeground(Color.BLACK);
		btnSignUp.setBackground(new Color(221, 96, 49));
		btnSignUp.setBounds(159, 217, 120, 40);
		contentPane.add(btnSignUp);
		
		
		JList<String> storesList = new JList<String>(list);
		storesList.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		storesList.setBackground(new Color(217, 221, 146));
		storesList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				storeField.setText("" + storesList.getSelectedIndex());
			}
		});
		storesList.setBounds(193, 40, 231, 133);
		contentPane.add(storesList);
		
		JButton btnBack = new JButton("<< Go Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AuthenticationFrame newFrame = new AuthenticationFrame();
					
					BufferedImage myPicture = ImageIO.read(new File("resources/logo.png"));
					newFrame.frame.setIconImage(myPicture);
					
					newFrame.frame.setTitle("Login | SmartShoppers");
					
					frame.dispose();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		btnBack.setHorizontalAlignment(SwingConstants.LEFT);
		btnBack.setBounds(10, 232, 100, 25);
		contentPane.add(btnBack);
	}
	
	private void authorize(User user) throws Exception{
		maintainStore = new MaintainStore("resources/csvs/stores.csv", user.getStoreId());
		StoreFrame newFrame = new StoreFrame(user, maintainUser, maintainStore, new HashMap<Item, JSpinner>());
		System.out.println(user);
		
		BufferedImage myPicture = ImageIO.read(new File("resources/logo.png"));
		newFrame.setIconImage(myPicture);
		newFrame.setTitle("Shop | SmartShoppers");
		
		// centers window
		newFrame.setLocationRelativeTo(null);
		newFrame.setResizable(false);
		newFrame.setVisible(true);
	}
	
	private void createAccount() throws Exception{
		User newUser = new User(maintainUser.generateUserId(), emailField.getText(), passwordField.getText(), "user", Integer.valueOf(storeField.getText()));
		
		maintainUser.add(newUser);
	}
}
