import org.newdawn.slick.SlickException;
/**
 * This class is used to contain all the properties of metal
 * @author Lucy Gu 994081
 */
public class Metal extends Resources {
	private static final String IMAGE = "assets/resources/metal_mine.png";
	private static final int INITIAL_COUNT = 500;
	
	/**
	 * Initialise metal
	 * @param x
	 * @param y
	 * @throws SlickException
	 */
	public Metal(double x, double y) throws SlickException {
		super(x, y, IMAGE, INITIAL_COUNT);
	}
}
