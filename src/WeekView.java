import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.TreeMap;
import java.util.Calendar;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
//////--------------------------------------
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.JScrollPane;



/**
 * 
 *  Class:	WeekView
 *  Description: WeekView is a concrete class that belongs to the strategy implementation and 
 *  provide a specific algorithm to execute.
 * @author Eric, Yanpeng, Trung
 */
public class WeekView implements Strategy
{
    	private JPanel rightSIde = new JPanel();
    	private JTextPane tp1 = new  JTextPane();
    	private JTextPane tp2 = new  JTextPane();
    	private JTextPane tp3 = new  JTextPane();
    	private JTextPane tp4 = new  JTextPane();
    	private JTextPane tp5 = new  JTextPane();
    	private JTextPane tp6 = new  JTextPane();
    	private JTextPane tp7 = new  JTextPane();
    	Model m= new Model();
    	
		@Override
		/**
		 * 
		 */
		public JScrollPane selectedView (Model m)
		{
			this.m = m;
		int dayOfWeek = m.getCalendar().get((Calendar.DAY_OF_WEEK)) - 1;
		
		//////////to reach Sunday
		tp1.setBounds(0,0, 200, 100);
		for(int i = 0; i < dayOfWeek; i++)
		{
			m.previousDay();
		}
		TreeMap<GregorianCalendar, String> eventTreeMap1 =  new TreeMap<GregorianCalendar, String>();
		String key1 = m.getYear() + m.getMonth() + m.getDay();
		String allEvents1 = "Sun, " + m.getMonth() + "/" + m.getDay() + "/" + m.getYear() + '\n';
		if (m.getEvents().get(key1) != null) 
		{
			for (int j =0; j<= m.getEvents().get(key1).size()-1; j++)
			{
				String title = m.getEvents().get(key1).get(j).getEventName();
				String day = m.getEvents().get(key1).get(j).getEventDay();
				String month = m.getEvents().get(key1).get(j).getEventMonth();
				String year = m.getEvents().get(key1).get(j).getEventYear();
				
				String[] time = m.getEvents().get(key1).get(j).getEventStartTime().split(":");
				int theTime = Integer.parseInt(time[0]);
				int theMinute = Integer.parseInt(time[1]);
				title = title + ": " + m.getEvents().get(key1).get(j).getEventStartTime() + "-" +m.getEvents().get(key1).get(j).getEventEndTime();
				GregorianCalendar treeKey = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day),theTime,theMinute);
				eventTreeMap1.put(treeKey, title);
			}
			
