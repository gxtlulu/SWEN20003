import org.newdawn.slick.SlickException;
/**
 * This class should be used to control builders
 * @author Lucy Gu 994081
 *
 */
public class Scout extends Units {
	private static final String IMAGE = "assets/units/scout.png";
	private static final double SPEED = 0.3;
	
	/**
	 * Initialise scout
	 * @param x
	 * @param y
	 * @throws SlickException
	 */
	public Scout(double x, double y) throws SlickException {
		super(x,y,IMAGE);
	}
	
	@Override
	public void update(World world) {
		super.update(world, SPEED);
	}

} 

