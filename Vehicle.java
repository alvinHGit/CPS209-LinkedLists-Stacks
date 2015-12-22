import java.awt.geom.* ;
import java.awt.* ;
import java.util.Random;
/**
 * Vehicle is an abstract and super class of RailCar and TrainEngine, it represents a LinkedList for vehicles
 *
 * Due April 5th 2015
 * @author Alvin Ho
 */
public abstract class Vehicle
{
	public double x, y;
	private int number, trailerCount;
	public boolean isTrailer, isSelected, isTrainEngine, hasContainer;
	public Vehicle trailer; //represents the .next of vehicles
	public StorageContainer vContainer;
	public double FULL_WIDTH, widthWithTrailers, railCarWidth, trainEngineWidth;

	public abstract void draw(Graphics2D g);
	public abstract boolean isMouseSelected(double x, double y);
	public abstract Rectangle2D.Double getRectBoundry();
	public static Random rand = new Random();

	/**
	* Default Vehicle constructor.
	*/
	public Vehicle(){}

	/**
	* Constructor for Vehicle when creating a single vehicle that is NOT linked
	* @param count is the number on the vehicle
	* @param x is the x coordinate of the vehicle
	* @param y is the y coordinate of the vehicle
	* @param isTrainEngine is the variable to specify that this vehicle is not a trainengine
	*/
	public Vehicle(int count, double x, double y, boolean isTrainEngine)
	{
		this.x = x;
		this.y = y;
		number = count;
		this.isTrainEngine = isTrainEngine;
	}

	/**
	* Constructor for Vehicle when creating a Vehicle that is to be linked with other vehicles
	* @param front is the Vehicle sent to represent the front of the linked list of vehicles
	* @param trailer is the Vehicle sent to represent the vehicle and all the rest of the vehicles linked to the front
	* @param count is the number on the vehicle
	* @param x is the x coordinate of the vehicle
	* @param y is the y coordinate of the vehicle
	* @param isTrainEngine is the variable to specify that this vehicle is a trainengine
	*/
	public Vehicle(Vehicle front, Vehicle trailer, double x, double y, boolean isTrainEngine)
	{
		if (front.hasContainer) {
			this.hasContainer = true;
			this.vContainer = front.vContainer;
		}
		this.isTrainEngine = isTrainEngine;
		this.x = x;
		this.y = y;
		number = front.number;

		if (front.getTrailerCount() == 0){
			this.trailer = trailer;
		}
		else if ((front.getTrailerCount() > 0) && (trailer != null))
		{
			this.trailer = front.trailer;
			setLastTrailer(trailer);
		}
	}

	/**
	* Method that recieves a trailer and adds it to the very back of the linked list by recursively calling setLastTrailer to get to the very last trailer for linking
	* @param trailer is the Vehicle (along with all its linked railcars) sent to be linked into to the current vehicle
	*/
	public void setLastTrailer(Vehicle trailer)
	{
		if (this.trailer == null){
			this.trailer = trailer;
			return;
		}
		else if (this.trailer != null){
			this.trailer.setLastTrailer(trailer);	
		}
	}

	/**
	* Accessor method to obtain the vehicle's x coordinate
	* @return double x is the x coordinate
	*/
	public double getX()
	{
		return x;
	}

	/**
	* Accessor method to obtain the vehicle's y coordinate
	* @return double y is the y coordinate
	*/
	public double getY()
	{
		return y;
	}

