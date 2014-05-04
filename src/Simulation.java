import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//The main simulation class
//The main global simulation logic occurs here
//A simulation can be run to completion or stepped through
public class Simulation {
	
	private Cell[][] board; //the cellular automaton grid
	private int time = 0; //global time
	private boolean finished = false;
	
	//all cells that are currently aware of the rumor are stored here
	private ArrayList<Coordinate> cellsAware; 
	private boolean wrapAround = false;

	//update modes
	public enum Mode {
	    FOURMODE, EIGHTMODE
	}
	
	private Mode mode;
	
	//what type of graph to plot
	private int graphMode;
	
	//create a new simulation object. parameters include board dimension
	//and a list of coordinates
	//Each coordinate refers to a cell that initially knows the rumor
	public Simulation(int dim, Mode mode, 
			boolean wrapAround, List<Coordinate> coord, int graphMode)
	{
		this.wrapAround = wrapAround;

		this.mode = mode;
		this.graphMode = graphMode;
		this.cellsAware = new ArrayList<Coordinate>(0);
		this.cellsAware.addAll(coord);

		
		//initialize board array
		
		this.board = new Cell[dim][dim];
		for (int i=0;i<dim;i++)
		{
			for (int j=0;j<dim;j++)
			{
				//initialise new cells
				this.board[i][j] = new Cell();
			}
		}
		
		//initially tell the rumor to all cells specified in the input file
		//using time = 0
		for (Coordinate c : this.cellsAware)
		{
			this.board[c.getX()][c.getY()].tell(this.time);
		}
		
		this.time += 1;
	}

	//run the simulation to completion
	public void runSim()
	{
		switch (this.graphMode)
		{
			
			case 0:
				printBoard();
				break;
			
			case 1:
				printNumberOfTimesBoard();
				break;
				
			case 2:
				printFirstHeardBoard();
				break;
				
				
			default:
				break;
		}
		
		// while simulation is not finished, i.e. not everyone knows the rumor
		// keep updating the simulation/ "ticking"
		while (this.cellsAware.size() < (this.board.length*this.board.length))
		{
			this.tick();
			
			//print a different graph depending on the mode specified by input
			switch (this.graphMode)
			{
				
				case 0:
					printBoard();
					break;
				
				case 1:
					printNumberOfTimesBoard();
					break;
					
				case 2:
					printFirstHeardBoard();
					break;
					
					
				default:
					break;
			}
			
		}
		
		if (this.graphMode < 3)
			{
			System.out.println("Total ticks: " + (this.time-1));
			}

	}
	
	//retrieve a neighbor to tell from the Moore neighborhood
	private Coordinate getEightModeNeighbour(Coordinate c)
	{
		Coordinate returnCoord = null;
		//generate a random int in order to decide the neighbor
		int randomNeighbor = new Random().nextInt(8);
		
		
		//each case represents a different neighbor coordinate
		switch (randomNeighbor)
		{
			case 0: 
				returnCoord = 
					normalizeCoord(new Coordinate(c.getX()-1,c.getY()-1));
				break;
				
			case 1: 
				returnCoord = 
					normalizeCoord(new Coordinate(c.getX(),c.getY()-1));
				break;
				
			case 2: 
				returnCoord = 
					normalizeCoord(new Coordinate(c.getX()+1,c.getY()-1));
				break;
			
			case 3: 
				returnCoord = 
					normalizeCoord(new Coordinate(c.getX()-1,c.getY()));
				break;
			
			case 4: 
				returnCoord = 
					normalizeCoord(new Coordinate(c.getX()+1,c.getY()));
				break;
			
			case 5: 
				returnCoord = 
					normalizeCoord(new Coordinate(c.getX()-1,c.getY()+1));
				break;
			
			case 6: 
				returnCoord = 
					normalizeCoord(new Coordinate(c.getX(),c.getY()+1));
				break;
			
			case 7: 
				returnCoord = 
					normalizeCoord(new Coordinate(c.getX()+1,c.getY()+1));
				break;
			
			default: 
				break;
		}
		
		return returnCoord;
		
	}
	
	//This is the same as the eight-mode function but selects from
	//four neighbours instead
	private Coordinate getFourModeNeighbour(Coordinate c)
	{
		Coordinate returnCoord = null;
		int randomNeighbor = new Random().nextInt(4);
		
		switch (randomNeighbor)
		{
			case 0: 
				returnCoord = 
					normalizeCoord(new Coordinate(c.getX()-1,c.getY()));
				break;
				
			case 1: 
				returnCoord = 
					normalizeCoord(new Coordinate(c.getX(),c.getY()-1));
				break;
				
			case 2: 
				returnCoord = 
					normalizeCoord(new Coordinate(c.getX()+1,c.getY()));
				break;
			
			case 3: 
				returnCoord = 
					normalizeCoord(new Coordinate(c.getX(),c.getY()+1));
				break;
			
			default: 
				break;
		}
		
		return returnCoord;
		
	}
	
