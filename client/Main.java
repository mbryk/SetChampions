package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import java.util.ArrayList;

import javax.swing.JSeparator;
import java.awt.Toolkit;

public class Main extends JFrame
{
	private static BufferedReader inFromServer;
	private static PrintWriter outToServer;
	private static boolean gameStarted = false;
	
	public Main(final Board board) throws IOException
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage("SableHead.PNG"));
		initUI(board);
	}
	
	private void initUI(final Board board) throws IOException
	{
		
		JPanel outerFrame = new JPanel();
		getContentPane().add(outerFrame, BorderLayout.CENTER);
		outerFrame.setLayout(new CardLayout(0, 0));
		outerFrame.add(board, "name_512405034174");
		board.init();
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
				System.exit(0); //TODO exit to lobby
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
		
		
		
	}
	
	
	
	
	public static void main(String[] args) throws IOException, Exception, Exception 
	{
		final Board board = new Board();
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				Main m = null;
				try {
					m = new Main(board);
				} catch (IOException e) {
					e.printStackTrace();
				}
				m.setVisible(true);
			}
		});

		Socket socket = new Socket("localhost",3000);
		inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outToServer = new PrintWriter(socket.getOutputStream(), true);
		outToServer.println("Caleb"); //TODO
		gameStarted = true;
		//TODO change this?
		while(gameStarted){
			String newBoard = inFromServer.readLine();
			System.out.println("server: "+newBoard);
			if(newBoard.length()<3){//is "No" or "Hi" or something
				//TODO maybe change this
				continue;
			}
			String newPlayers = inFromServer.readLine();
			System.out.println("server: "+newPlayers);
			board.updateBoard(newBoard);
			board.updatePlayers(newPlayers);
		}
	}

}
