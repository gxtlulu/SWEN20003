import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * This class should be used to control builders
 * @author Lucy Gu 994081
 */
public class Builder extends Units {
	private static final String IMAGE = "assets/units/builder.png";
	private static final int BUILD_COST = 100;
	private static final int BUILD_TIME = 10;
	private static final double SPEED = 0.1;
	private boolean isBuilding = false;
	private double time = 0;
	
	/**
	 * Initialise builder
	 * @param x
	 * @param y
	 * @throws SlickException
	 */
	public Builder(double x, double y) throws SlickException {
		super(x, y, IMAGE);
	}
	
	@Override
	public void update(World world) throws SlickException{
		// the selected builder is arrived and pressed Key1, then start building
		if(super.isSelected() && super.isStopped() && world.getInput().isKeyPressed(Input.KEY_1)) {
			isBuilding = true;
		}
		// timing while building
		if (isBuilding) {
			time += world.getDelta();
			// as the build time reaches, build factory
			if (time/1000 >= BUILD_TIME) {
				buildFactory(world);
				isBuilding = false;
				time = 0;
			} 
		} else {
			super.update(world, SPEED);
		}
	}
	
	/**
	 * build the factory in the current position
	 * @param world
	 * @throws SlickException
	 */
	public void buildFactory(World world) throws SlickException{
		// check having enough metal and the place is not occupied by other buildings
		if (world.getTotalMetal() >= BUILD_COST && world.notOccupied(super.getX(), super.getY())) {
			Factory obj = new Factory(super.getX(),super.getY());
			world.setObjectsAdd(obj);
			world.setTotalMetal(world.getTotalMetal()- BUILD_COST);
		}
	}
}
