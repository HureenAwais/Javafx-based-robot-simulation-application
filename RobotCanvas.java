
/**
 * The `RobotCanvas` class provides a canvas for drawing graphical elements and shapes in a JavaFX application.
 * It includes methods for drawing circles, images, text, and lines with specified properties such as color, size, and position.
 * This class is designed to work with a GraphicsContext and serves as a utility for visualizing elements on a canvas.
 * 
 * @author [Your Name]
 */

package RobotGUI;

import java.util.ArrayList;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;
/**
* The `RobotCanvas` class encapsulates a GraphicsContext and provides methods for drawing various elements on the canvas.
*/

public class RobotCanvas {
	int xCanvasSize = 512;				// constants for relevant sizes, default values set
	int yCanvasSize = 512;
    GraphicsContext gc;
	/**
     * Constructor for the RobotCanvas class.
     *
     * @param g   The GraphicsContext associated with the canvas.
     * @param xcs The width of the canvas.
     * @param ycs The height of the canvas.
     */
    public RobotCanvas(GraphicsContext g, int xcs, int ycs) {
    	gc = g;
    	xCanvasSize = xcs;
    	yCanvasSize = ycs;
    }
    /**
     * Get the width of the canvas.
     *
     * @return The width of the canvas.
     */
    public int getXCanvasSize() {
    	return xCanvasSize;
    }
    /**
     * Get the height of the canvas.
     *
     * @return The height of the canvas.
     */
    public int getYCanvasSize() {
    	return yCanvasSize;
    }
    /**
     * clear the canvas
     */
    public void clearCanvas() {
		gc.clearRect(0,  0,  xCanvasSize,  yCanvasSize);		// clear canvas
    }
	/**
     * drawImage ... draws object defined by given image at position and size
     * @param i		image
     * @param x		xposition	in range 0..1
     * @param y
     * @param sz	size
     */
	public void drawIt(Image i, double x, double y, double sz) {
			// to draw centred at x,y, give top left position and x,y size
			// sizes/position in range 0.. canvassize 
		gc.drawImage(i, xCanvasSize * (x - sz/2), yCanvasSize*(y - sz/2), xCanvasSize*sz, yCanvasSize*sz);
	}

	 /**
     * Convert a char representation of color to an actual Color object.
     *
     * @param c The char representation of color.
     * @return The Color object corresponding to the char.
     */
	Color colFromChar (char c){
		Color ans = Color.BLACK;
		switch (c) {
		case 'y' :	ans = Color.YELLOW;
					break;
		case 'w' :	ans = Color.WHITE;
					break;
		case 'r' :	ans = Color.RED;
					break;
		case 'g' :	ans = Color.GREEN;
					break;
		case 'b' :	ans = Color.BLUE;
					break;
		case 'o' :	ans = Color.ORANGE;
					break;
		case 'l' : ans=Color.BLACK;
		break;
		}
		return ans;
	}
	
	/**
     * Set the fill color to the specified color.
     *
     * @param c The color to set as the fill color.
     */
	public void setFillColour (Color c) {
		gc.setFill(c);
	}
	  /**
     * Show a filled circle on the canvas at the specified position with the specified radius and color.
     *
     * @param x   The x-coordinate of the circle.
     * @param y   The y-coordinate of the circle.
     * @param rad The radius of the circle.
     * @param col The char representation of color.
     */
	public void showCircle(double x, double y, double rad, char col) {
	 	setFillColour(colFromChar(col));			// set the fill colour
	 	showCircle(x, y, rad);						// show the circle
	}

	/**
	 * show the robot in the current colour at x,y size rad
	 * @param x
	 * @param y
	 * @param rad
	 */
	public void showCircle(double x, double y, double rad) {
		gc.fillArc(x-rad, y-rad, rad*2, rad*2, 0, 360, ArcType.ROUND);	// fill circle
	}
	   /**
     * Show text on the canvas at the specified position with the specified content.
     *
     * @param x The x-coordinate of the text.
     * @param y The y-coordinate of the text.
     * @param s The string content of the text.
     */
	public void showText (double x, double y, String s) {
		gc.setTextAlign(TextAlignment.CENTER);							// set horizontal alignment
		gc.setTextBaseline(VPos.CENTER);								// vertical
		gc.setFill(Color.WHITE);										// colour in white
		gc.fillText(s, x, y);											// print score as text
	}

	/**
	 * Show Int .. by writing int i at position x,y
	 * @param x
	 * @param y
	 * @param i
	 */
	public void showInt (double x, double y, int i) {
		showText (x, y, Integer.toString(i));
	}
	/**
     * Show whisker lines on the canvas.
     *
     * @param l The Line object representing a whisker.
     */
	public void showWhiskers(Line l) {
		double [] Coords = l.GetCoords();
		  gc.setLineWidth(3);
	        gc.strokeLine(Coords[0], Coords[1], Coords[2],Coords[3]);
	     
	}
	  /**
     * Show lines representing the boundaries of the arena on the canvas.
     *
     * @param arenaLines The ArrayList of Line objects representing the boundaries of the arena.
     */
	public void showLines(ArrayList<Line> arenaLines) {
		for (Line l : arenaLines) {
		double [] Coords = l.GetCoords();
		  gc.setLineWidth(3);
	        gc.strokeLine(Coords[0], Coords[1], Coords[2],Coords[3]);
		}
	     
	}
	
	    /**
	     * Draw a line from (startX, startY) to (endX, endY) with the specified width and color
	     * @param startX
	     * @param startY
	     * @param endX
	     * @param endY
	     * @param black 
	     * @param i 
	     * @param d 
	     * @param black
	     */
	    public void strokeLine(double startX, double startY, double endX, double endY, int i, char col) {
	      
	        gc.setLineWidth(i);
	        gc.setStroke(colFromChar(col));
	        gc.strokeLine(startX, startY, endX, endY);
	    }

		

	

}

	
	

