package src;

import java.io.*;
import java.net.*;

import sun.security.util.Password;

public class Start {

	public static void main(String[] args) {
		
		Game game = new Game();
		//int port = Integer.parseInt(args[0]);
		int port = 3000;
		try (ServerSocket serverSocket = new ServerSocket(port)) { 
            while (true) {
                System.out.println("Server: Listening for Players");
                Socket s = serverSocket.accept();
                System.out.println("Server: Received a Player");
                // get id and pass.
                // if he exists, player = findPlayer
//                player.game = r(s,game);
                game.addPlayer(player);
//                player.start();
            }
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
	}	
	
	public static PlayerServer findPlayer(Socket s){
//		PlayerServer player;
//		get id and Password
//		check database
//		if exists {
//			game.findId(id)
//		}
//		else {
//			PlayerServer player = new PlayerServer(s);
//			add player to db
//		}
	}
}