			for(GregorianCalendar i : eventTreeMap1.keySet())
			{
				allEvents1 += eventTreeMap1.get(i) + '\n';
			}
		}
		tp1.setText(allEvents1);
		
		///////to reach Monday
		m.nextDay();
		tp2.setBounds(0,0, 200, 100);
		TreeMap<GregorianCalendar, String> eventTreeMap2 =  new TreeMap<GregorianCalendar, String>();
		String key2 = m.getYear() + m.getMonth() + m.getDay();
		String allEvents2 = "Mon, " + m.getMonth() + "/" + m.getDay() + "/" + m.getYear() + '\n';
		if (m.getEvents().get(key2) != null) 
		{
			for (int j =0; j<= m.getEvents().get(key2).size()-1; j++)
			{
				String title = m.getEvents().get(key2).get(j).getEventName();
				String day = m.getEvents().get(key2).get(j).getEventDay();
				String month = m.getEvents().get(key2).get(j).getEventMonth();
				String year = m.getEvents().get(key2).get(j).getEventYear();
				
				String[] time = m.getEvents().get(key2).get(j).getEventStartTime().split(":");
				int theTime = Integer.parseInt(time[0]);
				int theMinute = Integer.parseInt(time[1]);
				title = title + ": " + m.getEvents().get(key2).get(j).getEventStartTime() + "-" +m.getEvents().get(key2).get(j).getEventEndTime();
				GregorianCalendar treeKey = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day),theTime,theMinute);
				eventTreeMap2.put(treeKey, title);
			}
			
			for(GregorianCalendar i : eventTreeMap2.keySet())
			{
				allEvents2 += eventTreeMap2.get(i) + '\n';
			}
		}
		tp2.setText(allEvents2);
		
	///////to reach Tue
		m.nextDay();
		tp3.setBounds(0,0, 200, 100);
		TreeMap<GregorianCalendar, String> eventTreeMap3 =  new TreeMap<GregorianCalendar, String>();
		String key3 = m.getYear() + m.getMonth() + m.getDay();
		String allEvents3 = "Tue, " + m.getMonth() + "/" + m.getDay() + "/" + m.getYear() + '\n';
		if (m.getEvents().get(key3) != null) 
		{
			for (int j =0; j<= m.getEvents().get(key3).size()-1; j++)
			{
				String title = m.getEvents().get(key3).get(j).getEventName();
				String day = m.getEvents().get(key3).get(j).getEventDay();
				String month = m.getEvents().get(key3).get(j).getEventMonth();
				String year = m.getEvents().get(key3).get(j).getEventYear();
				
				String[] time = m.getEvents().get(key3).get(j).getEventStartTime().split(":");
				int theTime = Integer.parseInt(time[0]);
				int theMinute = Integer.parseInt(time[1]);
				title = title + ": " + m.getEvents().get(key3).get(j).getEventStartTime() + "-" +m.getEvents().get(key3).get(j).getEventEndTime();
				GregorianCalendar treeKey = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day),theTime,theMinute);
				eventTreeMap3.put(treeKey, title);
			}
			
			for(GregorianCalendar i : eventTreeMap3.keySet())
			{
				allEvents3 += eventTreeMap3.get(i) + '\n';
			}
		}
		tp3.setText(allEvents3);
			
	      ///////to reach Wed
		m.nextDay();
		tp4.setBounds(0,0, 200, 100);
		TreeMap<GregorianCalendar, String> eventTreeMap4 =  new TreeMap<GregorianCalendar, String>();
		String key4 = m.getYear() + m.getMonth() + m.getDay();
		String allEvents4 = "Wed, " + m.getMonth() + "/" + m.getDay() + "/" + m.getYear() + '\n';
		if (m.getEvents().get(key4) != null) 
		{
			for (int j =0; j<= m.getEvents().get(key4).size()-1; j++)
			{
				String title = m.getEvents().get(key4).get(j).getEventName();
				String day = m.getEvents().get(key4).get(j).getEventDay();
				String month = m.getEvents().get(key4).get(j).getEventMonth();
				String year = m.getEvents().get(key4).get(j).getEventYear();
				
				String[] time = m.getEvents().get(key4).get(j).getEventStartTime().split(":");
				int theTime = Integer.parseInt(time[0]);
				int theMinute = Integer.parseInt(time[1]);
				title = title + ": " + m.getEvents().get(key4).get(j).getEventStartTime() + "-" +m.getEvents().get(key4).get(j).getEventEndTime();
				GregorianCalendar treeKey = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day),theTime,theMinute);
				eventTreeMap4.put(treeKey, title);
			}
			
			for(GregorianCalendar i : eventTreeMap4.keySet())
			{
				allEvents4 += eventTreeMap4.get(i) + '\n';
			}
		}
		tp4.setText(allEvents4);
		
	
		   ///////to reach Thursday
		m.nextDay();
		tp5.setBounds(0,0, 200, 100);
		TreeMap<GregorianCalendar, String> eventTreeMap5 =  new TreeMap<GregorianCalendar, String>();
		String key5 = m.getYear() + m.getMonth() + m.getDay();
		String allEvents5 = "Thu, " + m.getMonth() + "/" + m.getDay() + "/" + m.getYear() + '\n';
		if (m.getEvents().get(key5) != null) 
		{
			for (int j =0; j<= m.getEvents().get(key5).size()-1; j++)
			{
				String title = m.getEvents().get(key5).get(j).getEventName();
				String day = m.getEvents().get(key5).get(j).getEventDay();
				String month = m.getEvents().get(key5).get(j).getEventMonth();
				String year = m.getEvents().get(key5).get(j).getEventYear();
				
				String[] time = m.getEvents().get(key5).get(j).getEventStartTime().split(":");
				int theTime = Integer.parseInt(time[0]);
				int theMinute = Integer.parseInt(time[1]);
				title = title + ": " + m.getEvents().get(key5).get(j).getEventStartTime() + "-" +m.getEvents().get(key5).get(j).getEventEndTime();
				GregorianCalendar treeKey = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day),theTime,theMinute);
				eventTreeMap5.put(treeKey, title);
			}
			
			for(GregorianCalendar i : eventTreeMap5.keySet())
			{
				allEvents5 += eventTreeMap5.get(i) + '\n';
			}
		}
		tp5.setText(allEvents5);
		
		
		   ///////to reach Friday
		m.nextDay();
		tp6.setBounds(0,0, 200, 100);
		TreeMap<GregorianCalendar, String> eventTreeMap6 =  new TreeMap<GregorianCalendar, String>();
		String key6 = m.getYear() + m.getMonth() + m.getDay();
		String allEvents6 = "Fri, " + m.getMonth() + "/" + m.getDay() + "/" + m.getYear() + '\n';
		if (m.getEvents().get(key6) != null) 
		{
			for (int j =0; j<= m.getEvents().get(key6).size()-1; j++)
			{
				String title = m.getEvents().get(key6).get(j).getEventName();
				String day = m.getEvents().get(key6).get(j).getEventDay();
				String month = m.getEvents().get(key6).get(j).getEventMonth();
				String year = m.getEvents().get(key6).get(j).getEventYear();
				
				String[] time = m.getEvents().get(key6).get(j).getEventStartTime().split(":");
				int theTime = Integer.parseInt(time[0]);
				int theMinute = Integer.parseInt(time[1]);
				title = title + ": " + m.getEvents().get(key6).get(j).getEventStartTime() + "-" +m.getEvents().get(key6).get(j).getEventEndTime();
				GregorianCalendar treeKey = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day),theTime,theMinute);
				eventTreeMap6.put(treeKey, title);
			}
			
			for(GregorianCalendar i : eventTreeMap6.keySet())
			{
				allEvents6 += eventTreeMap6.get(i) + '\n';
			}
		}
		tp6.setText(allEvents6);
		
		
		   ///////to reach Sat
		m.nextDay();
		tp7.setBounds(0,0, 200, 100);
		TreeMap<GregorianCalendar, String> eventTreeMap7 =  new TreeMap<GregorianCalendar, String>();
		String key7 = m.getYear() + m.getMonth() + m.getDay();
		String allEvents7 = "Sat, " + m.getMonth() + "/" + m.getDay() + "/" + m.getYear() + '\n';
		if (m.getEvents().get(key7) != null) 
		{
			for (int j =0; j<= m.getEvents().get(key7).size()-1; j++)
			{
				String title = m.getEvents().get(key7).get(j).getEventName();
				String day = m.getEvents().get(key7).get(j).getEventDay();
				String month = m.getEvents().get(key7).get(j).getEventMonth();
				String year = m.getEvents().get(key7).get(j).getEventYear();
				
				String[] time = m.getEvents().get(key7).get(j).getEventStartTime().split(":");
				int theTime = Integer.parseInt(time[0]);
				int theMinute = Integer.parseInt(time[1]);
				title = title + ": " + m.getEvents().get(key7).get(j).getEventStartTime() + "-" +m.getEvents().get(key7).get(j).getEventEndTime();
				GregorianCalendar treeKey = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day),theTime,theMinute);
				eventTreeMap7.put(treeKey, title);
			}
			
			for(GregorianCalendar i : eventTreeMap7.keySet())
			{
				allEvents7 += eventTreeMap7.get(i) + '\n';
			}
		}
		tp7.setText(allEvents7);
		
		
		
		
		for(int i = 0; i < (6-dayOfWeek); i++)
		{
			m.previousDay();
		}
		
		rightSIde.setLayout(new BoxLayout(rightSIde, BoxLayout.X_AXIS));
		rightSIde.add(tp1);
		rightSIde.add(tp2);
		rightSIde.add(tp3);
		rightSIde.add(tp4);
		rightSIde.add(tp5);
		rightSIde.add(tp6);
		rightSIde.add(tp7);
        rightSIde.setPreferredSize(new Dimension(2000, 100));
        
		return new JScrollPane(rightSIde, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
}
}