import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.table.DefaultTableModel;

/**
 *  Class:	MonthView
 *  Description: MonthView is a concrete class that belongs to the Strategy implementation and 
 *  provide a specific algorithm to execute
 *  The Algorithm it provide changes the view of the right side of the application to MonthView per the
 *  User's request.
 * @author Eric, Yanpeng, Trung
 */
public class MonthView  implements Strategy {
	    GregorianCalendar calendar = new GregorianCalendar();
		private JPanel monthPanel = new JPanel();
		private JPanel monthHeading = new JPanel();
	   	Model m= new Model();
	   	Calendar c = Calendar.getInstance();


		private String[] columns = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
			
	
		@Override
		/**
		 * 
		 */
		public JScrollPane selectedView (Model m)
		{
			this.m = m;
			c.set(  Integer.parseInt(m.getYear()), Integer.parseInt(m.getMonth()), Integer.parseInt(m.getDay()));////////////
			
			
			String[] columns = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
			monthPanel = new JPanel();	
			monthHeading = new JPanel();
		

			int dayOfMonth = m.getCalendar().get((Calendar.DAY_OF_MONTH));
			int dayOfMonth2 = dayOfMonth;

			for(int i = 1; i < dayOfMonth; i++)
			{
				m.previousDay();
			}
			System.out.println(m.getDay());
			c.set(  Integer.parseInt(m.getYear()), Integer.parseInt(m.getMonth()), Integer.parseInt(m.getDay()));////////////
			
			int day = m.getCalendar().get((Calendar.DAY_OF_WEEK));

			for ( int i =0; i <= columns.length;i++){
				JLabel dayWeek = new JLabel();
				dayWeek.setText(Integer.toString(i));
				monthHeading.add(dayWeek);
			}		
			for(int i =1; i <= day -1; i++){
				JLabel blank = new JLabel(" ");
				monthPanel.add(blank);
			}
			

			for (int i=1; i<= m.getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH); i++) 
			{
				JTextPane l = new JTextPane();
				l.setOpaque(true);
				l.setBackground(Color.LIGHT_GRAY);
				l.setForeground(Color.BLACK);
				
				TreeMap<GregorianCalendar, String> eventTreeMap1 =  new TreeMap<GregorianCalendar, String>();
				String key1 = m.getYear() + m.getMonth() + m.getDay();
				String allEvents1 =  m.getMonth() + "/" + m.getDay() + "/" + m.getYear() + '\n';
				if (m.getEvents().get(key1) != null) 
				{
					for (int j =0; j<= m.getEvents().get(key1).size()-1; j++)
					{
						String title = m.getEvents().get(key1).get(j).getEventName();
						String day1 = m.getEvents().get(key1).get(j).getEventDay();
						String month = m.getEvents().get(key1).get(j).getEventMonth();
						String year = m.getEvents().get(key1).get(j).getEventYear();
						
						String[] time = m.getEvents().get(key1).get(j).getEventStartTime().split(":");
						int theTime = Integer.parseInt(time[0]);
						int theMinute = Integer.parseInt(time[1]);
						title = title + ": " + m.getEvents().get(key1).get(j).getEventStartTime() + "-" +m.getEvents().get(key1).get(j).getEventEndTime();
						GregorianCalendar treeKey = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day1),theTime,theMinute);
						eventTreeMap1.put(treeKey, title);
					}
					
					for(GregorianCalendar c : eventTreeMap1.keySet())
					{
						allEvents1 += eventTreeMap1.get(c) + '\n';
					}
				}
			    l.setText(Integer.toString(i)+"\n"+allEvents1);

			    
			    JScrollPane sp = new JScrollPane(l, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				monthPanel.add(sp);
				m.nextDay();
			}
			
		monthHeading.setLayout(new BoxLayout(monthPanel, BoxLayout.X_AXIS));
		monthPanel.setLayout(new GridLayout(0, 7,20,20));
		monthPanel.setBorder(new EmptyBorder(0,20,0,0));
        JScrollPane sp1 = new JScrollPane(monthPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        m.previousDay();
		for(int i = 1; i < m.getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH) - dayOfMonth2; i++)
		{
			m.previousDay();
		}
		return sp1;
	}
}
