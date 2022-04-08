package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OptimalFrame extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	
	/**
	 * Create the frame.
	 */
	public OptimalFrame() {
		frame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(236, 228, 183));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblOptimal = new JLabel("Here is your optimal shopping order");
		lblOptimal.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOptimal.setHorizontalAlignment(SwingConstants.CENTER);
		lblOptimal.setBounds(10, 11, 514, 35);
		contentPane.add(lblOptimal);
		
		JLabel lblOptimalImage = new JLabel(new ImageIcon("resources/optimalPath.jpg"));
		lblOptimalImage.setBounds(10, 55, 514, 318);
		contentPane.add(lblOptimalImage);
		
		JLabel lblThankYou = new JLabel("Thank you for shopping with SmartShoppers");
		lblThankYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblThankYou.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblThankYou.setBounds(10, 384, 514, 35);
		contentPane.add(lblThankYou);
		
		JButton btnBack = new JButton("Close");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnBack.setForeground(Color.BLACK);
		btnBack.setBackground(new Color(221, 96, 49));
		btnBack.setBounds(214, 475, 100, 25);
		btnBack.setFocusable(false);
		contentPane.add(btnBack);
	}

}
