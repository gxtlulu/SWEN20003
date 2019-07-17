import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * This class is used to contain all propertis of pylon
 * @author Lucy Gu 994081
 */
public class Pylon extends Buildings{
	private static final String IMAGE = "assets/buildings/pylon.png";
	private static final String ACTIVE_IMAGE = "assets/buildings/pylon_active.png";
	private static final int MIN_ACTIVATE = 32;
	private boolean isActivated, addRsc;
	
	/**
	 * Initialise pylon
	 * @param x
	 * @param y
	 * @throws SlickException
	 */
	public Pylon(double x, double y) throws SlickException{
		super(x,y,IMAGE);
		this.isActivated = false;
		this.addRsc = false;
	}
	 
	@Override
	public void update(World world) throws SlickException {
		if (!isActivated) {
			// as any units pass pylon, activate it
			for (Object obj: world.getObjects()) {
				if (obj instanceof Units && World.distance(
						((Units) obj).getX(), ((Units) obj).getY(), super.getX(), super.getY())<= MIN_ACTIVATE) {
					isActivated = true;
				}
			}
		} else if (isActivated && !addRsc) {
			// add one to amount of metal carry for each engineer
			for (Object obj: world.getObjects()) {
				if (obj instanceof Engineer) {
					((Engineer) obj).setMaxCarry((((Engineer) obj).maxCarry()+1));
				}
			}
			addRsc = true;
			super.setImage(new Image(ACTIVE_IMAGE));
		}
	}
}
