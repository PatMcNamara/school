//imports
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * This is the main class used throught the program and handels all of the display elements
 * in the program, it uses alot of subclasses that I was initialy planning on breaking off
 * into independant classes but I didn't get around to it.
 */
public class Interface extends JPanel {

	//Initialize a new Cal object that keeps track of the appointments, creates the calendar
	//cells with a variable number of rows
	private Cal calendar = new Cal();
	private final int NUMBER_OF_ROWS = 6;
	private Cell[] cells = new Cell[NUMBER_OF_ROWS * 7];
	//this panel holds all of the cells in a grid layout with 7 columns
	private JPanel cellPanel = new JPanel(new GridLayout(NUMBER_OF_ROWS,7));
	
	//all of these are used in the panel that shows the currently selected
	//appointment
	private JPanel currentAppointment = new JPanel();
	private JLabel monthLabel = new JLabel();
	private JLabel when = new JLabel("When:");
	private JLabel description = new JLabel("");
	
	//keeps track of the month that the calendar is currently displaying
	private int month;
	
	//Creates a new Calendar that defaults to local time and is used to keep track of
	//currently displayed month, first day of month shows what day of the week the month
	//starts on (not too effective
	private GregorianCalendar cal = new GregorianCalendar();
	private int FirstDayOfMonth = firstDayofMonth();
	
	//custom cursor that shows a pointed finger used when advancing months and selecting
 	//appointments
	private Cursor cursor = new Cursor(12);
	
	public Interface() {
		
		setLayout(new BorderLayout());

		//instiates all cells the array
		for (int i=0; i < cells.length; i++){
			cells[i] = new Cell();
		}
		
		//sets the month to the month it is right now then sets up the cells acordingly
		month = cal.get(cal.MONTH);
		updateCells();
		
		//adds cells to cellPanel in order they are in the array,
		if (FirstDayOfMonth + cal.getActualMaximum((cal.DAY_OF_MONTH)) < 7 * (NUMBER_OF_ROWS -1)){
			for (int i = FirstDayOfMonth; i < cells.length-7; i++){
				cellPanel.add(cells[i]);
			}
		} else {
			for (int i = FirstDayOfMonth; i < cells.length; i++){
				cellPanel.add(cells[i]);
			}
		}
		
		//This sets up the current appointment box, but leaves it not visible to start
		currentAppointment.setBorder(BorderFactory.createEtchedBorder());
		currentAppointment.setLayout(new BoxLayout(currentAppointment, BoxLayout.Y_AXIS));
		currentAppointment.setSize(30,630);
		currentAppointment.add(when);
		currentAppointment.add(description);
		currentAppointment.add(Box.createVerticalStrut(10));
		currentAppointment.add(new JLabel("Click on an appointment to modify it"));
		currentAppointment.add(new JLabel("Press any key to delete"));
		currentAppointment.setVisible(false);

		//Sets up the change month panel, sets up action listeners
		JPanel month = new JPanel();
		JLabel right = new JLabel(new ImageIcon("arrowleft.gif"));
		JLabel left = new JLabel(new ImageIcon("arrowright.gif"));
		NextListen monthListen = new NextListen(right, this);
		right.addMouseListener(monthListen);
		left.addMouseListener(monthListen);
		month.add(right);
		month.add(monthLabel);
		month.add(left);
		
		//adds the panels set up above to main panel
		add(cellPanel, BorderLayout.CENTER);
		add(currentAppointment, BorderLayout.SOUTH);
		add(month, BorderLayout.NORTH);
	}
	/*
	 * Should move month back or forward depending upon the parameters, not finished and
	 * disabled for because it causes instability
	 */
/*	public void advanceMonth(boolean forward){
		//goes forward a month
		if (forward){
			if(cal.get(cal.MONTH) == 12){
				cal.set(cal.MONTH, 1);
			}else{
				cal.set(cal.MONTH, cal.MONTH + 1);
			}
		//moves back a month
		}else{
			if (cal.get(cal.MONTH) == 1){
				cal.set(cal.MONTH, 12);
			}else{
				cal.set(cal.MONTH, cal.MONTH - 1);
			}
		}
		//updates local month variable to reflect changes, updates the cell displays for new
		//month
		month = cal.get(cal.MONTH);
		updateCells();
		
	}
*/	
	/*
	 * puts cells into an apropreate condition for the month, used to be in constructor but
	 * was moved out to try to provide support for changing months.  Has always been a bit
	 * messed up, no matter how much I have attempeted to fix it.  This is the best example
	 * of just how bad the Java Calendar API is.  Many of the values in this method where
	 * incorrect.  This is the reason the appointments don't show up in the right cell,
	 * havn't found the problem yet though
	 */
	public void updateCells(){
		//finds starting position for numbering of cells
		int firstday = firstDayofMonth();
		//adds numbers and listiners to the approprite cells
		for(int day = 1; day <= cal.getActualMaximum(cal.DAY_OF_MONTH); day++){
			cells[day+firstday].numberCell(day);
			cells[day+firstday].addMouseListener(cells[day+firstday]);
		}
		monthLabel = new JLabel("" + monthIntToString(this.month));
	}
	/*
	 * This is the other great example of how bad the calendar api is.  Confusing naming,
	 * initialization problems and a huge questionmark factor show just how badly this is
	 * designed.  This still doesn't seem to work properly, even after hours of trying to
	 * fix it.  This method has gone through many iterations
	 */
	public int firstDayofMonth(){
		//gets day of week and the date
		//System.out.println(cal.get(cal.DATE));
		//System.out.println(cal.get(cal.DAY_OF_WEEK));
		int date = cal.get(cal.DATE);
		int day = cal.get(cal.DAY_OF_WEEK);
		
		//gets the date below 7
		while (date>7){
			date-=7;
		}
		
		//This is left over code that was going to be activated once multiple months where
		//put in place.
		/*
		if(day-date<=0){
			day+=7;
		}*/

		FirstDayOfMonth = date;
		return date;
	}
	/*
	 * Takes as input an int and returnes the name of the month, for use with month display
	 * label seen above.  Simple switch statement.
	 */
	public static String monthIntToString(int month){
		switch (month){
		case 0: return "January";
		case 1: return "February";
		case 2: return "March";
		case 3: return "April";
		case 4: return "May";
		case 5: return "June";
		case 6: return "July";
		case 7: return "August";
		case 8: return "September";
		case 9: return "October";
		case 10: return "November";
		case 11: return "December";
		}
		//this is just to catch errors
		return "Month not valid";
	}
	
