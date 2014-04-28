package client;

public class Card 
{
	private int shape, color, fill, number;
	
	public Card(int s, int c, int f, int n)
	{
		shape = s;
		color = c;
		fill = f;
		number = n;
	}
	
	public int getShape()
	{
		return shape;
	}
	
	public int getColor()
	{
		return color;
	}
	
	public int getFill()
	{
		return fill;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public String toString()
	{
		return 
				"Color: " + color + "      " + "Shape: " + shape+ "      " + 
				"Fill: " + fill+ "       " + "Number: " + number;
	}
}
