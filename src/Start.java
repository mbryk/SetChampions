package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Start {

	public static void main(String[] args) {
		
		Game game = new Game();
		int port = Integer.parseInt(args[0]);
		try (ServerSocket serverSocket = new ServerSocket(port)) { 
            while (true) {
                System.out.println("Listening for Players");
                Socket s = serverSocket.accept();
                Player player = new Player(s,game);
                game.addPlayer(player);
                player.start();
            }
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
	}	
}
