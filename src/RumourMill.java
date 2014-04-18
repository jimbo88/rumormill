import java.util.ArrayList;


public class RumourMill {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		ArrayList<Coordinate> aware = new ArrayList<Coordinate>(0);

		aware.add(new Coordinate(0,0));
		
		Simulation s = new Simulation(10, aware, Simulation.Mode.EIGHTMODE, true);
		
		s.runSim();
	}

}
