package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class Main extends JFrame
{

	public Main() throws IOException
	{
		initUI();
	}
	
	private void initUI() throws IOException
	{
		
		JPanel outerFrame = new JPanel();
		getContentPane().add(outerFrame, BorderLayout.CENTER);
		outerFrame.setLayout(new CardLayout(0, 0));
		final Board board = new Board();
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
				System.out.println(board.getSelected());
				//TODO//Call board.getSet and send to Mark
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
		
		JPanel displayPane = new JPanel();
		controlPane.add(displayPane, BorderLayout.EAST);
		
		JLabel lblScore = new JLabel("Score:");
		displayPane.add(lblScore);
		
		JSeparator separator = new JSeparator();
		displayPane.add(separator);
		
		JLabel lblPlaceYoutotal = new JLabel("Place: you/total");
		displayPane.add(lblPlaceYoutotal);
		//panel.setLayout(null);
		
		JButton quitButton = new JButton("Quit");
		quitButton.setBounds(50,60,80,30);
		
		quitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});
		
		//panel.add(quitButton);
		
		setTitle("World Set Championship");
		setSize(493,297);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	
	public static void main(String[] args) throws IOException 
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main m = null;
				try {
					m = new Main();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				m.setVisible(true);
			}
		});
		// TODO Auto-generated method stub
		Board board = new Board();
		System.out.println("****************SETS*****************");
		board.printSets();
	}

}
