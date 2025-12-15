/**
 * The `Line` class represents a line in 2D space defined by two points (x1, y1) and (x2, y2).
 * It includes methods for calculating various properties of the line and checking for intersections.
 * This class is serializable to be used in Java I/O operations.
 * 
 * @author hureen
 */
package RobotGUI;

import java.io.Serializable;



public class Line implements Serializable{
	private static final long serialVersionUID = 1L;
	private double[] coords;		// coordinates of line (x1, y1, x2, y2)
	private double[] xy;			// xy point used in calculation
	private double gradient;	// gradient of line
	private double offset;		// offset
	 /**
     * Default constructor creates a basic horizontal line.
     */
	Line() {
		this(0,0,1,0);
	}
    /**
     * Constructor to create a line from (x1, y1) to (x2, y2).
     * 
     * @param x1 The x-coordinate of the starting point.
     * @param y1 The y-coordinate of the starting point.
     * @param x2 The x-coordinate of the ending point.
     * @param y2 The y-coordinate of the ending point.
     */
	Line(double x1, double y1, double x2, double y2) {
		coords = new double[] {x1, y1, x2, y2};	// store end points in coords
		xy = new double[] {x1, y1};				// initialise xy to one point
	}
	  /**
     * Constructor to create a line from an array of coordinates [x1, y1, x2, y2].
     * 
     * @param cs An array containing the coordinates of the line.
     */
	Line(double [] cs) {
		this(cs[0], cs[1], cs[2], cs[3]);
	}
	   /**
     * Constructor to create a line from an array of coordinates [x1, y1, x2, y2].
     * 
     * @param cs An array containing the coordinates of the line.
     */
	Line(int [] cs) {
		this(cs[0], cs[1], cs[2], cs[3]);
	}
	
	  /**
     * Calculate the distance between two points (x1, y1) and (x2, y2).
     * 
     * @param x1 The x-coordinate of the first point.
     * @param y1 The y-coordinate of the first point.
     * @param x2 The x-coordinate of the second point.
     * @param y2 The y-coordinate of the second point.
     * @return The distance between the two points.
     */
	static double distance(double x1, double y1, double x2, double y2) {
		return (double) Math.sqrt(((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)));
	}
	
