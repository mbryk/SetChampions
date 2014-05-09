package client;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;


public class CardGUI extends JPanel implements ComponentListener
{
	Border selected  = new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new LineBorder(new Color(0, 0, 255),2));
	Border notSelected  = new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
	Border newCard = new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new LineBorder(Color.yellow,2));
	JLabel cardPic;
	BufferedImage cardPNG;
	int shape;
	int number;
	int color;
	int filled;
	public CardGUI(int ishape, int inumber, int icolor, int ifilled) throws IOException {
		shape = ishape;
		number = inumber;
		color = icolor;
		filled = ifilled;
		this.addComponentListener(this);
		setBorder(notSelected);
		setLayout(new GridLayout(1, 0, 0, 0));
		String path = "setCards/";
		//number
		if(number==0){
			path+="one/";
		}
		if(number==1){
			path+="two/";
		}
		if(number==2){
			path+="three/";
		}
		//color
		if(color==0){
			path+="red/";
		}
		if(color==1){
			path+="green/";
		}
		if(color==2){
			path+="purple/";
		}
		//shape
		if(shape==0){
			path+="diamond/";
		}
		if(shape==1){
			path+="wavy/";
		}
		if(shape==2){
			path+="oval/";
		}
		//filled
		if(filled==0){
			path+="clear/";
		}
		if(filled==1){
			path+="shaded/";
		}
		if(filled==2){
			path+="solid/";
		}
		path+="card.PNG";
		//now we have the path, add the picture
		cardPNG = ImageIO.read(new File(path));
		cardPic = new JLabel(new ImageIcon(cardPNG));
		add(cardPic);
		
	}
	public void toggleBorder(){
		if(this.getBorder() == selected){
			this.setBorder(notSelected);
		}else{
			this.setBorder(selected);
		}
	}
	public void resetBorder(){
		this.setBorder(notSelected);
	}
	public void setNewCardBorder(){
		this.setBorder(newCard);
	}
	@Override
	public void componentHidden(ComponentEvent e) {
	}
	@Override
	public void componentMoved(ComponentEvent e) {	
	}
	@Override
	public void componentResized(ComponentEvent e) {
		//resize the pictures of the set cards
		if(getHeight() > 0 && getWidth() > 0){
			remove(cardPic);
			cardPic = new JLabel(new ImageIcon(cardPNG.getScaledInstance(getWidth(), getHeight(), 1)));
			add(cardPic);
			validate();
		}
	}
	@Override
	public void componentShown(ComponentEvent e) {
	}
	public Card getCard(){
		return new Card(shape, color, filled, number);
	}
	public void removePic(){
		remove(cardPic);
	}
	public void resetPic() throws IOException{
		System.out.println("resetPic called");
		remove(cardPic);
		String path = "setCards/";
		//number
		if(number==0){
			path+="one/";
		}
		if(number==1){
			path+="two/";
		}
		if(number==2){
			path+="three/";
		}
		//color
		if(color==0){
			path+="red/";
		}
		if(color==1){
			path+="green/";
		}
		if(color==2){
			path+="purple/";
		}
		//shape
		if(shape==0){
			path+="diamond/";
		}
		if(shape==1){
			path+="wavy/";
		}
		if(shape==2){
			path+="oval/";
		}
		//filled
		if(filled==0){
			path+="clear/";
		}
		if(filled==1){
			path+="shaded/";
		}
		if(filled==2){
			path+="solid/";
		}
		path+="card.PNG";
		//now we have the path, add the picture
		cardPNG = ImageIO.read(new File(path));
		cardPic = new JLabel(new ImageIcon(cardPNG.getScaledInstance(getWidth(), getHeight(), 1)));
		add(cardPic);
		
	}
	public boolean equals(Object mycard){
		CardGUI card = (CardGUI) mycard;
		if(card!=null&&card.color == color && card.number == number && card.shape == shape && card.filled == filled){
			return true;
		}else{
			return false;
		}
	}
	public int hashCode(){
		//System.out.println("hascode called");
		return color + 10*number + 100*shape + 1000*filled;
	}
	public String toString(){
		String str = "";
		
		switch(shape) { case 0: str+=" diamond";break; case 1: str+=" wavy";break; case 2: str+=" oval";break;}
		switch(color) { case 0: str+=" red ";break; case 1: str+=" green ";break; case 2: str+=" pink ";break;}
		switch(filled) { case 0: str+="clear";break; case 1: str+="shaded";break; case 2: str+="solid";break;}
		str+=number;
		return str;
	}
}