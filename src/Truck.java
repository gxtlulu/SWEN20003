import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * This class is used to control trucks
 * @author Lucy Gu 994081
 */
public class Truck extends Units {
	private static final String IMAGE = "assets/units/truck.png";
	private static final int BUILD_CC_TIME = 15;
	private static final double SPEED = 0.25;
	private boolean isBuilding;
	private double time = 0;
	
	/**
	 * Initialise truck
	 * @param x
	 * @param y
	 * @throws SlickException
	 */
	public Truck(double x, double y) throws SlickException {
		super(x, y, IMAGE);
	}

	@Override
	public void update(World world) throws SlickException {
		// the selected builder is arrived and pressed Key1, then start building
		if(super.isSelected() && super.isStopped() && world.getInput().isKeyPressed(Input.KEY_1)) {
			isBuilding = true;
		} 
		// timing while building
		if (isBuilding) {
			time += world.getDelta();
			// if enough time spent, build the command centre
			if (time/1000 >= BUILD_CC_TIME) {
				buildCmdCentre(world);
				isBuilding = false;
				time = 0;
			} 
		} else {
			super.update(world, SPEED);
		}
	}
	
	/**
	 * Build command centre at the current location
	 * @param world
	 * @throws SlickException
	 */
	public void buildCmdCentre(World world) throws SlickException{
		// make sure the current position is not occupied
		if (world.notOccupied(super.getX(), super.getY())) {
			CommandCentre obj = new CommandCentre(super.getX(),super.getY());
			world.setObjectsAdd(obj);
			// as truck finishes build command centre, destroy it
			world.setObjectsRemove(this);
		}
	}
}