	  /**
     * Calculate the square of the length of the line.
     * 
     * @return The square of the length of the line.
     */
	public double lineLength() {
		return distance(coords[0], coords[1], coords[2], coords[3]);
	}
	  /**
     * Get the XY point associated with the line.
     * 
     * @return An array of two doubles representing the XY point.
     */
	public double[] getXY() {
		return xy;
	}
	/**
	 * return calculated gradient of line, m as in y = mx + c
	 * @return gradient
	 */
	public double getGradient() {
		return gradient;
	}
	 /**
     * Get the calculated offset of the line.
     * 
     * @return The offset of the line.
     */
	public double getOffset() {
		return offset;
	}
	  /**
     * Get the coordinates of the line as an array [x1, y1, x2, y2].
     * 
     * @return The coordinates of the line.
     */
	public double [] GetCoords() {
		return coords;
	}
	/** 
	 * calculate the gradient and offset of the line
	 * already ascertained that is not vertical
	 */
	private void calcGradOff() {
		gradient = (double) (coords[3] - coords[1]) / (double) (coords[2] - coords[0]);
		offset = coords[3] - gradient * coords[2];
	}
	/**
	 * calculate y value of line, using pre calculated gradient and offset
	 * @param x
	 * @return y being mx + c
	 */
	public double calcY(double x) {
		return (double) Math.round(gradient*x + offset);
	}
	/**
	 * test if line is vertical (x ccoordinates the same)
	 * @return
	 */
	private boolean isVertical() {
		return coords[2]==coords[0];
	}
	/** 
	 * Is value v between v1 and v2
	 * @param v
	 * @param v1
	 * @param v2
	 * @return result of test
	 */
	private boolean isBetween(double v, double v1, double v2) {
		if (v1>v2)  return v>=v2 && v<=v1;
		else		return v>=v1 && v<=v2;
	}
	/**
	 * is point xyp on the line (ie between its start and end coordinates)
	 * @param xyp	xyp[0] is x; xyp[1] is y
	 * @return
	 */
	public boolean isOnLine(double[] xyp) {
		return isBetween(xyp[0], coords[0], coords[2]) && isBetween(xyp[1], coords[1], coords[3]);
	}
	/**
	 * See if the line intersects with otherLine, return true if so
	 * in which case calcaulate in xyp the point of intersection 
	 * @param otherLine
	 * @return if true
	 */
	public boolean findintersection (Line otherLine) {
	boolean isOne = true;
		if (isVertical()) {			// is vertical line
			if (otherLine.isVertical()) isOne = false;		// two vertical lines dont intersect
			else {
				xy[0] = coords[0];							// intersect at this x
				otherLine.calcGradOff();					// calc grad and offset of other line
				xy[1] = otherLine.calcY(coords[0]);			// so find y value of intersection
			}
		}
		else {
			calcGradOff();									// calc gradient and offset
			if (otherLine.isVertical()) {
				xy = otherLine.getXY();						// get xy associated with otherLine for x
				xy[1] = calcY(xy[0]);						// y value found using this line's grad/off
			}
			else {
				otherLine.calcGradOff();					// calc gradient and offset of other line
				double ograd = otherLine.getGradient();
				if (Math.abs(ograd-gradient)<1.0e-5)		// check not parallel lines
					isOne = false;
				else {										// calculate intersection
					xy[0] = (double) Math.round( (otherLine.getOffset() - offset) / (gradient  - ograd));
					xy[1] = otherLine.calcY(xy[0]);
				}
			}	
		}
		if (isOne) isOne = isOnLine(xy) && otherLine.isOnLine(xy);
				// if found intersection, check that it is on both lines
		return isOne;
	}
	/**
	 * Calculate the distance the line is from the otherLine
	 * @param otherLine
	 * @return
	 */
	public double distintersection (Line otherLine) {
		double ans = 100000000;
		if (findintersection(otherLine)) ans = distance(xy[0], xy[1], coords[0], coords[1]);
		return ans;
	}
	/**
	 * Find the  shortest distance of x,y from line 
	 * @param x
	 * @param y
	 * @return shortest distance
	 */
	public double distanceFrom (double x, double y) {
		double sdist, sdist2;				// used for holding result
					// first calculate in xy point where perpendicular to line meets x,y
		if (coords[0] == coords[2]) {    // vertical line
			xy[0] = coords[0];				// so meet at x coordinate of line
			xy[1] = y;						// and y coordinate is value of y passed
		}
		else if (coords[1] == coords[3]) {	// if horizontal line
			xy[0] = x;						// perpendicular at x 
			xy[1] = coords[1];				// and y is y coord of line
		}
		else {
			calcGradOff();					// calc gradient and offset of line
			double offset2 = y + x / gradient;		// find offset of perpendicular
													// grad of perpendendicular is -1/gradient of this
			xy[0] = (double) Math.round((offset2 - offset)/(gradient + 1.0/gradient));
			xy[1] = (double) Math.round((offset + offset2 * gradient*gradient)/(gradient*gradient + 1.0));
		}
				// now test is intersection is on line
		if (isOnLine(xy)) 
			sdist = distance(x, y, xy[0], xy[1]);			// so answer is dist^2 from x,y to interesction
		else {											// otherwise try distance^2 to end points of line
			sdist = distance(x, y, coords[0], coords[1]);
			sdist2 = distance(x, y, coords[2], coords[3]);
			if (sdist2 < sdist) sdist = sdist2;			// select shorter of two
		}
		return sdist;
	}
	// a function to draw lines from lines in canvas by using show line in canvas
	/*public void drawline(RobotCanvas c) {
	    ArrayList<Line> lines = new ArrayList<>();
	    lines.add(this); // Add the current line to the list
	    c.showLines(lines);
	}
*/
}
