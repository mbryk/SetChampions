package client;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.border.EtchedBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;

public class LogIn extends JPanel {
	String username = null;
	String password = null;
	Boolean attemptSignIn = false;
	private JTextField textField;
	private JPasswordField passwordField;
	Button button;
	private JPanel panel;
	private JCheckBox registerBox;
	private JLabel errorLabel;
	public LogIn() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{57, 78, 239, 24, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 0, 51, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		

		
		JLabel lblEnterUsername = new JLabel("Enter Username:");
		GridBagConstraints gbc_lblEnterUsername = new GridBagConstraints();
		gbc_lblEnterUsername.anchor = GridBagConstraints.EAST;
		gbc_lblEnterUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterUsername.gridx = 1;
		gbc_lblEnterUsername.gridy = 1;
		add(lblEnterUsername, gbc_lblEnterUsername);
		
		textField = new JTextField();
		textField.addKeyListener(new EnterListener());
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblEnterPassword = new JLabel("Enter Password:");
		GridBagConstraints gbc_lblEnterPassword = new GridBagConstraints();
		gbc_lblEnterPassword.anchor = GridBagConstraints.EAST;
		gbc_lblEnterPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterPassword.gridx = 1;
		gbc_lblEnterPassword.gridy = 3;
		add(lblEnterPassword, gbc_lblEnterPassword);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.anchor = GridBagConstraints.WEST;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 3;
		add(passwordField, gbc_passwordField);
		passwordField.setColumns(10);
		passwordField.addKeyListener(new EnterListener());
		
		button = new Button("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				submit();
			}
		});
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 4;
		add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		registerBox = new JCheckBox("New user");
		panel.add(registerBox);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.anchor = GridBagConstraints.WEST;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 2;
		gbc_button.gridy = 4;
		add(button, gbc_button);
		
		errorLabel = new JLabel("New label");
		GridBagConstraints gbc_errorLabel = new GridBagConstraints();
		gbc_errorLabel.insets = new Insets(0, 0, 0, 5);
		gbc_errorLabel.gridx = 2;
		gbc_errorLabel.gridy = 5;
		add(errorLabel, gbc_errorLabel);
		errorLabel.setVisible(false);
	}
	private void submit(){
		if(textField.getText().length() > 0 && passwordField.getPassword().length > 0){ //instead of testing if it equals null, because of newline characters
			username = textField.getText();
			password = String.valueOf(passwordField.getPassword());
			
			boolean register = registerBox.isSelected();
			
			if (register){
				attemptSignIn = LoginDAO.register(username, password);
				if(!attemptSignIn){
					errorLabel.setForeground(Color.RED);
					errorLabel.setText("That username already exists");
					errorLabel.setVisible(true);
				}
			}
			else{
				attemptSignIn = LoginDAO.login(username, password);
				if(!attemptSignIn){
					errorLabel.setForeground(Color.RED);
					errorLabel.setText("That username or password is incorrect");
					errorLabel.setVisible(true);
				}
			}
		}else{
			attemptSignIn = false;
		}
	}
	public class EnterListener implements KeyListener{
		@Override
		public void keyPressed(KeyEvent arg0) {	
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
				submit();
			}
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	}

}
