//This class is used simply to represent the cells in the system
//Every time a cell is told the rumor this object is updated.
public class Cell {

	public int timesTold = 0;
	private int firstTold = -1;
	
	//Called from the simulation
	//used to tell a particular cell the rumor
	//the current simulation time is a parameter
	//this is stored if the cell hasn't been told yet
	public void tell(int time)
	{
		if (this.timesTold == 0)
		{
			this.firstTold = time;
		}
		this.timesTold += 1;
	}
	
	//retrieve the time that this cell was first told the rumor
	public int getfirstTold()
	{
		return this.firstTold;
	}
	
}
