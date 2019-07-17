import org.newdawn.slick.SlickException;
/**
 * This class is used to contain all the properties of resources
 * @author Lucy Gu 994081
 */
public abstract class Resources extends Objects {
	private int count;
	
	/**
	 * Initialise resource
	 * @param x
	 * @param y
	 * @param imagePath
	 * @param count
	 * @throws SlickException
	 */
	public Resources(double x, double y, String imagePath, int count) throws SlickException {
		super(x,y,imagePath);
		this.setCount(count);
	}
	
	/**
	 * allow other class to get the count of resource
	 * @return count
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * allow other class to set the count of resource
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public void update(World world) {
		// as resource has none of the resource left, destroy it
		if (count==0) {
			world.setObjectsRemove(this);
		}
	}
}