	/*
	 * Class used for creating each individual cell (date) in calendar.  Supports methods
	 * to create a new appointment at mouse click and also holds appointments and descriptions
	 * for this month for the purpose of displaying.  Probably should have been split off
	 * into it's own class, not just for proper object orientation but also for for simplicity
	 */
	public class Cell extends JPanel implements MouseListener{

		//creates an array for holding 5 appointments (the only way of dealing with magic
		//number would be by putting in scroll box or something of the sort), the new
		//appointment window isn't initialized here because this cell might just be a
		//placeholder
		private newAppoint newAppointmentWindow;
		private DispAppoint[] appointments = new DispAppoint[5];
		private int numAppointments = 0; //keeps track of valid items in array
		
		//lays out the cell with a default 90px by 90px size
		public Cell(){
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			setPreferredSize(new Dimension(90,90));
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		}
		
		//If the cell is valid (it is not just a placeholder) this will be called to give
		//it a date and initialize the appointment window for adding new appointments
		public void numberCell(int number) {
			JLabel date = new JLabel("" + number);
			add(date);
			date.setAlignmentY(SwingConstants.WEST); //TODO
			newAppointmentWindow = new newAppoint(number);
			//appointments is instatiated here because you can not add an appointment to a
			//date that is not part of this month
			for(int i=0;i<5;i++){//used the magic number again
				appointments[i] = new DispAppoint();
				add(appointments[i]);
			}
		}
		
		//this will tell the cell to add an appointment with the given description, index
		//refers to location in Cal
		public void addAppoint(String description, int index){
			appointments[numAppointments++].changeDescription(description, index);
		}
		//creates an event that will run when you click on a cell, will bring up the add
		//appointment window
		public void mouseClicked(MouseEvent event){
			newAppointmentWindow.appointWindow();
		}
		//Empty definitions
		public void mousePressed(MouseEvent event){}
		public void mouseReleased(MouseEvent event){}
		public void mouseExited(MouseEvent event){}
		public void mouseEntered(MouseEvent event){}
		
		/*
		 * Visualy shows which cells have appointments, along with a small description.
		 * The inital plan was to use the delete key to get rid of appointments but
		 * the key listener suddenly stopped working, and now clicking on an appointment 
		 * will delete it
		 */
		public class DispAppoint extends JPanel implements MouseListener, KeyListener {
			
			JLabel appointDescription = new JLabel();
			int index;
			//sets up the panel for displaying
			public DispAppoint(){
				setBorder(BorderFactory.createLoweredBevelBorder());
				setMaximumSize(new Dimension(1000,25));
				setMinimumSize(new Dimension(1000,25));
				add(appointDescription);
				addMouseListener(this);
				addKeyListener(this);
				setVisible(false);
			}
			
			//this will make the appointment visible and give it an index value so it can
			//delete the appointment referance when it is deleted
			public void changeDescription(String description, int index){
				appointDescription.setText(description);
				this.index = index;
				setVisible(true);
			}
			
