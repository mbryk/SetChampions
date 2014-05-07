package client;

import java.util.ArrayList;

import javax.swing.JList;

public class PlayerList extends JList<String> {
	public PlayerList(){
		
	}
	public void updatePlayers(String players){
		ArrayList<String> list = parseList(players);
		String[] users = new String [list.size()];
		list.toArray(users);
		this.setListData(users);
		//System.out.println("players: "+players);
	}
	private ArrayList<String> parseList(String namepair){
		String parsed = "";
		ArrayList<String> parsedList = new ArrayList<String>();
		Boolean odd = true;
		for(int i=0; i < namepair.length(); ++i){
			if(namepair.charAt(i) != ';'){
				parsed += String.valueOf(namepair.charAt(i));
			}else{
				if(odd){
					parsed += " - ";
					odd = false;
				}else{
					parsed += " pts";
					odd = true;
					parsedList.add(parsed);
					parsed = "";
				}
			}
		}
		parsed +=" pts";
		return parsedList;
	}
}

