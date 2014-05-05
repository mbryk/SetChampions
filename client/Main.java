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
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.io.*;
import java.net.Socket;
import java.awt.event.*;
import javax.swing.JSeparator;
import java.awt.Toolkit;

public class Main extends JFrame
{
	private static BufferedReader inFromServer;
	private static PrintWriter outToServer;
	private static boolean gameStarted = false;
	private static boolean ingame = false;
	
	public Main(final Board board, final LogIn signIn) throws IOException
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage("SableHead.PNG"));
		initUI(board, signIn);
	}
	
	private void initUI(final Board board, final LogIn signIn) throws IOException
	{
		
		JPanel outerFrame = new JPanel();
		getContentPane().add(outerFrame, BorderLayout.CENTER);
		outerFrame.setLayout(new CardLayout(0, 0));
		outerFrame.add(board, "name_512405034174");
		JPanel controlPane = new JPanel();
		getContentPane().add(controlPane, BorderLayout.NORTH);
		controlPane.setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) buttonPane.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		controlPane.add(buttonPane);
		
		JButton btnSet = new JButton("Set!");
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(board.getSelected() != null){
					outToServer.println("1");
					outToServer.println(board.getSelected());
				}
			}
		});
		buttonPane.add(btnSet);
		
		JButton shuffleButton = new JButton("Shuffle");
		shuffleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				board.shuffle();
			}
		});
		buttonPane.add(shuffleButton);
		
		JButton quitButton = new JButton("Exit Room");
		buttonPane.add(quitButton);
		quitButton.setBounds(50,60,80,30);
		
		quitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				outToServer.println("2");
			}
		});
		
		JPanel displayPane = new JPanel();
		controlPane.add(displayPane, BorderLayout.EAST);
		
		JLabel lblScore = new JLabel("Score:");
		displayPane.add(lblScore);
		
		JSeparator separator = new JSeparator();
		displayPane.add(separator);
		
		JLabel lblPlaceYoutotal = new JLabel("Place: you/total");
		displayPane.add(lblPlaceYoutotal);
		
		setTitle("World Set Championship");
		setSize(493,297);
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
		final Lobby lobby = new Lobby();
		int gameNum;
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				Main m = null;
				try {
					m = new Main(board, signIn);
				} catch (IOException e) {
					e.printStackTrace();
				}
				m.setVisible(true);
			}
		});
		Socket socket = new Socket("localhost",3000);
		inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outToServer = new PrintWriter(socket.getOutputStream(), true);
		while(!signIn.attemptSignIn){
			Thread.sleep(5);
		}
		outToServer.println(signIn.username);
		board.remove(signIn);
		board.add(lobby);//TODO ALL WRONG
		String availableGames = inFromServer.readLine();
		while(!ingame){
			System.out.println("waiting");
		}
		board.remove(lobby);
		board.init();
		//TODO remove
		outToServer.println("0");
		gameStarted = true;
		while(gameStarted){
			String newBoard = inFromServer.readLine();
			if(newBoard.length()<3){//is "No" or "Hi" or something
				//TODO change this to get rid of bad set
				continue;
			}
			String newPlayers = inFromServer.readLine();
			System.out.println("server: "+newPlayers);
			board.updateBoard(newBoard);
			board.updatePlayers(newPlayers);
		}
	}

}