	/**
	* Mutator method to change the vehicle's (x, y) coordinate
	* @param x is the new x coordinate
	* @param y is the new y coordinate
	*/
	public void setLocation(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	* Accessor method to obtain the vehicle's number
	* @return int number is the vehicle's number
	*/
	public int getNumber()
	{
		return number;
	}

	/**
	* Mutator method to change the vehicle's number
	* @param updatedNumber is the new number
	*/
	public void updateNumber(int updatedNumber)
	{
		number = updatedNumber;
	}

	/**
	* Method to set if the vehicle is selected... it recursively sets it as selected to all the trailers attached to it too.
	* @param tf is the True or False value sent to this method
	*/
	public void setIsSelected (boolean tf)
	{
		isSelected = tf;
		if (trailer != null){
			trailer.setIsSelected(tf);
		}
	}

	/**
	* Accessor method to obtain whether the vehicle "isSelected"
	* @return boolean returns true if is selected, false if not
	*/
	public boolean isSelected()
	{
		return isSelected;
	}

	/**
	* Method to set the vehicle's FULL_WIDTH
	* @param width is the new width
	*/
	public void setWidth(double width)
	{
		FULL_WIDTH = width;
	}

	/**
	* Method to set the vehicle's railCarWidth
	* @param width is the new width
	*/
	public void setRailCarWidth(double width)
	{
		railCarWidth = width;
	}
	
	/**
	* Method that calculates the width of the vehicle along with all the railcars attached to it
	* @return double widthWithTrailers is the value of the vehicle's width with all the railcars
	*/
	public double getWidthWithTrailers()
	{
		if (trailer != null) widthWithTrailers = FULL_WIDTH + railCarWidth*getTrailerCount();
		if (trailer == null) widthWithTrailers = FULL_WIDTH;
		return widthWithTrailers;
	}

	/**
	* Method that recursively calculates how many trailers there are attached
	* @return int trailerCount is the number of attached railcars
	*/
	public int getTrailerCount()
	{
		if(trailer != null){
			trailerCount = 1;
			trailerCount += trailer.getTrailerCount();
		}
		else if (trailer == null) trailerCount = 0;
		return trailerCount;
	}

	/**
	* Method that puts a new vehicle into the first link of the trainEngine vehicle
	* @param trailer is vehicle sent to be put into the front of the attached railcars of the trainEngine
	*/
	public void addFirst(Vehicle trailer)
	{
		Vehicle newBackTrailer = this.trailer;
		this.trailer = trailer;
		setLastTrailer(newBackTrailer);
	}

	/**
	* Method that removes the first railcar attached to the trainEngine
	*/
	public void removeFirst()
	{
		if (trailer.trailer == null){
			MainComponent.vehicles.add (new RailCar(trailer, null, rand.nextInt(MainFrame.FRAME_WIDTH-(int)railCarWidth), rand.nextInt(MainFrame.FRAME_HEIGHT-(int)railCarWidth), false));
			trailer = null;
		}
		else if (trailer.trailer != null){
			Vehicle trailersWithRemovedFirst = trailer.trailer;
			MainComponent.vehicles.add (new RailCar(trailer, null, rand.nextInt(MainFrame.FRAME_WIDTH-(int)railCarWidth), rand.nextInt(MainFrame.FRAME_HEIGHT-(int)railCarWidth), false));
			trailer = null;
			setLastTrailer(trailersWithRemovedFirst);
		}		
	}

	/**
	* Method that removes the last railcar attached to the trainEngine... it goes through to the last trailer recursively before being able to remove it
	*/
	public void removeLast()
	{
		if (trailer.trailer == null){
			MainComponent.vehicles.add (new RailCar(trailer, null, rand.nextInt(MainFrame.FRAME_WIDTH-(int)railCarWidth), rand.nextInt(MainFrame.FRAME_HEIGHT-(int)railCarWidth), false));
			trailer = null;
		}
		else if (trailer.trailer != null){
			trailer.removeLast();
		}
	}

	/**
	* Method sets the vehicle's container info. (i.e. what container does it carry?)
	* @param hasContainer is to set whether the vehicle has a container (true) or if it does not (false)
	* @param vContainer is the container sent for the railcar to carry
	*/
	public void setVContainer(boolean hasContainer, StorageContainer vContainer)
	{
		if ((this.hasContainer == true) && (trailer != null)){
			trailer.setVContainer(hasContainer, vContainer);
		}
		if (this.hasContainer == false){
			this.hasContainer = hasContainer;
			this.vContainer = vContainer;
		}
	}

	/**
	* Method that recursively finds the first railcar in the list to carry a container and then returns it
	* @return StorageContainer is the first container that the railcar is carrying to be returned
	*/
	public StorageContainer getFirstContainer()
	{
		StorageContainer returnContainer = null;
		if ((hasContainer == true) && (!isTrainEngine)){
			StorageContainer tempContainer = vContainer;
			hasContainer = false;
			vContainer = null;
			return tempContainer;
		}
		if ((hasContainer == false) || (isTrainEngine)){
			returnContainer = trailer.getFirstContainer();
		}
		return returnContainer;
	}
	
	/**
	* Method that recursively goes through the list to see if there is an empty railcar (does not carry a container)
	* @return true if the list of railcars is empty (does not carry a container), false if none of the railcars are empty (all of them have containers)
	*/
	public boolean checkIfHasEmptyRailCar()
	{
		boolean empty = false;
		if ((hasContainer == true) && (trailer != null))
			empty = trailer.checkIfHasEmptyRailCar();

		if (hasContainer == false)
			return true;

		if (empty) return true;

		return false;
	}

	/**
	* Method that recursively goes through the list to see if there is a railcar that has a container
	* @return boolean currentHasContainer returns true if the list of railcars is has at least one railcar that carries a container, false if not
	*/
	public boolean checkIfRailCarHasContainer()
	{
		boolean currentHasContainer = false;
		if (((hasContainer == false) && (trailer != null)) || (isTrainEngine) && (trailer != null))
			currentHasContainer = trailer.checkIfRailCarHasContainer();

		else if ((hasContainer == true) && (!isTrainEngine)) return true;

		return currentHasContainer;
	}
}
