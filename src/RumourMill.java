import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


//This is the main driver class
//It is capable of parsing input files and running multiple simulations
public class RumourMill {

	public static void main(String[] args) {

		int dimension = 101; //board dimension initially 101
		
		//mode is the update rule mode, default FOURMODE
		Simulation.Mode mode = Simulation.Mode.FOURMODE;
		boolean wrapping = true; //wrap-around is on
		
		//keeps track of the coords of all cells that are aware of the rumor
		ArrayList<Coordinate> aware = new ArrayList<Coordinate>(0);
	    BufferedReader br;
	    
	    
	    try {
	    	//read an input file
			br = new BufferedReader(new FileReader(args[0]));
	    
			//read dimension
	        String line = br.readLine();
	        dimension = Integer.parseInt(line);
	        
	        //read mode line
	        line = br.readLine();
	        if (line == "EIGHTMODE")
	        {
	        	mode = Simulation.Mode.EIGHTMODE;
	        }
	        else if (line == "FOURMODE")
	        {
	        	mode = Simulation.Mode.FOURMODE;
	        }
	        
	        //is wrapping on or off?
	        line = br.readLine();
	        if (line == "false")
	        {
	        	wrapping = false;
	        }
	        else
	        {
	        	wrapping = true;
	        }
	        
	        
	        //keep reading in coordinates of initial cells
	        line = br.readLine();
	        while (line != null) {
	        	
	        	int x = Integer.parseInt(line.split(",")[0]);
	        	int y = Integer.parseInt(line.split(",")[1]);
	        	
	        	//add read in coordinate to list of aware cells
	        	aware.add(new Coordinate(x,y));
	        	
	            line = br.readLine();
	        }
	        br.close();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	    //keep track of total clock ticks
		float totalTicks = 0;
		
		//loop to run the simulation many times
		for (int i=0; i<Integer.parseInt(args[2]); i++)
		{
		Simulation s = new Simulation(dimension, mode, wrapping, 
									aware, Integer.parseInt(args[1]));
		
		s.runSim();
		totalTicks += s.getTicks();
		}
		
		System.out.println("Average ticks over "+ Integer.parseInt(args[2]) 
				+" run(s): " + totalTicks/Integer.parseInt(args[2]));
	}

}
