import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Stack;
import java.awt.geom.* ;

/**
 * MainComonent is a JComponent subclass. It is where the program listens for mouse actions and draws the TrainEngine, RailCars, and the StorageContainer stacks
 *
 * Due April 5th 2015
 * @author Alvin Ho
 */
public class MainComponent extends JComponent
{
	public int clickCount;
	public static final int PLATFORM_LENGTH = 65, PLATFORM_HEIGHT = 20;
	public static final int NUM_OF_VEHICLES = 6;
	public static final int NUM_OF_CONTAINERS = NUM_OF_VEHICLES-1;
	public boolean hasOneSelected;
	public Rectangle2D.Double selectedVBoundry, otherVBoundry, storagePlatform;

	//creating the StorageContainer stack
	public static StorageContainer containers;
	//creating an ArrayList of vehicles. NOTE: *this is an arraylist of linkedlists since the number of linkedlists in this program is constantly changing when linking and unlinking railcars*
	public static ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>(NUM_OF_VEHICLES);
	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	* Constructor that of the component, it has the mouselisteners and mousemotionlisteners
	* @param width is the FRAME_WIDTH from the MainFrame
	* @param height is the FRAME_HEIGHT from the MainFrame
	*/
	public MainComponent(int width, int height)
	{
		/**
		Class within the MainComponent that implements MouseListener and MouseMotionListener. It listens for when the mouse is pressed, released, clicked, and dragged.
		*/
		class MyListener implements MouseListener, MouseMotionListener{
			/**
			* Method that activates when the mouse is pressed. This method checks when, in the beginning, to create the first TrainEngine, RailCars, and StorageContainer stacks. It then checks if the mouse is within one of the vehicles, if yes, then it selects the vehicle.
			* @param event is an ActionEvent object
			*/
			public void mousePressed(MouseEvent event)
			{
				if (clickCount == 0)
					//creating the first train engine
					vehicles.add (new TrainEngine(clickCount, event.getX(), event.getY(), true));
				else if ((clickCount >= 1) && (clickCount < NUM_OF_VEHICLES)){
					//creating the first railcars
					vehicles.add (new RailCar(clickCount, event.getX(), event.getY(), false));
				}
				else if (clickCount == NUM_OF_VEHICLES)
				{
					//creating the storagePlatform and the first container 'A'
					storagePlatform = new Rectangle2D.Double (event.getX(), event.getY(), PLATFORM_LENGTH, PLATFORM_HEIGHT);
					containers = new StorageContainer(ALPHABET.charAt(0));
					containers.setLocation (event.getX()+PLATFORM_LENGTH/6, event.getY() - (containers.WIDTH*NUM_OF_CONTAINERS)-2);

					//creating the rest of the storage containers by pushing newly made StorageContainers into the stack
					for (int i = 1; i < NUM_OF_CONTAINERS; i++)
					{
						containers.push(new StorageContainer(ALPHABET.charAt(i)));
					}
				}

				//basically checking for if the mouse clicked on any vehicle on the screen and making the vehicle's "isSelected" variable true.
				else if (clickCount > NUM_OF_VEHICLES){
					for (int i = 0; i < vehicles.size(); i ++)
					{
						if ((vehicles.get(i).isMouseSelected(event.getX(), event.getY())) && (!hasOneSelected))
						{
							vehicles.get(i).setIsSelected (true);
							hasOneSelected = true;
						}
					}
				}	
				clickCount ++;
				repaint();
			}
			
			/**
			* Method that activates when the mouse is released. This method basically does checks to see which railcar was selected, creates an imaginary boundry box surrounding it, then checks if this boundry box intersects any other vehicle's imaginary boundry box. If they intersect, the vehicles will be linked... in other words, the selected vehicle is now added into the linkedlist of the intersected vehicle.
			* @param event is an ActionEvent object
			*/
			public void mouseReleased(MouseEvent event){
				if (hasOneSelected == true){
					for (int i = 0; i < vehicles.size(); i ++)
					{
							if ((vehicles.get(i).isSelected()) && (!vehicles.get(i).isTrainEngine)){
								selectedVBoundry = vehicles.get(i).getRectBoundry();
								for (int x = 0; x < vehicles.size(); x ++)
								{
									if (i != x){
										otherVBoundry = vehicles.get(x).getRectBoundry();
										if (otherVBoundry.intersects(selectedVBoundry)){
											vehicles.get(i).setIsSelected (false);
											hasOneSelected = false;

											if (vehicles.get(x).isTrainEngine)
												vehicles.set(x, new TrainEngine(vehicles.get(x), vehicles.get(i), vehicles.get(x).getX(), vehicles.get(x).getY(), true));
											if (!vehicles.get(x).isTrainEngine)
												vehicles.set(x, new RailCar(vehicles.get(x), vehicles.get(i), vehicles.get(x).getX(), vehicles.get(x).getY(), false));

											vehicles.remove(vehicles.get(i));
											break;
										}
									}
								}
								
							}

					}
				}
				repaint();
			}
			/**
			* Method that activates when the mouse is clicked. This checks if the user's click on an empty space, if there is a vehicle selected already, it will deselect it.
			* @param event is an ActionEvent object
			*/
			public void mouseClicked(MouseEvent event){
				if (hasOneSelected == true){
					for (int i = 0; i < vehicles.size(); i ++)
					{
						if ((vehicles.get(i).isSelected) && (!vehicles.get(i).isMouseSelected(event.getX(), event.getY()))){
							vehicles.get(i).setIsSelected (false);
							hasOneSelected = false;
						}
					}
				}
			}
			public void mouseEntered(MouseEvent event){}
			public void mouseExited(MouseEvent event){}
			public void mouseMoved(MouseEvent event){}
			/**
			* Method that activates when the mouse is dragged. Basically checks which vehicle is selected and updates its location to wherever the mouse is.
			* @param event is an ActionEvent object
			*/
			public void mouseDragged(MouseEvent event){
				if (hasOneSelected){
					for (int i = 0; i < vehicles.size(); i ++)
					{
						if ((vehicles.get(i).isSelected()))
						{
							vehicles.get(i).setLocation (event.getX(), event.getY());
							repaint();
							break;
						}
					}
				}
			}
		}
		MouseListener listener = new MyListener() ;
		MouseMotionListener mouseListener = new MyListener();
		addMouseListener(listener) ;
		addMouseMotionListener(mouseListener);
	}
	
