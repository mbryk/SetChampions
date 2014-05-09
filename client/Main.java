package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.io.*;
import java.net.Socket;
import java.awt.event.*;
import javax.swing.JSeparator;
import javax.swing.border.*;

import java.awt.Toolkit;

public class Main extends JFrame
{
	private static BufferedReader inFromServer;
	private static PrintWriter outToServer;
	private static boolean gameStarted = false;
	private static boolean ingame = false;
	private static Lobby lobby = new Lobby();
	private static JPanel outerFrame;
	private static JPanel buttonPane;
	private static JButton btnSet;
	private static Color defaultColor;
	private static JPanel listPane;
	private JButton btnReturnToLobby;
	private static JLabel gameOver;
	private JButton btnFoundABug;
	public Main(final Board board, final LogIn signIn, final PlayerList playerlist) throws IOException
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage("SableHead.PNG"));
		initUI(board, signIn, playerlist);
	}
	
	private void initUI(final Board board, final LogIn signIn, final PlayerList playerlist) throws IOException
	{
		
		outerFrame = new JPanel();
		outerFrame.add(lobby, "name_4838824622983");
		lobby.setVisible(false);
		listPane = new JPanel();
		listPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(listPane, BorderLayout.EAST);
		listPane.add(playerlist);
		listPane.setVisible(false);
		outerFrame.setLayout(new CardLayout(0, 0));
		getContentPane().add(outerFrame, BorderLayout.CENTER);
		outerFrame.add(board, "name_512405034174");
		JPanel controlPane = new JPanel();
		getContentPane().add(controlPane, BorderLayout.NORTH);
		controlPane.setLayout(new BorderLayout(0, 0));
		
		buttonPane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) buttonPane.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		controlPane.add(buttonPane);
		
		btnSet = new JButton("   Set!   ");
		defaultColor = btnSet.getBackground();
		//defaultBorder = btnSet.getBorder();
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSet.setBackground(defaultColor);
				if(board.getSelected() != null){
					outToServer.println("1");
					outToServer.println(board.getSelected());
				}
			}
		});
		buttonPane.add(btnSet);
		
		JButton shuffleButton = new JButton("   Shuffle   ");
		shuffleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				board.shuffle();
			}
		});
		buttonPane.add(shuffleButton);
		
		btnReturnToLobby = new JButton("Return to Lobby");
		btnReturnToLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				outToServer.println("2");
			}
		});
		buttonPane.add(btnReturnToLobby);
		
		btnFoundABug = new JButton("Found a bug?");
		buttonPane.add(btnFoundABug);
		btnFoundABug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnFoundABug.setText("It's a feature!");
				btnFoundABug.setEnabled(false);
				btnFoundABug.setBackground(Color.GREEN);
			}
		});
		
		gameOver = new JLabel("Game Over ");
		gameOver.setVisible(false);
		controlPane.add(gameOver, BorderLayout.EAST);
		
		setTitle("World Set Championship");
		setSize(502,303);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(new ExitListener()); //TODO test that works
		
		board.add(signIn);
		
	}
	
	public class ExitListener implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {
		}
		@Override
		public void windowClosed(WindowEvent arg0) {
		}
		@Override
		public void windowClosing(WindowEvent arg0) {
			if(ingame){//is in the middle of a game
				outToServer.println("3");
			}
		}
		@Override
		public void windowDeactivated(WindowEvent arg0) {	
		}
		@Override
		public void windowDeiconified(WindowEvent arg0) {	
		}
		@Override
		public void windowIconified(WindowEvent arg0) {	
		}
		@Override
		public void windowOpened(WindowEvent arg0) {
		}
	}
	
	
	public static void main(String[] args) throws IOException, Exception, Exception 
	{
		final LogIn signIn = new LogIn();
		final Board board = new Board();
		final PlayerList playerlist = new PlayerList();
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				Main m = null;
				try {
					m = new Main(board, signIn, playerlist);
				} catch (IOException e) {
					e.printStackTrace();
				}
				m.setVisible(true);
			}
		});
		buttonPane.setVisible(false);
		Socket socket = new Socket("199.98.20.120",3000);
		inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outToServer = new PrintWriter(socket.getOutputStream(), true);
		while(!signIn.attemptSignIn){
			Thread.sleep(5);
		}
		outToServer.println(signIn.username);
		String userName = signIn.username;
		board.remove(signIn);
		lobby.welcome(userName);
		String availableGames = inFromServer.readLine();
		System.out.println("games: " + availableGames);
		if(availableGames.substring(7).length() > 1){
			System.out.println("populating lobby");
			lobby.populate(availableGames.substring(7));
		}
		System.out.println("about to set lobby visible");
		lobby.setVisible(true);
		//TODO
		System.out.println("setting lobby visible");
		outerFrame.validate();
		outerFrame.repaint();
		while(true){//very hackey, but it should work
			while(lobby.gameChosen.length() < 1){
				outToServer.println("refresh");
				lobby.populate(inFromServer.readLine().substring(7));
				Thread.sleep(5);
			}
			lobby.setVisible(false);
			board.setVisible(true);
			buttonPane.setVisible(true);
			listPane.setVisible(true);
			board.init();
			outToServer.println(lobby.gameNumID.get(lobby.gameChosen));
			outToServer.println(lobby.gameChosen);
			gameStarted = true;
			ingame = true;
			board.cardsToPos.clear();
			board.posToCards.clear();
			while(gameStarted){
				String in = inFromServer.readLine();
				if(in.startsWith("No")){
					btnSet.setBackground(Color.RED);
					continue;
				}
				if(in.startsWith("board: ")){
					System.out.println(in);
					System.out.println(in.substring(7));
					board.updateBoard(in.substring(7));
					board.validate();
					board.repaint();
				}
				if(in.startsWith("player: ")){
					playerlist.updatePlayers(in.substring(8));
				}
				if(in.startsWith("lobby: ")){
					gameStarted = false;
					ingame = false;
					//board.removeAll();
					board.setVisible(false);
					listPane.setVisible(false);
					lobby.setVisible(true);
					lobby.gameChosen = "";
					buttonPane.setVisible(false);
					gameOver.setVisible(false);
					break;//Why do I need this???
				}
				if(in.startsWith("over: ")){
					gameOver.setVisible(true);
					Thread.sleep(6000);
				}
			}
		}
	}

}
