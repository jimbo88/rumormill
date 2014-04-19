import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Simulation {
	
	private Cell[][] board;
	private int time = 0;
	private boolean finished = false;
	private ArrayList<Coordinate> cellsAware;
	
	private boolean wrapAround = false;

	public enum Mode {
	    FOURMODE, EIGHTMODE
	}
	
	private Mode mode;
	
	//create a new simulation object. parameters include board dimension and a list of coordinates
	//Each coordinate refers to a cell that initially knows the rumor\
	
	public Simulation(int dim, Mode mode, boolean wrapAround, List<Coordinate> coord)
	{
		this.wrapAround = wrapAround;

		this.mode = mode;
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
		
		for (Coordinate c : this.cellsAware)
		{
			this.board[c.getX()][c.getY()].tell(this.time);
		}
		
		this.time += 1;
	}

	public void runSim()
	{
		printBoard();
		
		while (this.cellsAware.size() < (this.board.length*this.board.length))
		{
			this.tick();
			printBoard();
		}
		
		System.out.println("Total ticks: " + (this.time-1));

	}
	
	private Coordinate getEightModeNeighbour(Coordinate c)
	{
		Coordinate returnCoord = null;
		int randomNeighbor = new Random().nextInt(8);
		
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
		// TODO Auto-generated method stub
		
		ArrayList<Coordinate> newlyAware = new ArrayList<Coordinate>(0);
		for (Coordinate c : this.cellsAware)
		{
			Coordinate n = null;
			switch(this.mode)
			{
				case FOURMODE:
					n = getFourModeNeighbour(c);
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
		
		this.cellsAware.addAll(newlyAware);
				
		if (this.cellsAware.size() == (this.board.length*this.board.length))
		{
			this.finished = true;
		}
		
		this.time += 1;
	}

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
	
}
