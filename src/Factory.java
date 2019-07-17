import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * This class is used to contain all properties of factory
 * @author Lucy Gu 994081
 */
public class Factory extends Buildings {
	private static final String IMAGE = "assets/buildings/factory.png";
	private static final int TRAIN_TRUCK_COST = 150;
	private static final int TRAIN_TRUCK_TIME = 5;
	private double time = 0;
	private boolean isTrainTruck = false;
	
	/**
	 * Initialise factory
	 * @param x
	 * @param y
	 * @throws SlickException
	 */
	public Factory(double x, double y) throws SlickException {
		super(x,y,IMAGE);

	}
	
	@Override
	public void update(World world) throws SlickException {
		if (super.isSelected() && world.getInput().isKeyPressed(Input.KEY_1)) {
			isTrainTruck = true;
		}
		// timing while training
		if (isTrainTruck) {
			time += world.getDelta();
			// as enough time spent, train a truck
			if (time/1000 >=TRAIN_TRUCK_TIME) {
				trainTruck(world);
				time = 0;
				isTrainTruck = false;
			}
		}
	}
	
	/**
	 * train truck at the current location
	 * @param world
	 * @throws SlickException
	 */
	public void trainTruck(World world) throws SlickException {
		// make sure there is enough metal for training 
		if (world.getTotalMetal() >= TRAIN_TRUCK_COST) {
			Truck obj = new Truck(super.getX(), super.getY());
			world.setObjectsAdd(obj);
			world.setTotalMetal(world.getTotalMetal()-TRAIN_TRUCK_COST);
		}
	}

}
