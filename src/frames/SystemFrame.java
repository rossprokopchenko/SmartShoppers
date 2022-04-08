package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import classes.Category;
import classes.MaintainUser;
import classes.User;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SystemFrame extends JFrame {

	private JFrame frame;
	private JPanel contentPane;
	private JTextField idField;
	private JTextField emailField;
	private JTextField passwordField;
	private JTextField permissionField;
	private JTextField storeField;

	/**
	 * Create the frame.
	 */
	public SystemFrame(MaintainUser maintainUser) {
		frame = this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 430, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(236, 228, 183));
		setContentPane(contentPane);
		
		DefaultListModel<String> list = new DefaultListModel<String>();
		
		for (User u : maintainUser.getUsers()) {
				list.addElement("" + u.getId() + " - " + u.getEmail() + " - " + u.getPermission() + "");
		}
		contentPane.setLayout(null);
		
		JList userList = new JList(list);
		userList.setLayoutOrientation(JList.VERTICAL);
		userList.setBackground(new Color(217, 221, 146));
		userList.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		userList.setBounds(160, 50, 240, 244);
		userList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String userEmail = ((String) userList.getSelectedValue()).split(" ")[2];
				
				User user = maintainUser.getUserByEmail(userEmail);
				
				idField.setText("" + user.getId());
				emailField.setText(user.getEmail());
				passwordField.setText(user.getPassword());
				permissionField.setText(user.getPermission());
				storeField.setText("" + user.getStoreId());
			}
		});
		
		
		contentPane.add(userList);
		
		JLabel lblUserList = new JLabel("Select a User");
		lblUserList.setBounds(160, 25, 240, 14);
		contentPane.add(lblUserList);
		
		JLabel lblUserId = new JLabel("User Id");
		lblUserId.setBounds(10, 25, 100, 14);
		contentPane.add(lblUserId);
		
		idField = new JTextField();
		idField.setBounds(10, 50, 122, 20);
		contentPane.add(idField);
		idField.setColumns(10);
		
		JLabel lblUserEmail = new JLabel("Email");
		lblUserEmail.setBounds(10, 81, 100, 14);
		contentPane.add(lblUserEmail);
		
		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(10, 106, 122, 20);
		contentPane.add(emailField);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 137, 116, 14);
		contentPane.add(lblPassword);
		
		passwordField = new JTextField();
		passwordField.setColumns(10);
		passwordField.setBounds(10, 162, 122, 20);
		contentPane.add(passwordField);
		
		JLabel lblPermission = new JLabel("Permission");
		lblPermission.setBounds(10, 193, 116, 14);
		contentPane.add(lblPermission);
		
		permissionField = new JTextField();
		permissionField.setColumns(10);
		permissionField.setBounds(10, 218, 122, 20);
		contentPane.add(permissionField);
		
		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.setForeground(Color.BLACK);
		btnSaveChanges.setBackground(new Color(221, 96, 49));
		btnSaveChanges.setBounds(280, 345, 120, 25);
		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.valueOf(idField.getText());
				String email = emailField.getText();
				String password = passwordField.getText();
				String permission = permissionField.getText();
				int storeId = Integer.valueOf(storeField.getText());
				
				User newUser = new User(id, email, password, permission, storeId);
				
				try {
					maintainUser.updateUser(id, newUser);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnSaveChanges);
		
		JButton btnBack = new JButton("Close");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnBack.setForeground(Color.BLACK);
		btnBack.setBackground(new Color(221, 96, 49));
		btnBack.setBounds(10, 344, 100, 25);
		contentPane.add(btnBack);
		
		JLabel lblStoreId = new JLabel("Default Store");
		lblStoreId.setBounds(10, 249, 116, 14);
		contentPane.add(lblStoreId);
		
		storeField = new JTextField();
		storeField.setColumns(10);
		storeField.setBounds(10, 274, 122, 20);
		contentPane.add(storeField);
	}
}
