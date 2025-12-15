package RobotGUI;

import java.io.Serializable;

/**
 * The `robot` class is an abstract class representing a basic robot in a two-dimensional space.
 * It provides common properties and methods for robot entities, such as position and collision detection.
 * This class is serializable to be used in Java I/O operations.
 * 
 * @authorhureen
 */
public abstract class robot implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int robotId; // Unique identifier for each robot
    protected double x, y, rad; // Position and radius of the robot
    protected char col; // Color of the robot
    protected static int robotCount = 0; // Used to give each robot a unique number
	
    /**
     * Constructs a robot with default parameters.
     * Initialises the position and radius with default values.
     */
    robot() {
        this(100, 100, 10);
    }

    /**
     * Constructs a robot with specified parameters.
     * 
     * @param rx  The initial x-coordinate of the robot.
     * @param ry  The initial y-coordinate of the robot.
     * @param rad The radius of the robot.
     */
    public robot(double rx, double ry, double rad) {
        x = rx;
        y = ry;
        robotId = robotCount++;
        this.rad = rad;
 
    }

    /**
     * Gets the unique identifier of the robot.
     * 
     * @return The robot identifier.
     */
    protected int getID() {
        return robotId;
    }

    /**
     * Gets the x-coordinate of the robot.
     * 
     * @return The x-coordinate.
     */
    protected double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the robot.
     * 
     * @return The y-coordinate.
     */
    protected double getY() {
        return y;
    }

    /**
     * Gets the radius of the robot.
     * 
     * @return The radius.
     */
    protected double getRad() {
        return rad;
    }

    /**
     * Gets the color of the robot.
     * 
     * @return The color.
     */
    public char getCol() {
        return col;
    }

    /**
     * Updates the position of the robot.
     * 
     * @param nx The new x-coordinate.
     * @param ny The new y-coordinate.
     */
    public void updateXY(double nx, double ny) {
        x = nx;
        y = ny;
    }

    /**
     * Gets a string describing the type of the robot.
     * 
     * @return A string representing the type of the robot.
     */
    protected String getStrType() {
        return "Robot";
    }

    /**
     * Returns a string representation of the robot.
     * 
     * @return A string with information about the robot's type, ID, and position.
     */
    public String toString() {
        return getStrType() + robotId + " at X : " + Math.round(x) + ",  Y : " + Math.round(y) +  ".";
    }

    /**
     * Checks if the robot is at a specific location.
     * 
     * @param x2 The x-coordinate to check.
     * @param y2 The y-coordinate to check.
     * @return True if the robot is at the specified location; otherwise, false.
     */
    public boolean isHere(double x2, double y2) {
        return x == x2 && y == y2;
    }

    /**
     * Draws the robot on the specified canvas.
     * 
     * @param c The canvas where the robot is drawn.
     */
    public void drawRobot(RobotCanvas c) {
        c.showCircle(x, y, rad, col);
      
    }

    /**
     * Abstract method for checking the robot in a given arena.
     * 
     * @param RA The robot arena where the robot is checked.
     */
    protected abstract void checkRobot(RobotArena RA);

    /**
     * Checks if the robot is hitting a point (ox, oy) with a given radius.
     * 
     * @param ox The x-coordinate of the point.
     * @param oy The y-coordinate of the point.
     * @param or The radius of the object to check for collision.
     * @return True if hitting; otherwise, false.
     */
    public boolean hitting(double ox, double oy, double or) {
        return (ox - x) * (ox - x) + (oy - y) * (oy - y) < (or + rad) * (or + rad);
    }

    /**
     * Checks if the robot is hitting another robot.
     * 
     * @param r The other robot to check for collision.
     * @return True if hitting; otherwise, false.
     */
    public boolean hitting(robot r) {
        return hitting(r.getX(), r.getY(), r.getRad());
        
    }

    /**
     * Gets a string representation of the robot's state.
     * 
     * @return A string with the x-coordinate, y-coordinate, and ID of the robot.
     */
    public String rs() {
        return x + "  " + y + "  " + robotId + "  .";
    }

    /**
     * Abstract method for adjusting the robot.
     */
    protected abstract void adjustRobot();
}

