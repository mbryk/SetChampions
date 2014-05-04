package client;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Button;

public class LogIn extends JPanel {
	private JTextField textField;
	private JPasswordField passwordField;
	public LogIn() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{81, 182, 100, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel topPanel = new JPanel();
		GridBagConstraints gbc_topPanel = new GridBagConstraints();
		gbc_topPanel.anchor = GridBagConstraints.NORTH;
		gbc_topPanel.insets = new Insets(0, 0, 5, 5);
		gbc_topPanel.gridx = 1;
		gbc_topPanel.gridy = 2;
		add(topPanel, gbc_topPanel);
		
		JLabel lblEnterUsername = new JLabel("Enter Username:");
		topPanel.add(lblEnterUsername);
		
		textField = new JTextField();
		topPanel.add(textField);
		textField.setColumns(10);
		
		JPanel bottomPanel = new JPanel();
		GridBagConstraints gbc_bottomPanel = new GridBagConstraints();
		gbc_bottomPanel.insets = new Insets(0, 0, 5, 5);
		gbc_bottomPanel.gridx = 1;
		gbc_bottomPanel.gridy = 4;
		add(bottomPanel, gbc_bottomPanel);
		
		JLabel lblEnterPassword = new JLabel("Enter Password:");
		bottomPanel.add(lblEnterPassword);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		bottomPanel.add(passwordField);
		
		Button button = new Button("Submit");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 0, 5);
		gbc_button.gridx = 1;
		gbc_button.gridy = 5;
		add(button, gbc_button);
	}

}
