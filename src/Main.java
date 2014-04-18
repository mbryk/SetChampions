package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main extends JFrame
{

	public Main()
	{
		initUI();
	}
	
	private void initUI()
	{
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton quitButton = new JButton("Quit");
		quitButton.setBounds(50,60,80,30);
		
		quitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});
		
		panel.add(quitButton);
		
		setTitle("TITLE");
		setSize(300,200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main m = new Main();
				m.setVisible(true);
			}
		});
		// TODO Auto-generated method stub
		Board board = new Board();
		System.out.println("****************SETS*****************");
		board.printSets();
		Move m1 = new Move();
		Move m2 = new Move();
		Move m3 = new Move();
		Move m4 = new Move();
		
		m1.cards[0] = new Card(1,1,1,1);
		m1.cards[1] = new Card(0,1,1,2);
		m1.cards[2] = new Card(2,1,1,0);
		m2.cards[0] = new Card(0,1,1,0);
		m2.cards[1] = new Card(0,2,0,2);
		m2.cards[2] = new Card(0,0,2,1);
		m3.cards[0] = new Card(2,2,0,0);
		m3.cards[1] = new Card(2,0,1,0);
		m3.cards[2] = new Card(2,1,2,0);
		m4.cards[0] = new Card(1,0,1,0);
		m4.cards[1] = new Card(1,1,1,1);
		m4.cards[2] = new Card(1,2,1,2);
		
		BoardUtilities bu = board.getBU();
		
		ArrayList<Move> set = board.getSets();
		Move m5 = set.get(0);
		Move m6 = new Move();
		m6.cards[0] = new Card(1,1,1,1);
		m6.cards[1] = new Card(0,1,1,2);
		m6.cards[2] = new Card(2,1,1,0);
		
		// Why is .contains(m5) false when m5 is set.get(0)????????
		// I don't get this..
		System.out.println(bu.getSets().contains(m5));
		System.out.println(m5.equals(bu.getSets().get(0)));
		System.out.println(bu.getSets().get(0).equals(m5));
		System.out.println("****************Check Moves*****************");
		System.out.println("Move1: = " + m1 + " is " + board.checkMove(m1));
		System.out.println("Move2: = " + m2 + " is " + board.checkMove(m2));
		System.out.println("Move3: = " + m3 + " is " + board.checkMove(m3));
		System.out.println("Move4: = " + m4 + " is " + board.checkMove(m4));
		System.out.println("Move5: = " + m5 + " is " + board.checkMove(m5));
		//Print moves and checkMove
	}

}
