import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * This class should be used to contain all the different objects in your game world, and schedule their interactions.
 * @author Lucy Gu 994081
 */ 
public class World {
	private static final String MAP_PATH = "assets/main.tmx";
	private static final String SOLID_PROPERTY = "solid";
	private static final String OCCUPIED_PROPERTY = "occupied";
	private static final String CSV_PATH = "assets/objects.csv";
	private static final int MIN_SELECT = 32;
	private TiledMap map;
	private Camera camera;
	
	private Input lastInput;
	private int lastDelta; 
	// use to store every objects in the world
	private ArrayList<Objects> objects = new ArrayList<Objects>();
	
	// total resources
	private int totalMetal = 300;
	private int totalUnobtainium = 300;
	 
	// the object needs to be added to the world
	private Objects objectsAdd; 
	private Objects objectsRemove;
	
	/**
	 * Initialise world
	 * @throws SlickException
	 */
	public World() throws SlickException {
		map = new TiledMap(MAP_PATH);
		camera = new Camera();
		readObjects(CSV_PATH);
	}
	
	/**
	 * update the whole world's state.
	 * @param input Handle keyboard and mouse.
	 * @param delta Time passed since last frame in milliseconds.
	 * @throws SlickException
	 */
	public void update(Input input, int delta) throws SlickException {
		objectsAdd = null;
		lastInput = input;
		lastDelta = delta;
		objectsRemove = null;
		
		// as the mouse left button clicked, get x and y
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			double selectX = input.getMouseX() + camera.getX();
			double selectY = input.getMouseY() + camera.getY();
			// select the object where mouse clicked
			select(input, selectX, selectY);
		}
		
