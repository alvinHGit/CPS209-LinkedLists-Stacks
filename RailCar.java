import java.awt.* ;
import java.awt.geom.* ;

/**
 * RailCar is a Vehicle subclass. It is where all the railcar information and methods that are specific to railcars is. It is mainly used to draw the railcar itself
 *
 * Due April 5th 2015
 * @author Alvin Ho
 */
public class RailCar extends Vehicle
{
	public static final int UNIT = 10 ;
	public static final int U6 = 6 * UNIT ;
	public static final int U5 = 5 * UNIT ;
	public static final int U4 = 4 * UNIT ;
	public static final int U3 = 3 * UNIT ;
	public static final int U2 = 2 * UNIT ;
	public static final int U15 = UNIT + UNIT / 2 ;
	public static final int U05 =  UNIT / 2 ;
	public static final int BODY_WIDTH = U3 ;
	public static final int BODY_HEIGHT = U2 ;
	public Rectangle2D.Double body;
	public Ellipse2D.Double frontTire, rearTire;
	public double FULL_WIDTH = U6 + U05;

	/**
	* Default RailCar constructor.
	*/
	public RailCar(){}

	/**
	* Constructor for RailCar when creating a single railcar that is NOT linked
	* @param count is the number on the railcar
	* @param x is the x coordinate of the railcar
	* @param y is the y coordinate of the railcar
	* @param isTrainEngine is the variable to specify that this is not a trainengine
	*/
	public RailCar(int count, double x, double y, boolean isTrainEngine)
	{
		super(count, x, y, isTrainEngine);
		setWidth(FULL_WIDTH);
		setRailCarWidth(FULL_WIDTH);
	}
	/**
	* Constructor for RailCar when creating a railcar that is to be linked with other vehicles
	* @param front is the Vehicle sent to represent the front of the linked list of vehicles
	* @param trailer is the Vehicle sent to represent the vehicle and all the rest of the vehicles linked to the front
	* @param count is the number on the railcar
	* @param x is the x coordinate of the railcar
	* @param y is the y coordinate of the railcar
	* @param isTrainEngine is the variable to specify that this is not a trainengine
	*/
	public RailCar(Vehicle front, Vehicle trailer, double x, double y, boolean isTrainEngine)
	{
		super(front, trailer, x, y, isTrainEngine);
		setWidth(FULL_WIDTH);
		setRailCarWidth(FULL_WIDTH);
	}

	/**
	* Method that checks whether the mouse clicked on the inside of the railcar
	* @param x is the x coordinate of where the mouse clicked
	* @param y is the y coordinate of where the mouse clicked
	* @return boolean is true if the mouse clicked within the railcar, false otherwise
	*/
	public boolean isMouseSelected (double x, double y)
	{
		if ((body.contains(x,y)) || (frontTire.contains(x,y)) || (rearTire.contains(x,y))) return true;
		return false;
	}
	/**
	* Method that creates an imaginary rectangle the size of the railcar
	* @return Rectangle2D.Double is imaginary rectangle that is returned
	*/
	public Rectangle2D.Double getRectBoundry()
	{
		Rectangle2D.Double rect = new Rectangle2D.Double (getX(), getY(), getWidthWithTrailers(), UNIT);
		return rect; 
	}
	/**
	* Draws the rail car
	* @param g2 the graphics context
	*/
	public void draw(Graphics2D g2)
	{
		//if it is selected, it sets the railcar color to red, if not, black.
		if (!isSelected) g2.setColor(Color.black);
		if (isSelected) g2.setColor(Color.red);

		double xLeft = getX() ;
		double yTop = getY() ;
	
		body = new Rectangle2D.Double(getX(), yTop + UNIT, U6, UNIT);      
		frontTire = new Ellipse2D.Double(getX() + UNIT, yTop + U2, UNIT, UNIT);
		rearTire = new Ellipse2D.Double(getX() + U4, yTop + U2, UNIT, UNIT);

		// the right end of the hitch
		Point2D.Double r5 = new Point2D.Double(getX() + U6, yTop + U15);
		// the left end of the hitch
		Point2D.Double r6 = new Point2D.Double(getX() + U6 + U05, yTop + U15);

		Line2D.Double hitch = new Line2D.Double(r5, r6);

		g2.draw(body);
		g2.draw(hitch);
		g2.draw(frontTire);
		g2.draw(rearTire);
		g2.drawString("" + getNumber(), (int) getX() + U2, (int) yTop + U2) ;

		//checks if the railcar has a container, if yes, it draws it ontop of the railcar
		if (hasContainer)
		{
			vContainer.setLocation (xLeft + U05, (yTop + UNIT) - vContainer.WIDTH - 1);
			vContainer.draw(g2);
		}
	}
}
    
    
    
    
