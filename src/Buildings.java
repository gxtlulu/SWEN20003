import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * This class is used to contain all the properties of buildings
 * @author Lucy Gu 994081
 */
public abstract class Buildings extends Objects {
	private static final String BIG_HIGHTLIGHT = "assets/highlight_large.png";
	private Image bigHighlight = new Image(BIG_HIGHTLIGHT);
	
	/**
	 * Initialise building
	 * @param x
	 * @param y
	 * @param imagePath
	 * @throws SlickException
	 */
	public Buildings(double x, double y, String imagePath) throws SlickException {
		super(x,y,imagePath);
	}
	
	/**
	 * Render building on the screen to show its current state
	 */
	public void render() {
		if (super.isSelected()) {
			bigHighlight.drawCentered((float)super.getX(), (float)super.getY());
		}
		super.render();
	}
}
