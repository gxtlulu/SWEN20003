import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/** 
 * This class should be used for all the objects in the world
 * include their position, image and actions
 * @author Lucy Gu 994081
 */
public abstract class Objects {
	private double x, y; 
    private Image image;
    private boolean selected = false;
    
    /**
     * Initialise objects
     * @param x
     * @param y
     * @param imagePath
     * @throws SlickException
     */
	public Objects (double x, double y, String imagePath) throws SlickException{
		this.x = x;
		this.y = y;
		this.image = new Image(imagePath);
	} 
	
	public abstract void update(World world) throws SlickException;
	 
	/**
	 * Render objects, reflect its current state
	 */
	public void render() {
		image.drawCentered((float)x, (float)y);
	}
	
	/**
	 * allow other class to get x position
	 * @return x
	 */
	public double getX() {
		return x; 
	}
	
	/**
	 * allow other class to set x position
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * allow other class to get y position
	 * @return y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * allow other class to set y position
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * allow other class to set image
	 * @param image
	 */
	public void setImage(Image image) {
		this.image = image;
	}
	
	/**
	 * allow other class to get the object is selected or not
	 * @return true if selected
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * allow other class to set whether or not the object is selected
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
