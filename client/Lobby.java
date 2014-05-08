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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Dimension;

public class Lobby extends JPanel{
	public HashMap<String, String> gameNumID = new HashMap<String, String>();
	private JTextField txtTypeTheName;
	private JList<String> list;
	String gameChosen = "";
	int numberOfGames = 0;
	JLabel welcomeLabel;
	private String input = "!";
	public Lobby() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{316, 15, 0, 2, 0};
		gridBagLayout.rowHeights = new int[]{0, 154, 27, 30, 5, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		welcomeLabel = new JLabel("New label");
		GridBagConstraints gbc_welcomeLabel = new GridBagConstraints();
		gbc_welcomeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_welcomeLabel.gridx = 0;
		gbc_welcomeLabel.gridy = 0;
		add(welcomeLabel, gbc_welcomeLabel);
		
		JButton btnEnterGame = new JButton("  Enter game  ");
		btnEnterGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getMinSelectionIndex() > -1){
					gameChosen = list.getSelectedValue().substring(0, list.getSelectedValue().indexOf("\t"));
					//gameChosen = String.valueOf(list.getMinSelectionIndex());
				}
			}
		});
		GridBagConstraints gbc_btnEnterGame = new GridBagConstraints();
		gbc_btnEnterGame.anchor = GridBagConstraints.WEST;
		gbc_btnEnterGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnEnterGame.gridx = 2;
		gbc_btnEnterGame.gridy = 0;
		add(btnEnterGame, gbc_btnEnterGame);
		
		list = new JList<String>();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 3;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		add(list, gbc_list);
		
		JLabel lblNewLabel = new JLabel("                                or create your own game:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.SOUTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(50, 10));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridwidth = 3;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Enter name of game: ");
		panel.add(lblNewLabel_1, BorderLayout.WEST);
		
		txtTypeTheName = new JTextField();
		panel.add(txtTypeTheName, BorderLayout.CENTER);
		txtTypeTheName.setColumns(10);
		txtTypeTheName.addKeyListener(new EnterListener());
		
		JButton newGameButton = new JButton(" Create game ");
		panel.add(newGameButton, BorderLayout.EAST);
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submit();
			}
		});
	}
	/**
	 * List available games in the lobby
	 * @param availableGames list of games
	 */
	public void populate(String games) {
		//TODO
		System.out.println("lobby: games = " + games);
		if(!games.equals(input)){
			System.out.println("repopulating");
			System.out.println(games);
			System.out.println(input);
			input = games;
			int pos = 0;
			int i;
			gameNumID.clear();
			
			ArrayList<String> listData = new ArrayList<String>();
			for(numberOfGames = 0; pos < games.length(); ++numberOfGames){
				String gameID;
				String gameName;
				String cardsLeft;
				String playerName;
				for(i = pos; games.charAt(i) != ';'; ++i){
				}
				gameID = games.substring(pos, i);
				for(pos = ++i; games.charAt(i) != ';'; ++i){
				}
				gameName = games.substring(pos, i);
				gameNumID.put(gameName, gameID);
				for(pos = ++i; games.charAt(i) != ';'; ++i){
				}
				cardsLeft = games.substring(pos, i);
				for(pos = ++i; games.charAt(i) != ';' || games.charAt(i+1) != ';' || (i+2 < games.length() && games.charAt(i+2) != ';'); ++i){
				}
				playerName = games.substring(pos, i);
				pos = i + 3;
				listData.add(gameName + "\t " + cardsLeft + " cards left " + "      " + parseName(playerName));
			}
			String [] myData = new String[listData.size()];
			listData.toArray(myData);
			list.setListData(myData);
		}
	}
	/***
	 * Make the string look nice - separate points from other players
	 */
	private String parseName(String namepair){
		String parsed = "";
		Boolean odd = true;
		for(int i=0; i < namepair.length(); ++i){
			if(namepair.charAt(i) != ';'){
				parsed += String.valueOf(namepair.charAt(i));
			}else{
				if(odd){
					parsed += " - ";
					odd = false;
				}else{
					parsed += " pts, ";
					odd = true;
				}
			}
		}
		parsed +=" pts";
		return parsed;
	}
	public class EnterListener implements KeyListener{
		@Override
		public void keyReleased(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
				submit();
			}
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
		}
		@Override
		public void keyPressed(KeyEvent arg0) {
		}
	}
	private void submit(){
		if(txtTypeTheName.getText().length() > 0){
			gameChosen = txtTypeTheName.getText();
			gameNumID.put(gameChosen, String.valueOf(numberOfGames));
		}
	}
	public void welcome(String name){
		welcomeLabel.setText("Welcome, " + name);
	}
}
