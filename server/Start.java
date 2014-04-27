package server;

import java.io.*;
import java.net.*;

import sun.security.util.Password;

public class Start {
	public static Game game;
	
	public static void main(String[] args) {
		
		game = new Game();
		
		// int port = Integer.parseInt(args[0]);
		int port = 3000;
		try (ServerSocket serverSocket = new ServerSocket(port)) { 
            while (true) {
                System.out.println("Server: Listening for Players");
                Socket s = serverSocket.accept();
                System.out.println("Server: Received a Player");
                
//              get username and pass. the point is that if he was part of this game, he can come pack in
                
                new PlayerServer(s,game).start();
                
            }
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
	}	
}
