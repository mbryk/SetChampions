package src;

import java.net.*;
import java.io.*;

public class PlayerServer extends Thread {
	private Socket socket;
	private Game game;
	private PrintWriter outToPlayer;
	//private ObjectOutputStream outObject;
	private BufferedReader inFromPlayer;
	public int points;
	
	public PlayerServer(Socket socket, Game game) throws IOException{
		this.socket = socket;
		this.game = game;
		points = 0;
		OutputStream outStream = socket.getOutputStream();
		outToPlayer = new PrintWriter(outStream, true);
		//outObject = new ObjectOutputStream(outStream);
        inFromPlayer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void run() {
		System.out.println("Server: Started PlayerServer");
		Board2 board = game.getBoard();
	    sendBoard(board);
	    while(true){
	    	Move move = listenToPlayer(); // Wait for move
	    	System.out.println("Server: Received Move");
		    game.checkMove(this,move);
		    System.out.println("Server: Received Move");
	    }        
    }
	
	private Move listenToPlayer(){
		Move move = new Move();
		try{
			for(int i=0;i<3;i++){
				String c = inFromPlayer.readLine();
				move.cards[i] = Integer.parseInt(c);
			}	
		} catch(IOException e) {
			System.err.println("Error Receiving Move: " + e);
			System.exit(-1);
		}
        return move;
	}
		
	public synchronized void sendBoard(Board2 board){
		outToPlayer.println(board.toString());
		//outObject.writeObject(byteBoard);
	}
	
	public boolean equals(){
		return false;
	}
}
