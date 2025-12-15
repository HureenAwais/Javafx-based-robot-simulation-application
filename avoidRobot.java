package RobotGUI;

import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
/**
 * The `avoidRobot` class represents a robot in a game scenario that can avoid obstacles.
 * It extends the `gameRobot` class and includes specific properties and methods for obstacle avoidance.
 * This class is serializable to be used in Java I/O operations.
 * 
 * @hureen
 */
public class avoidRobot extends gameRobot {
	private static final long serialVersionUID = 1L;
	//private double avoidRobotAngle;	
	private double offSet; // Offset for drawing additional features

	 /**
     * Constructs an `avoidRobot` with default parameters.
     * Initialises offset and sets the color to red.
     */
	public avoidRobot() {
	        this(0, 0, 10, 30, 3);
	    }
	/**
     * Constructs an `avoidRobot` with specified parameters.
     * 
     * @param rx    The x-coordinate of the robot's center.
     * @param ry    The y-coordinate of the robot's center.
     * @param rad   The radius of the robot.
     * @param angle The initial angle of the robot.
     * @param speed The speed of the robot.
     */
	  public avoidRobot(double rx, double ry, double rad, double angle, double speed) {
	        super(rx, ry, rad, angle, speed);
	       col = 'r';
	        //this.avoidRobotAngle = 50;
	        offSet= 12; 
	    }
	  
	  /**
	     * Draws the `avoidRobot` on the specified canvas, including additional avoidance features.
	     * 
	     * @param c The canvas where the robot is drawn.
	     */
	    @Override
	    public void drawRobot(RobotCanvas c) {
	        super.drawRobot(c);
	        c.gc.setStroke(Color.LIGHTBLUE);
	        c.gc.strokeArc(x-(rad+offSet), y-(rad + offSet), rad*3.5, rad*3.5, 0, 360, ArcType.OPEN);
	    }
	    

	    /**
	     * Returns a string defining the type of the robot.
	     * 
	     * @return The string "AvoidRobot."
	     */
	    protected String getStrType() {
			return "RingRobot ";
		}
}

