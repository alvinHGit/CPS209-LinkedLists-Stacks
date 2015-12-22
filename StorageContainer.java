import java.awt.* ;
import java.awt.geom.* ;
/**
 * StorageContainer is a class that represents a Stack for StorageContainers. It holds all the information of the container and the info to draw it.
 *
 * Due April 5th 2015
 * @author Alvin Ho
 */
public class StorageContainer
{
	public static final double WIDTH = 40;
	public double x, y;
	private Rectangle2D.Double container;

	public boolean dead;
	public char letter;
	public StorageContainer next;

	/**
	* Constructor for StorageContainer
	* @param letter is the letter of the container
	*/
	public StorageContainer(char letter)
	{
		this.letter = letter;
	}

	/**
	* Constructor for StorageContainer, used for pop and push
	* @param container is the new container to be put on the top of the stack
	*/
	public StorageContainer(double x, double y, char letter, StorageContainer next)
	{
		this.x = x;
		this.y = y;
		this.letter = letter;
		this.next = next;
	}

	/**
	* Method to add a new StorageContainer to the top of the stack
	* @param container is the new container to be put on the top of the stack
	*/
	public void push(StorageContainer container)
	{
		container.next = new StorageContainer(x, y, letter, next); 
		letter = container.letter;
		next = container.next;
	}

	/**
	* Method to remove the StorageContainer at the top of the stack
	*/
	public StorageContainer pop()
	{
			StorageContainer poppedContainer = new StorageContainer(x, y, letter, next);
			if (next != null)
			{
				x = next.x;
				y = next.y;
				letter = next.letter;
				next = next.next;
			}
			else if (next == null)
			{
				dead = true;
			}
			return poppedContainer;
	}

	/**
	* Mutator method to change the container's (x, y) coordinate
	* @param x is the new x coordinate
	* @param y is the new y coordinate
	*/
	public void setLocation(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	* Accessor method to obtain the container's x coordinate
	* @return double x is the x coordinate
	*/
	public double getX()
	{
		return x;
	}
	
	/**
	* Accessor method to obtain the container's y coordinate
	* @return double y is the y coordinate
	*/
	public double getY()
	{
		return y;
	}

	/**
	* Method that recursively counts the size of the stack by going through each container's .next and adding 1 to count
	* @return int count is the size of the stack
	*/
	public int size()
	{
		int count = 1;
		if (next != null)
			count += next.size();

		return count;
	}

	/**
	* Draws the Storage Container
	* @param g2 the graphics context
	*/
	public void draw(Graphics2D g2)
	{
		if (!dead){
			g2.setColor (new Color(60, 179, 113));
			container = new Rectangle2D.Double(x, y, WIDTH, WIDTH);      

			g2.draw(container);
			g2.setColor (Color.black);
			g2.drawString(" " + letter, (int) (x + (WIDTH/3)), (int) (y + (WIDTH/3)*2) );
		}
	}
}
