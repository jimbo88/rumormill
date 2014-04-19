import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class RumourMill {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int dimension = 100;
		Simulation.Mode mode = Simulation.Mode.FOURMODE;
		boolean wrapping = true;
		
		ArrayList<Coordinate> aware = new ArrayList<Coordinate>(0);
		
	    BufferedReader br;
	    
	    
	    try {
			br = new BufferedReader(new FileReader(args[0]));
	    
	        String line = br.readLine();
	        dimension = Integer.parseInt(line);
	        
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
	        
	        line = br.readLine();
	        while (line != null) {

	        	System.out.println(line);
	        	
	        	int x = Integer.parseInt(line.split(",")[0]);
	        	int y = Integer.parseInt(line.split(",")[1]);
	        	
	        	aware.add(new Coordinate(x,y));
	        	
	            line = br.readLine();
	        }
	        br.close();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		Simulation s = new Simulation(dimension, mode, wrapping, aware);
		
		s.runSim();
	}

}
