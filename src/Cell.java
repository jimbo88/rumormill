
public class Cell {

	public int timesTold = 0;
	private int firstTold = -1;
	
	
	public void tell(int time)
	{
		if (this.timesTold == 0)
		{
			this.firstTold = time;
		}
		this.timesTold += 1;
	}
	
	public int getfirstTold()
	{
		return this.firstTold;
	}
	
}
