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
		return String.valueOf(shape) + String.valueOf(number) + String.valueOf(color) + String.valueOf(fill);
	}
}