	// This is used so that if out of bounds coordinates are generated earlier
	// they can be wrapped around to valid coordinates (if wrapping is on)
	private Coordinate normalizeCoord(Coordinate c) {
		// either normalize the coordinate or set it to null
		// if wrap around is off then out of bounds coordinates
		// will be nullified
		
		//normalize y coordinate
		if (c.getX() > this.board.length-1)
		{
			if (this.wrapAround)
			{
				c.setX(c.getX()-this.board.length);
			}
			else {return null;}
		}
		else if (c.getX() < 0)
		{
			if (this.wrapAround)
			{
				c.setX(c.getX()+this.board.length);
			}
			else {return null;}
		}		
		
		//normalize y coordinate
		if (c.getY() > this.board.length-1)
		{
			if (this.wrapAround)
			{
				c.setY(c.getY()-this.board.length);
			}
			else {return null;}
		}
		else if (c.getY() < 0)
		{
			if (this.wrapAround)
			{
				c.setY(c.getY()+this.board.length);
			}
			else {return null;}
		}
		
		
		return c;
	}

	private void tick() {
		// perform a single tick of the simulation
		
		//track the cells that have only become newly
		//aware of the rumor on this tick
		ArrayList<Coordinate> newlyAware = new ArrayList<Coordinate>(0);
		
		//loop through all cells that know the rumor
		for (Coordinate c : this.cellsAware)
		{
			Coordinate n = null;
			//find the neighbor to tell, depending on update rule mode
			switch(this.mode)
			{
				case FOURMODE:
					n = getFourModeNeighbour(c);
					if (n != null)
					{
						//tell the cell the rumor
						this.board[n.getX()][n.getY()].tell(this.time);
						if ((this.board[n.getX()][n.getY()].getfirstTold() 
								== this.time) && (
								this.board[n.getX()][n.getY()].timesTold == 1))
						{
							//add the cell to the newly aware list
							newlyAware.add(n);
						}
					}
					break;
				
				//as above
				case EIGHTMODE:
					n = getEightModeNeighbour(c);
					if (n != null)
					{
						this.board[n.getX()][n.getY()].tell(this.time);
						if ((this.board[n.getX()][n.getY()].getfirstTold() 
								== this.time) && (
								this.board[n.getX()][n.getY()].timesTold == 1))
						{
							newlyAware.add(n);
						}
					}
					break;
					
				default: 
					break;
			}
		}
		
		//add all newly aware cells to the main list of aware cells
		this.cellsAware.addAll(newlyAware);
				
		if (this.cellsAware.size() == (this.board.length*this.board.length))
		{
			this.finished = true;
		}
		
		this.time += 1;
	}

	public int getTicks()
	{
	 return	this.time-1;
	}
	
	//print board state
	//will only show if cells have been told(T), or not(-)
	public void printBoard()
	{
		System.out.println("Time:" + (this.time-1));
		System.out.println("CellsAware: " + this.cellsAware.size());

		for (int i=0;i<board.length;i++)
		{
			for (int j=0;j<board.length;j++)
			{
				if (board[j][i].timesTold > 0)
				{
					System.out.print("T");
				}
				else
				{
					System.out.print("-");
				}
				
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
	
	
	//print a csv output of when each cell first heard the rumor
	public void printFirstHeardBoard()
	{
		System.out.println("Time:" + (this.time-1));
		System.out.println("CellsAware: " + this.cellsAware.size());

		for (int i=0;i<board.length;i++)
		{
			for (int j=0;j<board.length;j++)
			{
				
				System.out.print(board[j][i].getfirstTold());
				if (j < (board.length-1))
				{
					System.out.print(",");
				}

				
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
	
	//print a csv output of how many times each cell has heard the rumor
	public void printNumberOfTimesBoard()
	{
		System.out.println("Time:" + (this.time-1));
		System.out.println("CellsAware: " + this.cellsAware.size());

		for (int i=0;i<board.length;i++)
		{
			for (int j=0;j<board.length;j++)
			{
				
				System.out.print(board[j][i].timesTold);
				if (j < (board.length-1))
				{
					System.out.print(",");
				}

				
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
	
	
}