			//Run when you mouse over the appointment, sets custom cursor and shows side
			//bar with appointment details.
			public void mouseEntered(MouseEvent event){
				setCursor(cursor);
				requestFocusInWindow();
				Appointment temp = calendar.getDate(index);
				//TODO this creates null pointer when 2 events exist on the same day and the first event (that was added second) is deleted
				when.setText("When: " + temp.getDate());
				description.setText("Description: " + temp.getDescription());
				currentAppointment.setVisible(true);
				setFocusable(true);
			}
			//turns cursor back to normal when it leaves the appointment panel
			public void mouseExited(MouseEvent event){
				setCursor(Cursor.getDefaultCursor());
			}
			//delets the appointment when the mouse is clicked
			public void mouseClicked(MouseEvent event){
				calendar.deleteAppointment(index);
				setVisible(false);
				currentAppointment.setVisible(false);
			}
			public void keyPressed(KeyEvent event){
				calendar.deleteAppointment(index);
				setVisible(false);
				currentAppointment.setVisible(false);
				setFocusable(false);
			}
//			Empty Definitions for unused event methods
			public void keyTyped(KeyEvent event){}
			public void keyReleased(KeyEvent event){}
			public void mousePressed(MouseEvent event){}
			public void mouseReleased(MouseEvent event){}
		}
	}
	
	/*
	 * Controles the new appointment window.  It would have been better to just
	 * have on frame insted of one per cell.
	 */
	public class newAppoint {
		private JFrame window = new JFrame("Create New Appointment");
		//should set border panels layout to be vertical
		private JPanel panel = new JPanel();
		//TODO ensure that hour and min always have 2 digets and are valid
		private JTextField hour = new JTextField(2);
		private JTextField min = new JTextField(2);
		private JTextField description = new JTextField("Description", 20);
		private JButton create = new JButton("Create");
		private JButton cancel = new JButton("Cancel");
		private JComboBox ampm = new JComboBox();
		
		private int date, month, year;

		//sets up the new appointment window to use
		public newAppoint(int day){

			description.addFocusListener(new descriptionPoint());
			
			//gets know data by what cell you clicked on
			date = day;
			month = cal.get(cal.MONTH);
			year = cal.get(cal.YEAR);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
			//AM/PM combo box
			ampm.addItem(new String("AM"));
			ampm.addItem(new String("PM"));
		
			//sets up time prompts
			JPanel time = new JPanel();
			time.add(new JLabel("Time: "));
			time.add(hour);
			time.add(new JLabel(":"));
			time.add(min);
			time.add(ampm);
			
			//creates buttons
			JPanel buttons = new JPanel();
			ButtonListener listen = new ButtonListener();
			create.addActionListener(listen);
			cancel.addActionListener(listen);
			buttons.add(create);
			buttons.add(cancel);
			
			//adds elements and packs the panel
			panel.add(time);
			panel.add(description);
			panel.add(buttons);
			window.getContentPane().add(panel);
			window.pack();
		}

		//sets the window visibiltiy
		public void appointWindow(){
			window.setVisible(true);
		}
		
		private class descriptionPoint implements FocusListener {
			public void focusGained(FocusEvent event){
				if (description.getText().equals(new String("Description"))){
					description.setText("");
				}
			}
			public void focusLost(FocusEvent event) {
				if (description.getText().equals(new String(""))){
					description.setText("Description");
				}
			}
		}
		
		//Listener for buttons above, creates new appointments when clicked
		private class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event){
				//checks to see if cancle was pressed
				if (event.getSource() == create && timeIsValid()) {
					//creates the new appointment
					Calendar create = (Calendar)cal.clone();
					create.set(year, month, date, 0, 0, 0);
					int time = (Integer.parseInt(hour.getText())*60) +
						Integer.parseInt(min.getText());
					if (ampm.getSelectedItem().equals(ampm.getItemAt(1))){
						time += 12 * 60;
					}
					calendar.addAppointment(description.getText(), create,
							time);
					//makes the appointment visible in cells
					cells[date + FirstDayOfMonth].addAppoint(description.getText(), 
							calendar.getAppointmentNum()-1);
					//hids window
					window.setVisible(false);
				}else if (event.getSource() != create) {
					//hids window
					window.setVisible(false);
				}else{
					JOptionPane.showMessageDialog(null, "Not valid time");
				}
			}
			public boolean timeIsValid(){
				int h, m;
				try {
					h = Integer.parseInt(hour.getText());
					m = Integer.parseInt(min.getText());
				} catch (Exception e) {return false;}
				if (0<h && (12>h || (ampm.getSelectedItem().equals(ampm.getItemAt(0))
						&& 24>h)) && 0<m && 60>m){
					return true;
				}
				return false;
			}
		}
	}
	
	/*
	 * was planned to be used to keep track of changes in month.  Since this feature does
	 * not work, this class can be ignored
	 */
	private class NextListen implements MouseListener{
		private JLabel right;
		private Interface parent;
		public NextListen(JLabel right, Interface parent){
			this.right = right;
			this.parent = parent;
		}
		public void mouseClicked(MouseEvent event){
//			parent.advanceMonth(event.getSource().equals(right));
		}
		public void mouseEntered(MouseEvent event){setCursor(cursor);}
		public void mouseExited(MouseEvent event){setCursor(Cursor.getDefaultCursor());}
		//Empty definitions for unused event methods
		public void mousePressed(MouseEvent event){}
		public void mouseReleased(MouseEvent event){}
	}
}