//This class wraps an x coordinate and a y coordinate into a single object
//These coordinates are used in the list of coords that are aware of the rumor
public class Coordinate {

	private int x;
	private int y;
	
	//create a new coordinate
	public Coordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public String toString()
	{
		return "("+this.x+","+this.y+")";
		
	}
}
