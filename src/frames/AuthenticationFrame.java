package frames;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import classes.Item;
import classes.MaintainStore;
import classes.MaintainUser;
import classes.User;

public class AuthenticationFrame implements KeyListener{

	public JFrame frame;
	private JTextField emailField;
	private JTextField passwordField;
	
	private MaintainUser maintainUser;
	private MaintainStore maintainStore;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthenticationFrame window = new AuthenticationFrame();
					BufferedImage myPicture = ImageIO.read(new File("resources/logo.png"));
					window.frame.setTitle("Login | SmartShoppers");
					window.frame.setIconImage(myPicture);
					// centers window
				    window.frame.setLocationRelativeTo(null);
				    window.frame.setResizable(false);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public AuthenticationFrame() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws Exception {
		maintainUser = new MaintainUser("resources/csvs/users.csv");
		maintainStore = new MaintainStore("resources/csvs/stores.csv", 0);
		
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 500));
        frame.getContentPane().setBackground(new Color(236, 228, 183));

        // Display the frame and text field.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);
        
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblEmail.setBounds(212, 144, 160, 14);
        frame.getContentPane().add(lblEmail);
        
        emailField = new JTextField();
        emailField.setBounds(212, 169, 160, 25);
        frame.getContentPane().add(emailField);
        emailField.setColumns(10);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblPassword.setBounds(212, 205, 160, 14);
        frame.getContentPane().add(lblPassword);
        
        passwordField = new JPasswordField();
        passwordField.setColumns(10);
        passwordField.setBounds(212, 230, 160, 25);
        frame.getContentPane().add(passwordField);
        
        
        JButton btnSignIn = new JButton("Sign In");
        btnSignIn.setSelectedIcon(null);
        
        btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					checkUser();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        });
        
        btnSignIn.setForeground(Color.BLACK);
        btnSignIn.setBackground(new Color(221, 96, 49));
        btnSignIn.setBounds(232, 285, 120, 40);
        frame.getContentPane().add(btnSignIn);
        
        JLabel lblCompanyTag = new JLabel("ShoppersLand Inc.");
        lblCompanyTag.setForeground(new Color(49, 30, 16));
        lblCompanyTag.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblCompanyTag.setHorizontalAlignment(SwingConstants.CENTER);
        lblCompanyTag.setBounds(162, 26, 260, 50);
        frame.getContentPane().add(lblCompanyTag);
        
        JButton btnSignUp = new JButton("Sign Up");
        
        btnSignUp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					signUp();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        
        btnSignUp.setForeground(Color.BLACK);
        btnSignUp.setBackground(Color.WHITE);
        btnSignUp.setBounds(242, 336, 100, 25);
        frame.getContentPane().add(btnSignUp);
        
        emailField.addKeyListener(this);
        passwordField.addKeyListener(this);
        btnSignIn.addKeyListener(this);
        
        frame.setVisible(true);
	}
	
	private void checkUser() throws Exception{
		if(maintainUser.userExists(emailField.getText(), passwordField.getText())) {
			frame.dispose();
			authorize(maintainUser.getUserByEmail(emailField.getText()));
		} else {
			JOptionPane.showMessageDialog(frame, "Incorrect email or password");
		}
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
	
	private void signUp() throws Exception{
		SignUpFrame newFrame = new SignUpFrame(emailField.getText(), maintainUser, maintainStore);
		
		BufferedImage myPicture = ImageIO.read(new File("resources/logo.png"));
		newFrame.setIconImage(myPicture);
		newFrame.setTitle("Sign Up | SmartShoppers");
		
		// centers window
		newFrame.setLocationRelativeTo(null);
		newFrame.setVisible(true);
		newFrame.setResizable(false);
		frame.dispose();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				checkUser();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
