/**
 * The `beamClass` represents a specialized type of robot, called Beam Robot, which extends the `whiskerRobot` class.
 * This robot has an additional feature of a beam that extends from the front, forming a cone-like structure.
 * It overrides certain methods to handle the unique characteristics of the Beam Robot.
 *
 * @author [hureen]
 */

package RobotGUI;
/**
 * The Beam Robot class, extending the whiskerRobot class.
 */


public class beamClass extends whiskerRobot {
	private static final long serialVersionUID = 1L;
    private double beamLength;
    private double beamAngle;
    Line whiskerJoinL, whiskerJoinR;
    /**
     * Default constructor for the Beam Robot.
     * Initializes the robot at the origin with default parameters.
     */
 public beamClass() {
        this(0, 0, 10, 30, 3, 23);
    }
    /**
     * Parameterized constructor for the Beam Robot.
     *
     * @param rx     The x-coordinate of the robot.
     * @param ry     The y-coordinate of the robot.
     * @param rad    The radius of the robot.
     * @param angle  The initial angle of the robot.
     * @param speed  The speed of the robot.
     * @param length The length of the beam.
     */
    public beamClass(double rx, double ry, double rad, double angle, double speed, double l) {
        super(rx, ry, rad, angle, speed, l);
        this.beamLength = l;
        this.beamAngle = 40;
        col='g';
    }
    /**
     * Calculate the outer points of the beam.
     * Used to determine the end points of the whiskers forming the cone-like structure.
     */
    protected void OuterBeam()
    {
    	double leftWhisker2X1 =  x + (rad * (Math.cos(Math.toRadians(currentRobotAngle-beamAngle))));
        double leftWhisker2Y1 = y + (rad * (Math.sin(Math.toRadians(currentRobotAngle-beamAngle))));
        double leftWhisker2X2 = x + ((rad + beamLength )* (Math.cos(Math.toRadians(currentRobotAngle-beamAngle))));
        double leftWhisker2Y2 = y + ((rad + beamLength )* (Math.sin(Math.toRadians(currentRobotAngle-beamAngle))));
       
        double rightWhisker2X1 = x + (rad * (Math.cos(Math.toRadians(currentRobotAngle+beamAngle))));
        double rightWhisker2Y1 = y + (rad * (Math.sin(Math.toRadians(currentRobotAngle+beamAngle))));
        
         double rightWhisker2X2 = x +  ((rad + beamLength )* (Math.cos(Math.toRadians(currentRobotAngle +beamAngle))));
        double rightWhisker2Y2 = y + ((rad + beamLength ) *(Math.sin(Math.toRadians(currentRobotAngle +beamAngle))));
        leftWhisker = new Line(leftWhisker2X1 ,  leftWhisker2Y1, leftWhisker2X2, leftWhisker2Y2);
        rightWhisker = new Line(rightWhisker2X1 ,  rightWhisker2Y1, rightWhisker2X2, rightWhisker2Y2);
    }
    /**
     * Calculate the end points of the beam that join with the whiskers.
     * Used to form the triangle connecting the end points of the whiskers.
     */
    protected void beamEnds(){
    	
    	double leftWhisker2X2 = x + ((rad + beamLength )* (Math.cos(Math.toRadians(currentRobotAngle-beamAngle))));
        double leftWhisker2Y2 = y + ((rad + beamLength )* (Math.sin(Math.toRadians(currentRobotAngle-beamAngle))));
        double rightWhisker2X2 = x +  ((rad + beamLength )* (Math.cos(Math.toRadians(currentRobotAngle +beamAngle))));
        double rightWhisker2Y2 = y + ((rad + beamLength ) *(Math.sin(Math.toRadians(currentRobotAngle +beamAngle))));
        double whiskerJoinLX2 =  x +  ((rad + beamLength ) * (Math.cos(Math.toRadians(currentRobotAngle + super.getWhiskerAngle())))); 
        double whiskerJoinLY2 =    y + ((rad + beamLength ) *(Math.sin(Math.toRadians(currentRobotAngle + super.getWhiskerAngle()))));
        double whiskerJoinRX2 =  x +  ((rad + beamLength ) * (Math.cos(Math.toRadians(currentRobotAngle -super.getWhiskerAngle()))));
        double whiskerJoinRY2 =    y + ((rad + beamLength ) *(Math.sin(Math.toRadians(currentRobotAngle -super.getWhiskerAngle()))));
    	whiskerJoinL= new Line( leftWhisker2X2, leftWhisker2Y2, whiskerJoinRX2,whiskerJoinRY2);
        whiskerJoinR= new Line( rightWhisker2X2, rightWhisker2Y2, whiskerJoinLX2, whiskerJoinLY2);
    }
    /**
     * Check for collisions with other robots in the arena.
     * Overrides the method in the superclass (`whiskerRobot`) to account for the beam structure.
     *
     * @param RA The RobotArena containing the robots.
     */
    protected void checkRobot(RobotArena RA) {
    	//currentRobotAngle = RA.CheckRobotAngle(x, y, rad, currentRobotAngle, robotId);
    	super.checkRobot(RA); // Call the superclass method
    	OuterBeam();
    	currentRobotAngle = RA. checkIntersection(rightWhisker, leftWhisker,  currentRobotAngle, robotId, x, y);
    	
    	
    }
    /**
     * Draw the Beam Robot on the canvas.
     *
     * @param c The RobotCanvas on which the robot is drawn.
     */
    @Override
    public void drawRobot(RobotCanvas c) {
        super.drawRobot(c);
        
     // Draw the lines connecting the end points of whiskers to form a triangle
        
        c.showWhiskers(rightWhisker);
        c.showWhiskers(leftWhisker);
        beamEnds();
        c.showWhiskers(whiskerJoinL);
        c.showWhiskers(whiskerJoinR);
        OuterBeam();
        c.showWhiskers(rightWhisker); 
        c.showWhiskers(leftWhisker);
      
        }
    /**
     * Get the string representation of the robot type.
     *
     * @return A string indicating the type of robot (Beam).
     */
    protected String getStrType() {
		return "Beam ";
	}
    
  }

   


