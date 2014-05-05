package client;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Lobby extends JPanel{
	private JTextField txtTypeTheName;
	public Lobby() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{316, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 215, 21, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel welcomeLabel = new JLabel("New label");
		GridBagConstraints gbc_welcomeLabel = new GridBagConstraints();
		gbc_welcomeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_welcomeLabel.gridx = 0;
		gbc_welcomeLabel.gridy = 0;
		add(welcomeLabel, gbc_welcomeLabel);
		
		JButton btnEnterGame = new JButton("  Enter game  ");
		GridBagConstraints gbc_btnEnterGame = new GridBagConstraints();
		gbc_btnEnterGame.anchor = GridBagConstraints.WEST;
		gbc_btnEnterGame.insets = new Insets(0, 0, 5, 0);
		gbc_btnEnterGame.gridx = 2;
		gbc_btnEnterGame.gridy = 0;
		add(btnEnterGame, gbc_btnEnterGame);
		
		JList<String> list = new JList<String>();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 3;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		add(list, gbc_list);
		
		JLabel lblNewLabel = new JLabel("or create your own game:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel.add(lblNewLabel_1, BorderLayout.WEST);
		
		txtTypeTheName = new JTextField();
		panel.add(txtTypeTheName, BorderLayout.CENTER);
		txtTypeTheName.setColumns(10);
		
		JButton newGameButton = new JButton(" Create game ");
		panel.add(newGameButton, BorderLayout.EAST);
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
	}
	
}
