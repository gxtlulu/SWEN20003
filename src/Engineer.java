import org.newdawn.slick.SlickException;
/**
 * This class should be used to control engineers
 * @author Lucy Gu 994081
 */
public class Engineer extends Units {
	private static final String IMAGE = "assets/units/engineer.png";
	private static final int MINE_TIME = 5;
	private static final double SPEED = 0.1;
	private int maxCarry = 2, time = 0, numCarry = maxCarry;
	private boolean rscDropped = true, isMining = false;
	private Resources rscNearby;
	private CommandCentre nearstCmdCentre;
	
	/**
	 * Initialise engineer
	 * @param x
	 * @param y
	 * @throws SlickException
	 */
	public Engineer(double x, double y) throws SlickException {
		super(x, y, IMAGE);
	}
 
	@Override
	public void update(World world) {
		// as the selected engineer is stopped and found a mine nearby, start mining
		if (super.isStopped() && findMine(world)) {
			isMining = true;
		}
		// timing while mining
		if (isMining) {
			time += world.getDelta();
			// after spending enough time to mine, then carry resources to command centre
			if (time/1000 >= MINE_TIME) {
				// find nearst command centre, and set that as target position
				findNearstCmdCentre(world);
				super.setTargetX(nearstCmdCentre.getX());
				super.setTargetY(nearstCmdCentre.getY());
				// if there is enough mine, carry the maximum amount 
				// otherwise, carry the rest of mine
				if (rscNearby.getCount()<maxCarry) {
					numCarry = rscNearby.getCount();
				} else {
					numCarry = maxCarry;
				}
				rscNearby.setCount(rscNearby.getCount()-numCarry);
				rscDropped = isMining = false;
				time = 0;
			}
		} else if (super.isStopped() && !rscDropped && 
				World.distance(super.getX(), super.getY(), nearstCmdCentre.getX(), nearstCmdCentre.getY())<=SPEED) {
			rscDropped = true;
			super.setTargetX(rscNearby.getX());
			super.setTargetY(rscNearby.getY());
			// add the mine to the total
			if (rscNearby instanceof Metal) {
				world.setTotalMetal(world.getTotalMetal() + numCarry);
			} else {
				world.setTotalUnobtainium(world.getTotalUnobtainium() + numCarry);
			}
		} 
		super.update(world, SPEED);
	}

	/**
	 * find the nearest command centre
	 * @param world
	 */
	public void findNearstCmdCentre(World world) {
		int index = -1;
		double minDistance = Integer.MAX_VALUE;
		// check every command centre and choose the one with smallest distance
		for (Objects obj: world.getObjects()) {
			if (obj instanceof CommandCentre && World.distance(obj.getX(), obj.getY(), 
					rscNearby.getX(), rscNearby.getY()) < minDistance) {
				minDistance = World.distance(obj.getX(), obj.getY(), rscNearby.getX(), rscNearby.getY());
				index = world.getObjects().indexOf(obj);
			}
		}
		nearstCmdCentre = (CommandCentre) world.getObjects().get(index); 
	}

	/**
	 * find mine near the current position
	 * @param world
	 * @return true if there is mine nearby
	 */
	// return true if find mine nearby
	public boolean findMine(World world) {
		// iterate through all the objects and check the distance between each command centre and engineer
		for (Objects obj: world.getObjects()) {
			// the mine should be near the engineer and has enough resources
			if (obj instanceof Resources && World.distance(super.getX(), super.getY(),
					((Resources) obj).getX(), ((Resources) obj).getY()) <= World.getMinSelect()
					&& ((Resources) obj).getCount()>0) {
				rscNearby = (Resources) obj;
				return true;
			}
		}
		return false;
	}

	/**
	 * allow other class to get the maximum amount of mine engineer can carry
	 * @return maxCarry
	 */
	public int maxCarry() {
		return maxCarry;
	}
	
	/**
	 * allow other class to set the maximum amount of mine engineer can carry
	 * @param maxCarry
	 */
	public void setMaxCarry(int maxCarry) {
		this.maxCarry = maxCarry;
	}
}
