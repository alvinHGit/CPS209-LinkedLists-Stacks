import java.awt.geom.* ;
import java.awt.* ;

/**
 * TrainEngine is a Vehicle subclass. It is where all the TrainEngine information and methods that are specific to the TrainEngine is. It is mainly used to draw the TrainEngine itself.
 *
 * Due April 5th 2015
 * @author Alvin Ho
 */
public class TrainEngine extends Vehicle
{
	private static final double WIDTH = 35 ;
	private static final double UNIT = WIDTH / 5 ;
	private static final double LENGTH_FACTOR = 14 ; // length is 14U
	private static final double HEIGHT_FACTOR = 5 ; // height is 5U
	private static final double U_3 = 0.3 * UNIT ; 
	private static final double U2_5 = 2.5 * UNIT ; 
	private static final double U3 = 3 * UNIT ; 
	private static final double U4 = 4 * UNIT ; 
	private static final double U5 = 5 * UNIT ; 
	private static final double U10 = 10 * UNIT ; 
	private static final double U10_7 = 10.7 * UNIT ; 
	private static final double U12 = 12 * UNIT ; 
	private static final double U13 = 13 * UNIT ; 
	private static final double U14 = 14 * UNIT ; 
	private Rectangle2D.Double hood, body;
	private Ellipse2D.Double wheel1, wheel2, wheel3, wheel4, wheel5, wheel6;
	private Line2D.Double hitch;
	RailCar tempRailCar = new RailCar();
	private double FULL_WIDTH = U14; 

	/**
	* Constructor for TrainEngine when creating a single TrainEngine that is NOT linked
	* @param count is the number on the TrainEngine
	* @param x is the x coordinate of the TrainEngine
	* @param y is the y coordinate of the TrainEngine
	* @param isTrainEngine is the variable to specify that this is a trainEngine
	*/
	public TrainEngine(int count, double x, double y, boolean isTrainEngine)
	{
		super(count, x, y, isTrainEngine);
		setWidth(FULL_WIDTH);
		setRailCarWidth(tempRailCar.FULL_WIDTH);
		//makes the hasContainer true so that the trainEngine cannot hold one in the future
		hasContainer = true;
	}
	/**
	* Constructor for TrainEngine when creating a TrainEngine that is to be linked with other vehicles
	* @param front is the Vehicle sent to represent the front of the linked list of vehicles
	* @param trailer is the Vehicle sent to represent the vehicle and all the rest of the vehicles linked to the front
	* @param count is the number on the TrainEngine
	* @param x is the x coordinate of the TrainEngine
	* @param y is the y coordinate of the TrainEngine
	* @param isTrainEngine is the variable to specify that this is a trainengine
	*/
	public TrainEngine(Vehicle front, Vehicle trailer, double x, double y, boolean isTrainEngine)
	{
		super(front, trailer, x, y, isTrainEngine);
		setWidth(FULL_WIDTH);
		setRailCarWidth(tempRailCar.FULL_WIDTH);
		//makes the hasContainer true so that the trainEngine cannot hold one in the future
		hasContainer = true;
	}

	/**
	* Method that checks whether the mouse clicked on the inside of the TrainEngine
	* @param x is the x coordinate of where the mouse clicked
	* @param y is the y coordinate of where the mouse clicked
	* @return boolean is true if the mouse clicked within the TrainEngine, false otherwise
	*/
	public boolean isMouseSelected (double x, double y)
	{
		if ((hood.contains (x, y)) || (body.contains (x, y)) || (wheel1.contains (x, y)) || (wheel2.contains (x, y)) || (wheel3.contains (x, y)) || (wheel4.contains (x, y)) || (wheel5.contains (x, y)) || (wheel6.contains (x, y)) ) return true;
		return false;
	}
	
	/**
	* Method that creates an imaginary rectangle the size of the TrainEngine
	* @return Rectangle2D.Double is imaginary rectangle that is returned
	*/
	public Rectangle2D.Double getRectBoundry()
	{
		Rectangle2D.Double rect = new Rectangle2D.Double (getX(), getY(), getWidthWithTrailers(), U5);
		return rect; 
	}

	/**
	* Draws the train engine
	* @param g2 the graphics context
	*/
	public void draw(Graphics2D g2)
	{
		double x1 = getX() ;
		double y1 = getY() ;
		hood = new Rectangle2D.Double(x1, y1 + UNIT, U3, U3 ) ;
		g2.setColor(Color.blue) ;
		g2.fill(hood) ;

		body = new Rectangle2D.Double(x1 + U3, y1, U10, U4) ;
		g2.setColor(Color.blue) ;
		g2.fill(body) ;

		hitch = new Line2D.Double(x1 + U13, y1 + U2_5, x1 + U14, y1 + U2_5) ;
		g2.setColor(Color.black) ;
		g2.draw(hitch) ;

		wheel1 = new Ellipse2D.Double(x1 + U_3, y1 + U4, UNIT, UNIT) ;
		if (!isSelected) g2.setColor(Color.black);
		if (isSelected) g2.setColor(Color.red);
		g2.fill(wheel1) ;

		wheel2 = new Ellipse2D.Double(x1 + 1.3 * UNIT, y1 + U4, UNIT, UNIT) ;
		g2.fill(wheel2) ;
	
		wheel3 = new Ellipse2D.Double(x1 + 2.3 * UNIT, y1 + 4 * UNIT, UNIT, UNIT) ;
		g2.fill(wheel3) ;

		wheel4 = new Ellipse2D.Double(x1 + U10_7, y1 + U4, UNIT, UNIT) ;
		g2.fill(wheel4) ;

		wheel5 = new Ellipse2D.Double(x1 + U12, y1 + U4, UNIT, UNIT) ;
		g2.fill(wheel5) ;
	
		wheel6 = new Ellipse2D.Double(x1 + 9.7 * UNIT, y1 + U4, UNIT, UNIT) ;
		g2.fill(wheel6) ;
	
	
	}
}
