package src;

import java.net.*;
import java.io.*;

public class PlayerServer extends Thread {
	private Socket socket;
	private Game game;
	private PrintWriter outWrite;
	private ObjectOutputStream outObject;
	private BufferedReader in;
	
	public PlayerServer(Socket socket, Game game) throws IOException{
		this.socket = socket;
		this.game = game;
		OutputStream outStream = socket.getOutputStream();
		outWrite = new PrintWriter(outStream, true);
		outObject = new ObjectOutputStream(outStream);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void run() {
	    sendBoard();
	    while(true){
	    	Move move = listenToPlayer();
		    game.checkMove(this,move);
	    }        
    }
	
	private Move listenToPlayer(){
		Move move = new Move();
		try{
			for(int i=0;i<3;i++){
				String c = in.readLine();
				move.cards[i] = Integer.parseInt(c);
			}	
		} catch(IOException e) {
			System.out.println("Error Receiving Move");
			System.exit(-1);
		}
        return move;
	}
		
	private void sendBoard(){
		Board2 board = game.getBoard();
		outWrite.write(board.toString());
		//outObject.writeObject(byteBoard);
	}
}
