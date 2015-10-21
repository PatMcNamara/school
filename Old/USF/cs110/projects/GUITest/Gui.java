import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui {
	
	Cell cell = new Cell();
	JFrame frame = new JFrame("test");
	
	public Gui(){
		
		cell.numberCell(3);
		cell.addMouseListener(cell);
		
		frame.getContentPane().add(cell);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public class Cell extends JPanel implements MouseListener{
		
		private JLabel date;
		
		private newAppoint newAppointmentWindow = new newAppoint(0,0,0);
		
		public Cell(){
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			setPreferredSize(new Dimension(90,90));
		}
		
		public void numberCell(int number){
			add(date = new JLabel("" + number)); 
		}
		
		public void mouseClicked(MouseEvent event){
			newAppointmentWindow.appointWindow();
		}
		public void mousePressed(MouseEvent event){}
		public void mouseReleased(MouseEvent event){}
		public void mouseExited(MouseEvent event){}
		public void mouseEntered(MouseEvent event){}
	}
	public class newAppoint{
		
		JFrame window = new JFrame("Test");
		//should set border panels layout to be vertical
		JPanel panel = new JPanel();
		
		private JTextField hour = new JTextField();
		private JTextField min = new JTextField();
		private JTextField sec = new JTextField();
		
		private int date, month, year;

		public newAppoint(int day, int month, int year){
			
			this.date = day;
			this.month = month;
			this.year = year;
			
			JPanel time = new JPanel();
			time.add(new JLabel("Time: "));
			time.add(hour);
			time.add(new JLabel(":"));
			time.add(min);
			time.add(new JLabel(":"));
			time.add(sec);
			
			panel.add(time);
			window.getContentPane().add(panel);
			window.pack();
		}

		public void appointWindow(){
			window.setVisible(true);
			//call addAppointment
		}
	}
	
}

