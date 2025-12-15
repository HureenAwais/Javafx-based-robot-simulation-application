package RobotGUI;

/**
* The `gameRobot` class represents a robot in a game scenario.
* It extends the base `robot` class and includes specific properties and methods for the game.
* This class is serializable to be used in Java I/O operations.
* 
* @authorhureen
*/

public class gameRobot extends robot {
	private static final long serialVersionUID = 1L;
    private double currentRobotAngle, RobotSpeed;
    protected int score;
   
    /**
     * Constructs a `gameRobot` with specified parameters.
     * 
     * @param rx     The x-coordinate of the robot's center.
     * @param ry     The y-coordinate of the robot's center.
     * @param rad    The radius of the robot.
     * @param angle  The initial angle of the robot.
     * @param speed  The speed of the robot.
     */

    public gameRobot(double rx, double ry, double rad, double angle, double speed) {
        super(rx, ry, rad);
        currentRobotAngle = angle;
       RobotSpeed=speed;
       col = 'o';
       score = 3;
    }
  
    /**
     * Checks and updates the state of the robot based on its position in the game arena.
     * 
     * @param RA The game arena where the robot exists.
     */
   @Override
    protected void checkRobot(RobotArena RA) {
    
    	currentRobotAngle = RA.CheckRobotAngle(x, y, rad, currentRobotAngle, robotId);
    	if (RA.checkHitCharge(this)) score++;
    	if (RA.checkHitAvoid(this)) score--;
    	if (score == 0) RA.dltRobot(x, y);
    }
   /**
    * Adjusts the position of the robot based on its current angle and speed.
    */
     @Override
   protected void adjustRobot() {
    
        double rad = Math.toRadians(currentRobotAngle);
        x += RobotSpeed * Math.cos(rad);		// new X position
		y += RobotSpeed * Math.sin(rad);		// new Y position
		//super.hitting(x, y, rad); 
		
			
		} 
   
     /**
      * Draws the robot on the specified canvas, including wheels.
      * 
      * @param c The canvas where the robot is drawn.
      */


    public void drawRobot(RobotCanvas c) {
		
    	
        super.drawRobot(c);
        c.showInt(x, y, score); //showing the score
      // add wheels to robot
        double leftWheelX1 =  x + rad * (Math.cos(Math.toRadians(currentRobotAngle-55)));
        double leftWheelY1 = y + rad * (Math.sin(Math.toRadians(currentRobotAngle-55)));
        double leftWheelX2 = x + rad * (Math.cos(Math.toRadians(currentRobotAngle-125)));
       
        double leftWheelY2 = y + rad * (Math.sin(Math.toRadians(currentRobotAngle-125)));
        
        
        double rightWheelX1 = x + rad * (Math.cos(Math.toRadians(currentRobotAngle+55)));
        double rightWheelY1 = y + rad * (Math.sin(Math.toRadians(currentRobotAngle+55)));
        
        double rightWheelX2 = x +  rad * (Math.cos(Math.toRadians(currentRobotAngle +125)));
        double rightWheelY2 = y + rad * (Math.sin(Math.toRadians(currentRobotAngle +125)));
       
      
        // Draw the left wheel as a thick line
     
        c.strokeLine(leftWheelX1 ,  leftWheelY1, leftWheelX2, leftWheelY2, 10,  'l');

        // Draw the right wheel as a thick line
        c.strokeLine(rightWheelX1 , rightWheelY1, rightWheelX2, rightWheelY2, 10,  'l'); 
     
    }


    /**
	 * return string defining robot type
	 */
	protected String getStrType() {
		return "Game Robot";
	}
 

       
}
