/**
 * 
 */
package RobotGUI;

/**
 * @authorhureen
 * The Target Ball which you are aiming at
 */
public class target extends robot {
	private static final long serialVersionUID = 1L;
	 private int score;
	/**
	 * 
	 */
	public target() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ix
	 * @param iy
	 * @param ir
	 */
	public target(double ix, double iy, double ir) {
		super(ix, iy, ir);
		score = 5;
		col = 'g';
	}

	/** 
	 * checkBall in arena 
	 * @param b BallArena
	 */
	@Override
	protected void checkRobot(RobotArena b) {
		if (b.checkHit(this)) score--;			// if been hit, then increase score
		if (score == 0)  b.dltRobot(x, y);
		
		
	}
	/**
	 * draw Ball and display score
	 */
	public void drawRobot(RobotCanvas mc) {
		super.drawRobot(mc);
		mc.showInt(x, y, score);
	}

	/**
	 * adjustBall
	 * for moving the ball - not needed here
	 */
	@Override
	protected void adjustRobot() {
				// nothing to do at the moment...
	}
	/**
	 * return string defining ball ... here as target
	 */
	protected String getStrType() {
		return "Charging point";
	}	
}