/**
 * The `obstacles` class represents an obstacle in the robot arena.
 * It is a subclass of the `robot` class and includes methods for checking collisions and adjustments.
 * Obstacles are stationary and do not move in the arena.
 * 
 * @authorhureen
 */

package RobotGUI;
/**
 * The `obstacles` class represents an obstacle in the robot arena.
 */ 
public class obstacles extends robot {
	
	private static final long serialVersionUID = 1L;
	 //private double currentRobotAngle; 
	 /**
	     * Default constructor for the `obstacles` class.
	     */
	 
	public obstacles() {
		
	}

	 /**
     * Parameterized constructor for the `obstacles` class.
     * Initializes the obstacle with specified parameters.
     *
     * @param ix     The x-coordinate of the obstacle.
     * @param iy     The y-coordinate of the obstacle.
     * @param ir     The radius of the obstacle.
   
     */
	public obstacles(double ix, double iy, double ir) {
		super(ix, iy, ir);
		
	     
		col = 'y';
	}


	 /**
     * Get a string representation of the obstacle type.
     *
     * @return A string indicating the obstacle type.
     */
	protected String getStrType() {
		return "Blocker";
	}

	@Override
	protected void checkRobot(RobotArena RA) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void adjustRobot() {
		// TODO Auto-generated method stub
		
	}	

}
