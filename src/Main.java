package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	}

}