	/**
	* Method that calls the objects of the classes RailCar, TrainEngine, and StorageContainer to recursively draw what was created in the current class
	* @param g is a Graphics object sent for functionality of drawing
	*/
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		//drawing the storage platform
		if (storagePlatform != null){
			g2.fill(storagePlatform);
		}
		
		//runs through the arraylist of vehicles and recursively draws the linked lists of vehicles within it
		for (Vehicle vehicle : vehicles)
		{
			if (vehicle == null) break;
			while (vehicle != null)
			{
				vehicle.draw(g2) ;
				if (vehicle.trailer != null) {
					double newX = vehicle.getX() + vehicle.FULL_WIDTH;
					double newY = vehicle.getY();
					vehicle.trailer.setLocation (newX, newY);
				}
				vehicle = vehicle.trailer;
			}
		}

		//recursively draws the stack of storage containers that are above the storage platform
		if (containers != null){
			StorageContainer container = containers;
			container.setLocation(storagePlatform.getX()+PLATFORM_LENGTH/6, storagePlatform.getY() - (containers.WIDTH*containers.size())-2);
			while (container != null)
			{
				container.draw(g2);
				if (container.next != null){
					double newX = container.getX();
					double newY = container.getY() + container.WIDTH;
					container.next.setLocation (newX, newY);
				}
				container = container.next;
			}
		}
	}
}
