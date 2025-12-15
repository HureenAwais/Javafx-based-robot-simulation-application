/**
 * The `whiskerRobot` class is a subclass of the `robot` class and represents a robot with whiskers.
 * It includes methods to handle whisker movement, collision detection, and drawing on a canvas.
 * This class encapsulates the robot's current angle, speed, whisker length, and whisker angle.
 * 
 * @authorhureen
 */

package RobotGUI;
/**
 * The `whiskerRobot` class represents a robot with whiskers.
 */
public class whiskerRobot extends robot {
	
	private static final long serialVersionUID = 1L;	
    protected double currentRobotAngle;
	private double RobotSpeed;
	 private double whiskerLength;
	private double  whiskerRobotAngle;
	Line rightWhisker;
	Line leftWhisker;
	/**
     * Default constructor for the `whiskerRobot` class.
     * Initializes the robot at the origin with default parameters.
     */	 
	    
public whiskerRobot() {
	this(0,0,10,30,3,23);
}
/**
 * Parameterized constructor for the `whiskerRobot` class.
 * Initializes the robot with specified parameters.
 *
 * @param rx   The x-coordinate of the robot.
 * @param ry   The y-coordinate of the robot.
 * @param rad  The radius of the robot.
 * @param angle The initial angle of the robot.
 * @param speed The speed of the robot.
 * @param l    The length of the whiskers.
 */
  public whiskerRobot(double rx, double ry, double rad, double angle, double speed, double l ) {
	     super(rx, ry, rad);
	     currentRobotAngle = angle;
	      RobotSpeed=speed;
	      whiskerLength = l;
	      whiskerRobotAngle= 15;
	       col = 'b';
	    }
/**
 * Get the angle of the whiskers.
 *
 * @return The angle of the whiskers.
 */

	  public  double getWhiskerAngle() {
		  return whiskerRobotAngle;
		  
	  }
	  
	  /**
	     * Calculate and set the coordinates of the whiskers.
	     */  
          protected void Whiskers() {
		  
	        double leftWhiskerX1 =  x + (rad * (Math.cos(Math.toRadians(currentRobotAngle-whiskerRobotAngle))));
	        double leftWhiskerY1 = y + (rad * (Math.sin(Math.toRadians(currentRobotAngle-whiskerRobotAngle))));
	        double leftWhiskerX2 = x + ((rad + whiskerLength )* (Math.cos(Math.toRadians(currentRobotAngle-whiskerRobotAngle))));
	        double leftWhiskerY2 = y + ((rad + whiskerLength )* (Math.sin(Math.toRadians(currentRobotAngle-whiskerRobotAngle))));
	        leftWhisker =  new Line(leftWhiskerX1 ,  leftWhiskerY1, leftWhiskerX2, leftWhiskerY2);
	        
	        double rightWhiskerX1 = x + (rad * (Math.cos(Math.toRadians(currentRobotAngle+whiskerRobotAngle))));
	        double rightWhiskerY1 = y + (rad * (Math.sin(Math.toRadians(currentRobotAngle+whiskerRobotAngle))));
	        double rightWhiskerX2 = x +  ((rad + whiskerLength )* (Math.cos(Math.toRadians(currentRobotAngle +whiskerRobotAngle))));
	        double rightWhiskerY2 = y + ((rad + whiskerLength ) *(Math.sin(Math.toRadians(currentRobotAngle +whiskerRobotAngle))));
	        
	        rightWhisker =  new Line(rightWhiskerX1 ,  rightWhiskerY1, rightWhiskerX2, rightWhiskerY2);
	  }
    /**
     * Check for collisions with other robots using whiskers.
     *
     * @param RA The RobotArena containing other robots.
     */
	    protected void checkRobot(RobotArena RA) {
	    	
	    	Whiskers();
	    	currentRobotAngle = RA. checkIntersection(rightWhisker, leftWhisker,  currentRobotAngle, robotId, x ,y);
	    	currentRobotAngle = RA.CheckRobotAngle(x, y, rad, currentRobotAngle, robotId);
	    	
	    }
	    /**
	     * Adjust the robot's position based on its angle and speed.
	     */
	     @Override
	   protected void adjustRobot() {
	    
	        double rad = Math.toRadians(currentRobotAngle);
	        x += RobotSpeed * Math.cos(rad);		// new X position
			y += RobotSpeed * Math.sin(rad);		// new Y position
			 		
			} 
	     /**
	      * Get a string representation of the robot type.
	      *
	      * @return A string indicating the robot type.
	      */
	   
	     protected String getStrType() {
				return "whisker";
			}	
	     /**
	      * Draw the whisker robot on the canvas.
	      *
	      * @param c The RobotCanvas on which to draw the robot.
	      */
	    public void drawRobot(RobotCanvas c) {
	    	
	        super.drawRobot(c);
	     
	      // add wheels to robot, Draw the left wheel as a thick line
	        double leftWheelX1 =  x + rad * (Math.cos(Math.toRadians(currentRobotAngle-55)));
	        double leftWheelY1 = y + rad * (Math.sin(Math.toRadians(currentRobotAngle-55)));
	        double leftWheelX2 = x + rad * (Math.cos(Math.toRadians(currentRobotAngle-125)));
	        double leftWheelY2 = y + rad * (Math.sin(Math.toRadians(currentRobotAngle-125)));
	     // Draw the right wheel as a thick line
	        
	        double rightWheelX1 = x + rad * (Math.cos(Math.toRadians(currentRobotAngle+55)));
	        double rightWheelY1 = y + rad * (Math.sin(Math.toRadians(currentRobotAngle+55)));
	        double rightWheelX2 = x +  rad * (Math.cos(Math.toRadians(currentRobotAngle +125)));
	        double rightWheelY2 = y + rad * (Math.sin(Math.toRadians(currentRobotAngle +125)));
	        //Draw the left wheel as a thick line
	        c.strokeLine(leftWheelX1 ,  leftWheelY1, leftWheelX2, leftWheelY2, 10,  'l');

	        // Draw the right wheel as a thick line
	        c.strokeLine(rightWheelX1 , rightWheelY1, rightWheelX2, rightWheelY2, 10,  'l'); 
	        //add whiskers from line class
	        Whiskers();
	        // Show whiskers
	        c.showWhiskers(rightWhisker);
	        c.showWhiskers(leftWhisker);
	        
	    }    
	}

