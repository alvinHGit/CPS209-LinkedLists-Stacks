import javax.swing.*;

/**
 * MainViewer is the class containing the main method
 *
 * Due April 5th 2015
 * @author Alvin Ho
 */
public class MainViewer
{	
	/**
	* The main method that creates the frame
	*/
	public static void main (String[] args)
	{
		JFrame frame = new MainFrame();
		frame.setTitle ("Alvin Ho (500633279) | CPS209 Assignment 2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);		
		frame.setVisible (true);
	}	
}
