import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MainFrame is a JFrame subclass. It is where the menuBar and MainComponent is created and added into the frame. It holds all the listeners to the menuBar items so that the program knows what to do when you click each item.
 *
 * Due April 5th 2015
 * @author Alvin Ho
 */
public class MainFrame extends JFrame
{
	public static final int FRAME_WIDTH  = 800;
	public static final int FRAME_HEIGHT = 600;
	public MainComponent component;

	/**
	* Constructor that sets the frame bounds, creates the JMenuBar "menuBar", adds the JMenus to the JMenuBar, and creates and adds the JComponent "component"
	*/
	public MainFrame()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) screenSize.getWidth()/2 - FRAME_WIDTH/2, (int) screenSize.getHeight()/2 - FRAME_HEIGHT/2, FRAME_WIDTH, FRAME_HEIGHT);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		menuBar.add(createStackMenu());
		menuBar.add(createListMenu());
		setJMenuBar(menuBar);

		component = new MainComponent(FRAME_WIDTH, FRAME_HEIGHT);
		add(component);
	}

	/**
	Creates the File menu along with all its items.
	@return the menu
	*/
	public JMenu createFileMenu()
	{
		JMenu menu = new JMenu("File");
		JMenuItem newItem = new JMenuItem("New");
		JMenuItem exitItem = new JMenuItem("Exit"); 

		ActionListener newItemListener = new NewItemListener();
		newItem.addActionListener(newItemListener);
		ActionListener exitItemListener = new ExitItemListener();
		exitItem.addActionListener(exitItemListener);

		menu.add(newItem);
		menu.add(exitItem);
		return menu;
	}
	/**
	Creates the Stack menu along with all its items.
	@return the menu
	*/
	public JMenu createStackMenu()
	{
		JMenu menu = new JMenu("Stack");
		JMenuItem popItem = new JMenuItem("Pop");
		JMenuItem pushItem = new JMenuItem("Push");

		ActionListener popItemListener = new PopItemListener();
		popItem.addActionListener(popItemListener);
		ActionListener pushItemListener = new PushItemListener();
		pushItem.addActionListener(pushItemListener);

		menu.add(popItem);
		menu.add(pushItem);
		return menu;
	}
	/**
	Creates the List menu along with all its items.
	@return the menu
	*/
	public JMenu createListMenu()
	{
		JMenu menu = new JMenu("List");
		JMenuItem addFirstItem = new JMenuItem("AddFirst");
		JMenuItem addLastItem = new JMenuItem("AddLast"); 
		JMenuItem removeFirstItem = new JMenuItem("RemoveFirst");
		JMenuItem removeLastItem = new JMenuItem("RemoveLast");

		ActionListener addFirstItemListener = new AddFirstItemListener();
		addFirstItem.addActionListener(addFirstItemListener);
		ActionListener addLastItemListener = new AddLastItemListener();
		addLastItem.addActionListener(addLastItemListener);
		ActionListener removeFirstItemListener = new RemoveFirstItemListener();
		removeFirstItem.addActionListener(removeFirstItemListener);
		ActionListener removeLastItemListener = new RemoveLastItemListener();
		removeLastItem.addActionListener(removeLastItemListener);

		menu.add(addFirstItem);
		menu.add(addLastItem);
		menu.add(removeFirstItem);
		menu.add(removeLastItem);
		return menu;
	}
	
	/**
	Class within the MainFrame that implements ActionListener. It listens for when the exitItem is pressed. 
	*/
	class ExitItemListener implements ActionListener
	{
		/**
		* Method that corresponds to the ExitItemListener. Every time exitItem is pressed, it closes the program.
		* @param event is an ActionEvent object
		*/
		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}
	}
	/**
	Class within the MainFrame that implements ActionListener. It listens for when the newItem is pressed. 
	*/
	class NewItemListener implements ActionListener
	{
		/**
		* Method that corresponds to the NewItemListener. Every time newItem is pressed, this method resets the necessary variables in the component and basically clears the screen. Repaints.
		* @param event is an ActionEvent object
		*/
		public void actionPerformed(ActionEvent event)
		{
			component.clickCount = 0;
			component.vehicles.clear();
			component.containers = null;
			component.storagePlatform = null;
			component.hasOneSelected = false;
			
			component.repaint();
		}
	}
	/**
	Class within the MainFrame that implements ActionListener. It listens for when the popItem is pressed. 
	*/
	class PopItemListener implements ActionListener
	{
		/**
		* Method that corresponds to the popItemListener. Every time popItem is pressed, it checks if a RailCar/TrainEngine is selected and which one is selected. It then calls the container's pop() method to "pop" off the top of the stack of containers and gives it to the railcar. Repaints.
		* @param event is an ActionEvent object
		*/
		public void actionPerformed(ActionEvent event)
		{
			if (component.hasOneSelected){
				for (int i = 0; i < component.vehicles.size(); i ++)
				{
					if ((component.vehicles.get(i).isSelected()))
					{
						if (component.vehicles.get(i).checkIfHasEmptyRailCar())
						{
							component.vehicles.get(i).setVContainer(true, component.containers.pop());
							component.repaint();
						}
					}
				}
			}
		}
	}
	/**
	Class within the MainFrame that implements ActionListener. It listens for when the pushItem is pressed. 
	*/
	class PushItemListener implements ActionListener
	{
		/**
		* Method that corresponds to the pushItemListener. Every time pushItem is pressed, it checks if a RailCar/TrainEngine is selected and which one is selected. It then calls the container's push(StorageContainer container) method to remove the railcar's container and then "push" it back onto the containers stack. Repaints.
		* @param event is an ActionEvent object
		*/
		public void actionPerformed(ActionEvent event)
		{
			if (component.hasOneSelected){
				for (int i = 0; i < component.vehicles.size(); i ++)
				{
					if ((component.vehicles.get(i).isSelected()))
					{
						if (component.vehicles.get(i).checkIfRailCarHasContainer())
						{
							component.containers.push(component.vehicles.get(i).getFirstContainer());
							component.repaint();
						}
					}
				}
			}
		}
	}
	/**
	Class within the MainFrame that implements ActionListener. It listens for when the addFirstItem is pressed. 
	*/
	class AddFirstItemListener implements ActionListener
	{
		/**
		* Method that corresponds to the addFirstItemListener. Every time addFirstItem is pressed, it checks if a RailCar is selected and which one is selected. It then calls the addFirst(Vehicle trailer) method in the Vehicle class to add the railcar to the beginning of the link of the TrainEngine. Repaints.
		* @param event is an ActionEvent object
		*/
		public void actionPerformed(ActionEvent event)
		{
			if (component.hasOneSelected){
				for (int i = 1; i < component.vehicles.size(); i ++)
				{
					if ((component.vehicles.get(i).isSelected()))
					{
						component.vehicles.get(0).addFirst(component.vehicles.get(i));
						component.vehicles.get(i).setIsSelected (false);
						component.hasOneSelected = false;
						component.vehicles.remove(component.vehicles.get(i));
						component.repaint();
					}
				}
			}
		}
	}
	/**
	Class within the MainFrame that implements ActionListener. It listens for when the addLastItem is pressed. 
	*/
	class AddLastItemListener implements ActionListener
	{
		/**
		* Method that corresponds to the addLastItemListener. Every time addLastItem is pressed, it checks if a RailCar is selected and which one is selected. It then calls the addLast(Vehicle trailer) method in the Vehicle class to add the railcar to the end of the link of the TrainEngine. Repaints.
		* @param event is an ActionEvent object
		*/
		public void actionPerformed(ActionEvent event)
		{
			if (component.hasOneSelected){
				for (int i = 1; i < component.vehicles.size(); i ++)
				{
					if ((component.vehicles.get(i).isSelected()))
					{
						component.vehicles.get(0).setLastTrailer(component.vehicles.get(i));
						component.vehicles.get(i).setIsSelected (false);
						component.hasOneSelected = false;
						component.vehicles.remove(component.vehicles.get(i));
						component.repaint();
					}
				}
			}
		}
	}
	/**
	Class within the MainFrame that implements ActionListener. It listens for when the removeFirstItem is pressed. 
	*/
	class RemoveFirstItemListener implements ActionListener
	{
		/**
		* Method that corresponds to the removeFirstIemListener. Every time removeFirstItem is pressed, it checks if the TrainEngine linkedlist has trailers in it, if it does, it calls the removeFirst() method in the Vehicle class removes the first railcar in the TrainEngine link.
		* @param event is an ActionEvent object
		*/
		public void actionPerformed(ActionEvent event)
		{
			if (component.vehicles.get(0).getTrailerCount() > 0){
				component.vehicles.get(0).removeFirst();
				component.repaint();
			}
		}
	}
	/**
	Class within the MainFrame that implements ActionListener. It listens for when the removeLastItem is pressed. 
	*/
	class RemoveLastItemListener implements ActionListener
	{
		/**
		* Method that corresponds to the removeLastIemListener. Every time removeLastItem is pressed, it checks if the TrainEngine linkedlist has trailers in it, if it does, it calls the removeLast() method in the Vehicle class removes the last railcar in the TrainEngine link.
		* @param event is an ActionEvent object
		*/
		public void actionPerformed(ActionEvent event)
		{
			if (component.vehicles.get(0).getTrailerCount() > 0){
				component.vehicles.get(0).removeLast();
				component.repaint();
			}
		}
	}
}
