package RobotGUI;

import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;
import java.util.Random;
import RobotGUI.RobotArena;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * The RobotArena class represents an arena containing robots.
 * @author hureen
 */
public class RobotArena implements Serializable {
	
private static final long serialVersionUID = 1L;
private ArrayList<robot> manyRobots;
private double Xmax, Ymax, radius;// size of arena
private ArrayList<Line>arenaLines;
Random randomGenerator;

/**
 * Constructs an arena with default size.
 */

RobotArena() {
	this(500, 400);			// default size
} 
/**
 * Constructs an arena with specified size.
 * @param i The size in the x-direction.
 * @param j The size in the y-direction.
 */
public RobotArena(double i, double j) {
	
	Xmax = i;
	Ymax = j;
	radius=15;
	randomGenerator= new Random();
	manyRobots = new ArrayList<robot>();// list of all robots, initially empty
	
	manyRobots.add(new gameRobot(Xmax/2, Ymax/3, 15, 90, 3));// add game robot
	manyRobots.add(new obstacles(Xmax/5, Ymax/2, 15));		// add blocker
	manyRobots.add(new obstacles(Xmax/2, Ymax/2, 15));	// add blocker
	manyRobots.add(new whiskerRobot(Xmax/2, Ymax/4, 15, 20, 0.8, 25));	// add whisker
	manyRobots.add(new beamClass(Xmax/4, Ymax-45, 15, 150, 0.8, 25));	// add beam Robot
	manyRobots.add(new avoidRobot(Xmax/4, Ymax/10, 15, 90, 3));	// add avoid Robot
	manyRobots.add(new target(Xmax/2, 20, 15));	
	manyRobots.add(new target(Xmax/2, Ymax-20, 15));	
	//ADDING BOUNDRIES around arena
	arenaLines = new ArrayList<Line>();
	arenaLines.add(new Line(0,Ymax,0,0));// add line on leftside
	arenaLines.add(new Line(0,0,0,0));// add line 
	arenaLines.add(new Line(0,0,Xmax,0));// add line on BOTTOM
	arenaLines.add(new Line(Xmax,0,Xmax,Ymax));// add line on rightside
	arenaLines.add(new Line(0,0,0,0));// add line 
	arenaLines.add(new Line(Xmax,Ymax,0,Ymax));// add line on top
	//arenaLines.add(new Line(0,0,0,0));// add line 
	
	//arenaLines.add(new Line(0,0,0,0));// add line
}
/**
 * Shows arena lines according to the coordinates of arena lines.
 * @param c The RobotCanvas interface.
 */
public void drawline(RobotCanvas c) { 
	c.showLines(arenaLines); // draw arena lines
}



/**
 * Returns arena size in the x direction.
 * @return The size in the x-direction.
 */
public double getXsize() {
	return Xmax;

}

/**
 * Returns arena size in the y direction.
 * @return The size in the y-direction.
 */
public double  getYsize() {
	return Ymax;
}
/** 
 * Check angle of ball ... if hitting wall, rebound; if hitting ball, change angle
 * @param x				ball x position
 * @param y				y
 * @param rad			radius
 * @param ang			current angle
 * @param notID			identify of ball not to be checked
 * @return				new angle 
 */

public double CheckRobotAngle(double x, double y, double rad, double ang, int notID) {
	double ans = ang;
	if (x < rad || x > Xmax - rad) ans =  180-ang;
		// if ball hit (tried to go through) left or right walls, set mirror angle, being 180-angle
	if (y < rad || y > Ymax - rad) ans = -ang ;
		// if try to go off top or bottom, set mirror angle
	
	for (robot r : manyRobots) 
		if (r.getID() != notID && r.hitting(x, y, rad)) ans = 180*Math.atan2(y-r.getY(), x-r.getX())/Math.PI;
			// check all balls except one with given id
			// if hitting, return angle between the other ball and this one.
	
	return ans;		// return the angle
} 


/**
 * Draws all robots in the arena.
 * @param mc The RobotCanvas interface.
 */
public void drawArena(RobotCanvas mc) {
	// Iterate through all robots in the arena
	for (robot r : manyRobots) r.drawRobot(mc);		// draw all robots
	
	drawline(mc); // draw line from drawline function in arena
}

/**
 * Checks for intersections between robot whiskers and other robots or arena boundaries.
 *
 * @param RW      The right whisker line.
 * @param LW      The left whisker line.
 * @param angle   The current angle of the robot.
 * @param notID   The identification of the robot to be ignored in collision checks.
 * @param x       The x-coordinate of the robot.
 * @param y       The y-coordinate of the robot.
 * @return The updated angle based on detected collisions or intersections.
 */
double checkIntersection(Line RW, Line LW, double angle, int notID, double x, double y) {
	
    if (x < radius || x > Xmax - radius || y < radius || y > Ymax - radius) {
        angle += 10; 
        System.out.println(" Turning +10 degrees.");
    } 
	
    // Check if whisker detects other robot or robot is outside the boundaries
    for (robot r : manyRobots) {
        if (r.getID() != notID && RW.distanceFrom(r.getX(), r.getY()) < r.getRad()) {
            System.out.println("Collision with robot detected. Turning +90 degrees.");
            return angle + 90;
        }
        if (r.getID() != notID && LW.distanceFrom(r.getX(), r.getY()) < r.getRad()) {
            System.out.println("Collision with robot detected. Turning +90 degrees.");
            return angle + 90;
        }
        
    }

    // Check if whisker detects walls
    for (Line l : arenaLines) {
        if (RW.findintersection(l)) {
            if (RW.distintersection(l) < 30) {
                System.out.println("Right whiskers intersect arena. Turning -40 degrees.");
                return angle - 40;
            }
        }

        if (LW.findintersection(l)) {
            if (LW.distintersection(l) < 30) {
                System.out.println("Left whiskers intersect arena. Turning +60 degrees.");
                return angle + 40;
            }
        }

        if (RW.findintersection(l) && LW.findintersection(l)) {
            // If both whiskers intersect arena
            if (RW.distintersection(l) < 20 && LW.distintersection(l) < 20) {
                System.out.println("Both whiskers intersect arena. Turning +45 degrees.");
                return angle + 45;
            }
        }
    }

    return angle;
}

/**
 * check if the target robot has been hit by gameRobot
 * @param target	the target robot
 * @return 	true if hit
 */
public boolean checkHit(robot target) {
	boolean ans = false;
	for (robot r : manyRobots)
		if (r instanceof gameRobot  && r.hitting(target)) ans = true;
			// try all balls, if GameBall, check if hitting the target
	return ans;
}
/**
 * check if the gameRobot robot has been hit by whiskerRobot
 * @param gameRobot	the game robot
 * @return 	true if hit
 */
public boolean checkHitAvoid(robot gameRobot) {
	boolean ans = false;
	for (robot r : manyRobots) {
		if (r instanceof whiskerRobot && r.hitting(gameRobot)) ans = true;	
		if (r instanceof beamClass && r.hitting(gameRobot)) ans = true;	
		
	}
	return ans;
}
/**
 * check if the gameRobot robot has been hit by targetball
 * @param gameRobot	the game robot
 * @return 	true if hit
 */
public boolean checkHitCharge(robot gameRobot) {
	boolean ans = false;
	for (robot r : manyRobots)
		if (r instanceof target  && r.hitting(gameRobot)) ans = true;
			// try all balls, if GameBall, check if hitting the target
	return ans;
}

/**
 * Checks if any robots need to change angle.
 */
public void checkRobots() {
	for (robot r : manyRobots) r.checkRobot(this);	// check all balls
}
/**
 * adjust all robots.. move any moving ones
 */
public void adjustRobot() {
	for (robot r : manyRobots) r.adjustRobot();
}

/**
 * Sets the XY coordinates of robots based on the provided values.
 * @param x The x-coordinate.
 * @param y The y-coordinate.
 */

public void setXY(double x, double y)
{
	for(robot r : manyRobots)
	{
		if(r.hitting(x, y, radius))
		{
			if(r instanceof whiskerRobot)
				r.updateXY(x,y);
			if(r instanceof gameRobot)
				r.updateXY(x, y);
			if(r instanceof beamClass)
				r.updateXY(x, y);
			if(r instanceof obstacles)
				r.updateXY(x, y);
			}
			
	}
}

/**
 * Deletes the robot based on the specified coordinates.
 * @param x The x-coordinate.
 * @param y The y-coordinate.
 */
/*
public void dltRobot(double x, double y) {
	Iterator<robot>  Index = manyRobots.iterator();
	while ( Index.hasNext()) {
		robot r = (robot) Index.next();
		if (r.hitting(x, y, 2))
			 Index.remove();
	}
}
*/
/**
 /**
 * Deletes the robot based on the specified coordinates.
 * @param x The x-coordinate.
 * @param y The y-coordinate.
 */
public void dltRobot(double x, double y) {
    List<robot> robotsToRemove = new ArrayList<>();

    for (robot r : manyRobots) {
        if (r.hitting(x, y, 2)) {
            robotsToRemove.add(r);
        }
    }

    manyRobots.removeAll(robotsToRemove);
}

/* * Returns a list of strings defining each robot.
 * @return The list of strings.
 */
public ArrayList<String> describeAll() {
	ArrayList<String> ans = new ArrayList<String>();		// set up empty arraylist
	for (robot r: manyRobots) ans.add(r.toString());// add string defining each robot
	
	return ans; // return string list
}







/**
 * Adds obstacles to the arena at random positions.
 */

public void addObstacles() {
	
double valX, valY;  ;


	do {
	 valX = randomGenerator.nextDouble(Xmax-60); // Robot at random X axis
	 valY = randomGenerator.nextDouble(Ymax- 60); // robot at random Y axis
	 valX= valX + 30;
	 valY= valY + 30;
	}
	while (getRobotAT(valX, valY)!= null); {
		//created with the generated position, a random angle, and a speed of 3.
		manyRobots.add(new obstacles(valX, valY, 15));
	
	}
	

}
/**
 * Adds gameRobot to the arena at random positions.
 */
public void addGameRobot() {
	
double valX, valY;  ;


	do {
	 valX = randomGenerator.nextDouble(Xmax-60); // Robot at random X axis
	 valY = randomGenerator.nextDouble(Ymax- 60); // robot at random Y axis
	 valX= valX + 30;
	 valY= valY + 30;
	}
	while (getRobotAT(valX, valY)!= null); {
		//created with the generated position, a random angle, and a speed of 2.
		manyRobots.add(new gameRobot(valX, valY, 15, 180, 2));
	
	}
	

}
/**
 * Adds ring robot to the arena at random positions.
 */

public void addavoidRobot() {
	
double valX, valY;  ;


	do {
	 valX = randomGenerator.nextDouble(Xmax-60); // Robot at random X axis
	 valY = randomGenerator.nextDouble(Ymax- 60); // robot at random Y axis
	 valX= valX + 30;
	 valY= valY + 30;
	}
	while (getRobotAT(valX, valY)!= null); {
		//created with the generated position, a random angle, and a speed of 2.
		manyRobots.add(new avoidRobot(valX, valY, 15, 90, 2 ));
	
	}
	

}

/**
 * Adds beam robots to the arena at random positions.
 */
public void addBeams() {
	
double valX, valY;  ;


	do {
	 valX = randomGenerator.nextDouble(Xmax-60); // Robot at random X axis
	 valY = randomGenerator.nextDouble(Ymax- 60); // robot at random Y axis
	 valX= valX + 30;
	 valY= valY + 30;
	}
	while (getRobotAT(valX, valY)!= null); {
		//created with the generated position, a random angle, and a speed of 3.
		manyRobots.add(new beamClass(valX, valY, 15, 45, 0.8, 30));
	
	}
	

}

/**
 * Adds whisker robots to the arena at random positions.
 */
public void addRobot() {
	
double valX, valY;  ;


	do {
	 valX = randomGenerator.nextDouble(Xmax-60); // Robot at random X axis
	 valY = randomGenerator.nextDouble(Ymax- 60); // robot at random Y axis
	 valX= valX + 30;
	 valY= valY + 30;
	}
	while (getRobotAT(valX, valY)!= null); {
		//created with the generated position, a random angle, and a speed of 3.
		manyRobots.add(new whiskerRobot(valX, valY, 15, randomGenerator.nextDouble(361), 0.8, 24));
	
	}
	

}

/**
 * Gets the robot at the specified coordinates.
 * @param x The x-coordinate.
 * @param y The y-coordinate.
 * @return The robot at the coordinates or null if not found.
 */
public robot getRobotAT(double  x, double y) {
	for (robot a : manyRobots) 
	{
		if (a.isHere(x,y))
			return a;
	}

	return null;
		
	
}

/**
 * Displays robots on the provided RobotCanvas.
 * @param c The RobotCanvas interface.
 */

public void showRobots(RobotCanvas c) {
	for(robot r : manyRobots)
		r.drawRobot(c);
	
}


/**
 * Loads the arena configuration from the given file.
 * @param fname The filename.
 * @return 0 if successful.
 */
public int loadFile (String fname) {
	int status = 0;
	manyRobots.clear();
      try {
    	  FileInputStream fileInputStream = new FileInputStream(fname);					// set up input stream
    	  ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);		// and file
    	  RobotArena A = (RobotArena) inputStream.readObject();							// read whole object
    	  this.Xmax = A.Xmax;															// load into arena
    	  this.Ymax = A.Ymax;
    	  this.manyRobots = A.manyRobots;
    	  inputStream.close();															// close...
    	  fileInputStream.close();
      } catch(IOException e) {
    	  e.printStackTrace();
    	  status = 1;
      }
      catch(ClassNotFoundException c) {
    	  c.printStackTrace();
    	  status = 2; 
      }
      return status;
}

