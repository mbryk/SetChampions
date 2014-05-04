package client;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.border.EtchedBorder;

public class LogIn extends JPanel {
	String username = null;
	String password = null;
	Boolean attemptSignIn = false;
	private JTextField textField;
	private JPasswordField passwordField;
	Button button;
	public LogIn() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{25, 81, 100, 25, 0};
		gridBagLayout.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		

		
		JLabel lblEnterUsername = new JLabel("Enter Username:");
		GridBagConstraints gbc_lblEnterUsername = new GridBagConstraints();
		gbc_lblEnterUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterUsername.gridx = 1;
		gbc_lblEnterUsername.gridy = 1;
		add(lblEnterUsername, gbc_lblEnterUsername);
		
		textField = new JTextField();
		textField.addKeyListener(new EnterListener());
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblEnterPassword = new JLabel("Enter Password:");
		GridBagConstraints gbc_lblEnterPassword = new GridBagConstraints();
		gbc_lblEnterPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterPassword.gridx = 1;
		gbc_lblEnterPassword.gridy = 3;
		add(lblEnterPassword, gbc_lblEnterPassword);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
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
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 2;
		gbc_button.gridy = 4;
		add(button, gbc_button);
	}
	private void submit(){
		if(textField.getText().length() > 0 && passwordField.getPassword().length > 0){ //instead of testing if it equals null, because of newline characters
			username = textField.getText();
			password = String.valueOf(passwordField.getPassword());
			
			boolean register = true;    //EDIT THIS
			
			if (register)
				attemptSignIn = LoginDAO.register(username, password);
			else
				attemptSignIn = LoginDAO.login(username, password);
			
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
