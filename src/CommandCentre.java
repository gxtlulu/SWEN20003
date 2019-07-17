import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * This class is used to contain all the properties of command centre
 * @author Lucy Gu 994081
 */
public class CommandCentre extends Buildings {
	private static final String IMAGE = "assets/buildings/command_centre.png";
	private static final int TRAIN_UNITS_TIME = 5;
	private static final int TRAIN_SCOUT_COST = 5;
	private static final int TRAIN_BUILDER_COST = 10;
	private static final int TRAIN_ENGINEER_COST = 20;
	private double time = 0;
	private boolean isTrainScout = false, isTrainBuilder = false, isTrainEngineer = false;
	
	/**
	 * Initialise command centre
	 * @param x
	 * @param y
	 * @throws SlickException
	 */
	public CommandCentre(double x, double y) throws SlickException{
		super(x,y, IMAGE);
	}
	
	@Override
	public void update(World world) throws SlickException {
		// the selected builder is arrived and pressed Key1, then start building
		if(super.isSelected()) {
			if (world.getInput().isKeyPressed(Input.KEY_1)) {isTrainScout = true;}
			else if (world.getInput().isKeyPressed(Input.KEY_2)) {isTrainBuilder = true;} 
			else if (world.getInput().isKeyPressed(Input.KEY_3)) {isTrainEngineer = true;} 
		}
		if (isTrainScout || isTrainBuilder || isTrainEngineer) {
			time += world.getDelta();
		}
		if (time/1000 >= TRAIN_UNITS_TIME) {
			if (isTrainScout) {trainScout(world);}
			if (isTrainBuilder) {trainBuilder(world);}
			if (isTrainEngineer) {trainEngineer(world);}
			time = 0;
			isTrainScout = isTrainBuilder = isTrainEngineer = false;
		}
	}
	
	/**
	 * train scout at the current location
	 * @param world
	 * @throws SlickException
	 */
	public void trainScout(World world) throws SlickException {
		// must have enough metal to train scout
		if (world.getTotalMetal()>=TRAIN_SCOUT_COST) {
			Scout obj = new Scout(super.getX(),super.getY());
			world.setObjectsAdd(obj);
			world.setTotalMetal(world.getTotalMetal()-TRAIN_SCOUT_COST);
		} 
	}
	
	/**
	 * train builder at the current location
	 * @param world
	 * @throws SlickException
	 */
	public void trainBuilder(World world) throws SlickException {
		// must have enough metal for training
		if (world.getTotalMetal()>=TRAIN_BUILDER_COST) {
			Builder obj = new Builder(super.getX(),super.getY());
			world.setObjectsAdd(obj);	
			world.setTotalMetal(world.getTotalMetal()-TRAIN_BUILDER_COST);
		}
	} 
	
	/**
	 * train engineer at the current location
	 * @param world
	 * @throws SlickException
	 */
	public void trainEngineer(World world) throws SlickException {
		// must have enough metal for training
		if (world.getTotalMetal()>=TRAIN_ENGINEER_COST) {
			Engineer obj = new Engineer(super.getX(),super.getY());
			world.setObjectsAdd(obj);
			world.setTotalMetal(world.getTotalMetal()-TRAIN_ENGINEER_COST);
		}
	}
}