/**
 * Saves the arena configuration to the given file.
 * @param fname The filename.
 * @return 0 if successful.
 */
public int saveFile (String fname) {
	int status = 0;
      try {
    	  FileOutputStream fileOutputStream = new FileOutputStream(fname);				// setup
    	  ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);		
    	  outputStream.writeObject(this);												// write arena and contents
    	  outputStream.close();															// close
    	  fileOutputStream.close();
      } catch(IOException e) {
    	  e.printStackTrace();
    	  status = 1;
      }
      return status;
}	
/**
 * Gets a string representation of the game robots in the arena.
 * @return The string representation of game robots.
 */
public String getGameRobots() {
    StringBuilder gameRobotsString = new StringBuilder();

    for (robot r : manyRobots) {
        if (r instanceof gameRobot) {
            gameRobotsString.append(r.toString()).append("\n");
        }
    }

    return gameRobotsString.toString();
}


/**
 * Main method for testing purposes.
 * @param args The command-line arguments.
 */

public static void main(String[] args) {
	
	
	 RobotArena robotArena = new RobotArena();
     
     // Call the describeAll method to get the ArrayList<String>
     ArrayList<String> robotDescriptions = robotArena.describeAll();
     
     // Print each robot description
     for (String d : robotDescriptions) {
         System.out.println(d);
     }
 }



	
}