		// update every objects
		for (Objects obj: objects) {
			obj.update(this);			
		}
		// object needed to add
		if (objectsAdd != null ) {
			objects.add(objectsAdd);
		}
		// objects needed to delete
		if (objectsRemove != null) {
			objects.remove(objectsRemove);
		}
		// update the camera
		camera.update(this);
		
	}
	/**
	 * Render the entire screen, it reflects the current game state.
	 * @param g Slick graphics object, used for drawing.
	 */
	public void render(Graphics g) {
		g.translate(-(float)camera.getX(), -(float)camera.getY());
		map.render(0,0);
		// draw every object on the screen
		for(Objects obj: objects) {
			// print options for selected objects
			if (obj instanceof Builder && obj.isSelected()) {
				g.drawString("1- Create Factory", 
						(float)(32+camera.getX()), (float)(100+camera.getY()));
			} else if (obj instanceof Truck && obj.isSelected()) {
				g.drawString("1- Create Command Centre", 
						(float)(32+camera.getX()), (float)(100+camera.getY()));
			} else if (obj instanceof CommandCentre && obj.isSelected()) {
				g.drawString("1- Create Scout \n2- Create Builder \n3- Create Engineer", 
						(float)(32+camera.getX()), (float)(100+camera.getY()));
			} else if (obj instanceof Factory && obj.isSelected()) {
				g.drawString("1- Create Truck", 
						(float)(32+camera.getX()), (float)(100+camera.getY()));
			}
			obj.render();
		}
		// show information of total resources
		g.drawString("Metal: " + Integer.toString(totalMetal) 
		+"\nUnobtainium: " + Integer.toString(totalUnobtainium), (float)(32+camera.getX()), (float)(32+camera.getY()));
	}
	
	/**
	 * select the object within 32 pixels of the position given
	 * @param input Handle mouse input
	 * @param selectX selected x position
	 * @param selectY selected y position
	 */
	public void select(Input input, double selectX, double selectY) {
		Objects selected = null;
		// check all units and buildings, see which one is selected
		for (Objects obj: objects) {
			// units & buildings can be selected within 32 pixels 
			if ((obj instanceof Units || obj instanceof Buildings) &&
					distance(obj.getX(), obj.getY(), selectX, selectY) <= MIN_SELECT) {
				// deselect previous one 
				// Units has higher priority than Buildings, so select units first
				if ((selected != null && selected instanceof Units && obj instanceof Buildings && !obj.isSelected())
						||obj.isSelected()) {
					obj.setSelected(false);
				} else {
					// if the previous selected object exists, it should not be selected anymore
					selected = obj;
				}
			} else {
				obj.setSelected(false);
			}
		}
		// as we have a selected object, set it as selected
		if (selected != null) {
			selected.setSelected(true);
			// as we selected the object, it should be in the centre of the screen
			if (selected instanceof Units || selected instanceof Buildings) {
				camera.setTarget(selected);
			}
		}
	
	}
	
	/**
	 * read objects from the given CSV file
	 * @param path CSV path 
	 */
	public void readObjects(String path) {
		// store objects and their position given by CSV file in an ArrayList
		try (BufferedReader br = 
				new BufferedReader(new FileReader(path))) {
			String text;
			while ((text = br.readLine()) != null) {
				String[] line = text.split(",");
				int x = Integer.parseInt(line[1]);
				int y = Integer.parseInt(line[2]);
				
				if (line[0].equals("factory")) {
					Factory obj = new Factory(x,y);
					objects.add(obj);
				} else if (line[0].equals("pylon")) {
					Pylon obj = new Pylon(x,y);
					objects.add(obj);
				} else if (line[0].equals("command_centre")) {
					CommandCentre obj = new CommandCentre(x,y);
					objects.add(obj);
				} else if (line[0].equals("metal_mine")) {
					Metal obj = new Metal(x,y);
					objects.add(obj);
				} else if (line[0].equals("unobtainium_mine")) {
					Unobtainium obj = new Unobtainium(x,y);
					objects.add(obj);
				} else if (line[0].equals("scout")) {
					Scout obj = new Scout(x,y);
					objects.add(obj);
				} else if (line[0].equals("engineer")) {
					Engineer obj = new Engineer(x,y);
					objects.add(obj);
				} else if (line[0].equals("builder")) {
					Builder obj = new Builder(x,y);
					objects.add(obj);
				} else if (line[0].equals("truck")) {
					Truck obj = new Truck(x,y);
					objects.add(obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Calculate the distance between source and destination
	 * @param x1 x position of source
	 * @param y1 y position of source
	 * @param x2 x position of destination
	 * @param y2 y position of destination
	 * @return distance
	 */
	public static double distance(double x1, double y1, double x2, double y2) {
		return (double)Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
	
	/**
	 * check whether or not the position is accessible
	 * @param x
	 * @param y
	 * @return true if position is accessible
	 */
	public boolean isPositionFree(double x, double y) {
		int tileId = map.getTileId(worldXToTileX(x), worldYToTileY(y), 0);
		return !Boolean.parseBoolean(map.getTileProperty(tileId, SOLID_PROPERTY, "false"));
	}
	
	/**
	 * check whether or not the position is occupied by other buildings
	 * @param x
	 * @param y
	 * @return true if it is not occupied
	 */
	public boolean notOccupied(double x, double y) {
		for (Object obj: objects) {
			// is occupied by the buildings we created
			if (obj instanceof Buildings && ((Buildings) obj).getX()==x && ((Buildings) obj).getY()==y) {
				return false;
			}
		}
		int tileId = map.getTileId(worldXToTileX(x), worldYToTileY(y), 0);
		return !Boolean.parseBoolean(map.getTileProperty(tileId, OCCUPIED_PROPERTY, "false"));
	}
	
	
	private int worldXToTileX(double x) {
		return (int)(x / map.getTileWidth());
	}
	private int worldYToTileY(double y) {
		return (int)(y / map.getTileHeight());
	}
	
	/**
	 * allow other class to get input 
	 * @return input
	 */
	public Input getInput() {
		return lastInput;
	}
	
	/**
	 * allow other class to get delta
	 * @return delta
	 */
	public int getDelta() {
		return lastDelta;
	}
	
	/**
	 * allow other class to get the width of map
	 * @return width
	 */
	public double getMapWidth() {
		return map.getWidth() * map.getTileWidth();
	}
	
	/**
	 * allow other class to get the height of map
	 * @return height
	 */
	public double getMapHeight() {
		return map.getHeight() * map.getTileHeight();
	}
	
	/**
	 * allow other class to get total number of metal
	 * @return total metal
	 */
	public int getTotalMetal() {
		return totalMetal;
	}
	
	/**
	 * allow other class to set total number of metal
	 * @param total_metal
	 */
	public void setTotalMetal(int total_metal) {
		this.totalMetal = total_metal;
	}
	
	/**
	 * allow other class to get total number of unobtainium
	 * @return total unobtainium
	 */
	public int getTotalUnobtainium() {
		return totalUnobtainium;
	}
	
	/**
	 * allow other class to set total number of unobtainium
	 * @param total_unobtainium
	 */
	public void setTotalUnobtainium(int total_unobtainium) {
		this.totalUnobtainium = total_unobtainium;
	}
	
	/**
	 * allow other class to get ArrayList of all objects
	 * @return ArrayList
	 */
	public ArrayList<Objects> getObjects() {
		return objects;
	}
	
	/**
	 * allow other class to set ArrayList of all objects 
	 * @param objects
	 */
	public void setObjects(ArrayList<Objects> objects) {
		this.objects = objects;
	}
	
	/**
	 * allow other class to get the camera
	 * @return camera
	 */
	public Camera getCamera() {
		return camera;
	}
	
	/**
	 * allow other class to set the objects needs to be added
	 * @param objectsAdd
	 */
	public void setObjectsAdd(Objects objectsAdd) {
		this.objectsAdd = objectsAdd;
	}
	
	/**
	 * allow other class to get minimum distance for selecting 
	 * @return MIN_SELECT
	 */
	public static int getMinSelect() {
		return MIN_SELECT;
	}
	
	/**
	 * allow other class to set the objects needs to be removed
	 * @param objectsRemove
	 */
	public void setObjectsRemove(Objects objectsRemove) {
		this.objectsRemove = objectsRemove;
	}
}
