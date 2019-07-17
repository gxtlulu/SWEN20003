import org.newdawn.slick.SlickException;
/**
 * This class is used to contain all the properties of unobtainium
 * @author gul
 *
 */
public class Unobtainium extends Resources {
	private static final String IMAGE = "assets/resources/unobtainium_mine.png";
	private static final int INITIAL_COUNT = 50;
	
	/**
	 * Initialise unobtainium
	 * @param x
	 * @param y
	 * @throws SlickException
	 */
	public Unobtainium(double x, double y) throws SlickException {
		super(x, y, IMAGE, INITIAL_COUNT);
	}
}
 