package server;

import java.io.*;
import java.net.*;

public class Start {	
	
	public static void main(String[] args) {
		Lobby lobby = new Lobby();
		// int port = Integer.parseInt(args[0]);
		int port = 3000;
		try (ServerSocket serverSocket = new ServerSocket(port)) { 
            while (true) {
                System.out.println("Server: Listening for Players");
                Socket s = serverSocket.accept();
                System.out.println("Server: Received a Player");                             
                new PlayerServer(s,lobby).start();
            }
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
	}	
}
