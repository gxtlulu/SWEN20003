import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * The class should be used to contain all properties and actions of units
 * @author Lucy Gu 994081
 */
public abstract class Units extends Objects {

	private static final String SMALL_HIGHLIGHT = "assets/highlight.png";
	private double targetX = super.getX(), targetY = super.getY();
	private Image smallHighlight = new Image(SMALL_HIGHLIGHT);
	private boolean stopped = true;
	
	/**
	 * Initialise units
	 * @param x
	 * @param y
	 * @param imagePath
	 * @throws SlickException
	 */
	public Units(double x, double y, String imagePath) throws SlickException {
		super(x,y,imagePath);
	}  
	
	/**
	 * update unit's current state
	 * @param world
	 * @param speed
	 */
	public void update(World world, double speed) {
		Input input = world.getInput();
		
		// for selected unit, right-click to set target position
		if (super.isSelected() && input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			targetX = input.getMouseX()+world.getCamera().getX();
			targetY = input.getMouseY()+world.getCamera().getY();
			
		}
		move(world, speed);
		
	}
	
	@Override
	public void render() {
		// if selected, draw highlight under
		if (super.isSelected()) {
			smallHighlight.drawCentered((float)super.getX(), (float)super.getY());
		}
		super.render();
	} 
	
	/** move the units to (targetX, targetY)
	 * @author Project 1 sample solution
	 * @param world
	 * @param speed
	 */
	public void move(World world, double speed) {
		// If we're close to our target, reset to our current position
		if (World.distance(super.getX(), super.getY(), targetX, targetY) <= speed) {
			resetTarget();
		} else {
			// Calculate the appropriate x and y distances
			double theta = Math.atan2(targetY - super.getY(), targetX - super.getX());
			double dx = (double)Math.cos(theta) * world.getDelta() * speed;
			double dy = (double)Math.sin(theta) * world.getDelta() * speed;
			// Check the tile is free before moving; otherwise, we stop moving
			if (world.isPositionFree(super.getX() + dx, super.getY() + dy)) {
				super.setX(super.getX()+dx);
				super.setY(super.getY()+dy);
				stopped = false;
			} else {
				resetTarget();
			}
		}
	}
	
	private void resetTarget() {
		targetX = super.getX();
		targetY = super.getY();
		stopped = true;
	}
	
	/**
	 * allow other class to get whether or not the unit is stopped
	 * @return true if stopped
	 */
	public boolean isStopped() {
		return stopped;
	}
	
	/**
	 * allow other class to set x of target position
	 * @param targetX
	 */
	public void setTargetX(double targetX) {
		this.targetX = targetX;
	}
	
	/**
	 * allow other class to set y of target position
	 * @param targetY
	 */
	public void setTargetY(double targetY) {
		this.targetY = targetY;
	}
}
