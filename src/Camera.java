import org.lwjgl.input.Keyboard;

/**
 * This class should be used to restrict the game's view to a subset of the entire world.
 * modified from Project 1 sample solution
 * @author Lucy Gu 994081 
 */
public class Camera{
	private static final double CAMERA_SPEED = 0.4;
	private double x = 0, y = 0;
	private Objects target;
	
	/**
	 * Update camera's state
	 * @param world
	 */
	public void update(World world) {
			// move left
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			x -= CAMERA_SPEED;
			target = null;
			// move right
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			x += CAMERA_SPEED;
			target = null;
			// move up
		} else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			y -= CAMERA_SPEED;
			target = null;
			// move down
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			y += CAMERA_SPEED;
			target = null;
			// follow the selected target as centre
		} else if (target!=null) {
			x = target.getX() - App.WINDOW_WIDTH / 2;
			y = target.getY() - App.WINDOW_HEIGHT / 2;
		}
		// make sure x and y doesn't exceed the range of map
		x = Math.min(x, world.getMapWidth() - App.WINDOW_WIDTH);
		x = Math.max(x, 0);
		y = Math.min(y, world.getMapHeight() - App.WINDOW_HEIGHT);
		y = Math.max(y, 0);
	}
	/**
	 * allow other class to get x of camera
	 * @return camera x
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * allow other class to get y of camera
	 * @return camera y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * allow other class to set the target 
	 * @param target
	 */
	public void setTarget(Objects target) {
		this.target = target;
	}
}
