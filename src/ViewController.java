import java.util.ArrayList;
import java.util.TreeMap;

import java.util.GregorianCalendar;
import java.util.Calendar;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.io.Console;
import java.io.File;

import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JOptionPane;
//////////
import java.util.GregorianCalendar;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.event.ChangeEvent;
import java.io.ObjectInputStream;
import java.util.Calendar;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * fixed set of constants consisting of MONTHS
 */
enum MONTHS {
	January, February, March, April, May, June, July, August, September, October, November, December;
}

/**
 * fixed set of constants consisting of DAYS
 */
enum DAYS {
	Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
}
/**
 * @author Eric, Yanpeng, Trung
 * Class:	ViewController
 * Description:	ViewController Handles the GUI portion of our application it is
 * responsible for creating and visualizing all the components that the user will 
 * see and need to interact with the application. 
 * (Message prompts, GUI buttons, Diffrenet Views for Calendar and agenda etc. )
 **/
public class ViewController extends JFrame implements ChangeListener 
{
	private HashMap<String, ArrayList<Records>> events = new HashMap<>();
		
   	private String selectedColor  = "BLACK";
   	
   	
	private static final long serialVersionUID = 1L;
	private Model m;
	private int currentMonth;
	private int previousHighlight = -1;
	private JTextField textFieldforMonth;
	private JTextPane eventsArea = new JTextPane();
	private JTextField daysOfTheWeek = new JTextField("            " +"Sun                 Mon                 Tue                    Wed                    Thu                  Fri                      Sat");

	// DAY and MONTHS and calendarDays arrays to hold their respective values 
	private static DAYS[] arrayOfDays = DAYS.values();
	private static MONTHS[] arrayOfMonths = MONTHS.values();
	private ArrayList<JButton> calendarDays = new ArrayList<>();

	// Containers -----------------------
	private JPanel days = new JPanel();
	private JPanel leftSide = new JPanel();
	private JPanel rightSIde = new JPanel();
	private JPanel buttons = new JPanel();
	private JPanel newView = new JPanel();
	JPanel viewPanel = new JPanel();
	JPanel monthPanel = new JPanel();
	JPanel fileIOPanel = new JPanel();


	// text component -------------------------
	private JLabel labelOnTopOfEventsTextArea;
	// Used for  checking the current selected View  /// 8/4/17
	private int viewIndicator= 0;
	
	/**
	 * 
	 * @return
	 */
	public JPanel display(){
		
		rightSIde.removeAll();
		
		rightSIde.add(viewPanel);
		
		rightSIde.add(fileIOPanel);
		
		rightSIde.revalidate();
		
		rightSIde.repaint();
			
		return rightSIde;
			
		}
	
	/**
	 * Constroctor of ViewController that is responsible for the creation and
	 *  formating of all the GUI Components.
	 * @param m
	 */
	public ViewController(Model m) {
		this.m = m;
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		setPreferredSize(null);
		setTitle("CALENDAR");
		//currentMonth = m.getCalendar().get(Calendar.MONTH);
	    GregorianCalendar g = new GregorianCalendar();
	    currentMonth = g.get(Calendar.MONTH);
		String weekDay = "" + arrayOfDays[m.getCalendar().get((Calendar.DAY_OF_WEEK))-1];
		labelOnTopOfEventsTextArea = new JLabel(weekDay + " " + m.getMonth() + "/" + m.getDay());
		monthLabel();
		displayDaysOfTheWeek();
		daysOfMonths();
		for (JButton b : calendarDays) {
			days.add(b);
		}

		// Create Buttons and Attach their respective Listeners 

		// Create Button
		JButton createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createFrame();
				m.update();
			}
		});
		// Quit Button \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.save();
				System.exit(0);
			}
		});
		// Previous day button \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		JButton leftButton = new JButton("<");
		leftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.previousDay();
				if( viewIndicator == 0 )//Day View
				{
					m.update();
				}
				else if (viewIndicator == 1 )//Week View
				{
					m.update();
					rightSIde.removeAll();
					rightSIde.add(viewPanel);
					Strategy weekView = new WeekView();
					ViewOptions viewOptions = new ViewOptions(weekView);
					rightSIde.add(viewOptions.getResult(m));
					
					rightSIde.add(fileIOPanel);
					rightSIde.revalidate();
					rightSIde.repaint();
				}
				else //Month View
				{
					m.update();
					rightSIde.removeAll();
					rightSIde.add(viewPanel);

					Strategy monthView = new MonthView();
					ViewOptions viewOptions = new ViewOptions(monthView);
					rightSIde.add(viewOptions.getResult(m));

					rightSIde.add(fileIOPanel);
					setVisible(true);

					rightSIde.revalidate();
					rightSIde.repaint();
				}

			}
		});
		// Forward day button \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		JButton rightButton = new JButton(">");
		rightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.nextDay();
				if( viewIndicator == 0 )//Day View
				{
					m.update();
				}
				else if (viewIndicator == 1 )//Week View
				{
					m.update();
					rightSIde.removeAll();
					rightSIde.add(viewPanel);
					Strategy weekView = new WeekView();
					ViewOptions viewOptions = new ViewOptions(weekView);
					rightSIde.add(viewOptions.getResult(m));
					
					rightSIde.add(fileIOPanel);
					rightSIde.revalidate();
					rightSIde.repaint();
				}
				else //Month View
				{
					m.update();
					rightSIde.removeAll();
					rightSIde.add(viewPanel);

					Strategy monthView = new MonthView();
					ViewOptions viewOptions = new ViewOptions(monthView);
					rightSIde.add(viewOptions.getResult(m));

					rightSIde.add(fileIOPanel);
					setVisible(true);

					rightSIde.revalidate();
					rightSIde.repaint();
				}
			
			}
		});
		
		

		// Previous week button \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		JButton PreviousWeekButton = new JButton("<<");
		PreviousWeekButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.previousWeek();
				if( viewIndicator == 0 )//Day View
				{
					m.update();
				}
				else if (viewIndicator == 1 )//Week View
				{
					m.update();
					rightSIde.removeAll();
					rightSIde.add(viewPanel);
					Strategy weekView = new WeekView();
					ViewOptions viewOptions = new ViewOptions(weekView);
					rightSIde.add(viewOptions.getResult(m));
					
					rightSIde.add(fileIOPanel);
					rightSIde.revalidate();
					rightSIde.repaint();
				}
				else //Month View
				{
					m.update();
					rightSIde.removeAll();
					rightSIde.add(viewPanel);

					Strategy monthView = new MonthView();
					ViewOptions viewOptions = new ViewOptions(monthView);
					rightSIde.add(viewOptions.getResult(m));

					rightSIde.add(fileIOPanel);
					setVisible(true);

					rightSIde.revalidate();
					rightSIde.repaint();
				}
			
			}
		});
		
		
		// Forward week button \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		JButton rightWeekButton = new JButton(">>");
		rightWeekButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.nextWeek();
				if( viewIndicator == 0 )//Day View
				{
					m.update();
				}
				else if (viewIndicator == 1 )//Week View
				{
					m.update();
					rightSIde.removeAll();
					rightSIde.add(viewPanel);
					Strategy weekView = new WeekView();
					ViewOptions viewOptions = new ViewOptions(weekView);
					rightSIde.add(viewOptions.getResult(m));
					
					rightSIde.add(fileIOPanel);
					rightSIde.revalidate();
					rightSIde.repaint();
				}
				else //Month View
				{
					m.update();
					rightSIde.removeAll();
					rightSIde.add(viewPanel);

					Strategy monthView = new MonthView();
					ViewOptions viewOptions = new ViewOptions(monthView);
					rightSIde.add(viewOptions.getResult(m));

					rightSIde.add(fileIOPanel);
					setVisible(true);

					rightSIde.revalidate();
					rightSIde.repaint();
				}
			
			}
		});
		
		
		// Previous month button
		JButton prevMonthButton = new JButton("<<<");
		prevMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.previousMonth();
				if( viewIndicator == 0 )//Day View
				{
					m.update();
				}
				else if (viewIndicator == 1 )//Week View
				{
					m.update();
					rightSIde.removeAll();
					rightSIde.add(viewPanel);
					
					Strategy weekView = new WeekView();
					ViewOptions viewOptions = new ViewOptions(weekView);;
					rightSIde.add(viewOptions.getResult(m));
					
					rightSIde.add(fileIOPanel);
					rightSIde.revalidate();
					rightSIde.repaint();
				}
				else //Month View
				{
					m.update();
					rightSIde.removeAll();
					rightSIde.add(viewPanel);

					Strategy monthView = new MonthView();
					ViewOptions viewOptions = new ViewOptions(monthView);
					rightSIde.add(viewOptions.getResult(m));

					rightSIde.add(fileIOPanel);
					setVisible(true);

					rightSIde.revalidate();
					rightSIde.repaint();
				}
			
			}
		});
		// Next month button
		JButton nextMonthButton = new JButton(">>>");
		nextMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.nextMonth();
				if( viewIndicator == 0 )//Day View
				{
					m.update();
				}
				else if (viewIndicator == 1 )//Week View
				{
					m.update();
					rightSIde.removeAll();
					rightSIde.add(viewPanel);
					Strategy weekView = new WeekView();
					ViewOptions viewOptions = new ViewOptions(weekView);
					rightSIde.add(viewOptions.getResult(m));
					
					rightSIde.add(fileIOPanel);
					rightSIde.revalidate();
					rightSIde.repaint();
				}
				else //Month View
				{
					m.update();
					rightSIde.removeAll();
					rightSIde.add(viewPanel);

					Strategy monthView = new MonthView();
					ViewOptions viewOptions = new ViewOptions(monthView);
					rightSIde.add(viewOptions.getResult(m));

					rightSIde.add(fileIOPanel);
					setVisible(true);

					rightSIde.revalidate();
					rightSIde.repaint();
				}
			}
		});
		//Current day button
		JButton currentDayButton = new JButton("Current Day");
		currentDayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( viewIndicator == 0 )//Day View
				{
					rightSIde.removeAll();
					rightSIde.add(viewPanel);
					
					m.today();
					m.update();
					rightSIde.add(labelOnTopOfEventsTextArea);
					rightSIde.add(eventsArea);
					
					rightSIde.add(fileIOPanel);
					rightSIde.revalidate();
					rightSIde.repaint();
				}
				else if (viewIndicator == 1 )//Week View
				{
					m.today();
					m.update();
					rightSIde.removeAll();
					rightSIde.add(viewPanel);
					
					Strategy weekView = new WeekView();
					ViewOptions viewOptions = new ViewOptions(weekView);
					rightSIde.add(viewOptions.getResult(m));
					
					rightSIde.add(fileIOPanel);
					rightSIde.revalidate();
					rightSIde.repaint();
				}
				else //Month View
				{
					m.today();
					m.update();
					rightSIde.removeAll();
					rightSIde.add(viewPanel);

					Strategy monthView = new MonthView();
					ViewOptions viewOptions = new ViewOptions(monthView);
					rightSIde.add(viewOptions.getResult(m));

					rightSIde.add(fileIOPanel);
					setVisible(true);

					rightSIde.revalidate();
					rightSIde.repaint();
				}
			}
		});
		// Day View Button
		JButton dayViewButton = new JButton("Day");
		dayViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewIndicator = 0;////////////////////////////////8/4/2017

				rightSIde.removeAll();
				m.update();
				rightSIde.add(viewPanel);
				rightSIde.add(labelOnTopOfEventsTextArea);
				rightSIde.add(eventsArea);
				rightSIde.add(fileIOPanel);
				
				rightSIde.revalidate();
				rightSIde.repaint();
			}
		});
		// Week View Button
		JButton weekViewButton = new JButton("Week");
		weekViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewIndicator = 1;////////////////////////////////8/4/2017
				
				rightSIde.removeAll();
				rightSIde.add(viewPanel);

				Strategy weekView = new WeekView();
				ViewOptions viewOptions = new ViewOptions(weekView);
				rightSIde.add(viewOptions.getResult(m));
				
				rightSIde.add(fileIOPanel);
				rightSIde.revalidate();
				rightSIde.repaint();

			}
		});
		// Month View Button
		JButton monthViewButton = new JButton("Month");//////8/4/2017
		monthViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//display();
				viewIndicator = 2;////////////////////////////////8/4/2017

				rightSIde.removeAll();
				rightSIde.add(viewPanel);

				Strategy monthView = new MonthView();
				ViewOptions viewOptions = new ViewOptions(monthView);
				rightSIde.add(viewOptions.getResult(m));

				rightSIde.add(fileIOPanel);
				setVisible(true);

				rightSIde.revalidate();
				rightSIde.repaint();
			}
		});
		// Agenda View Button
		////YangPeng 8/4/2017
		JButton agendaViewButton = new JButton("Agenda");
		agendaViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				rightSIde.removeAll();
				rightSIde.add(viewPanel);

				rightSIde.add(agendaWindow());
			

				rightSIde.add(fileIOPanel);

				rightSIde.revalidate();
				rightSIde.repaint();
			}
		});
		// From File Button
		JButton fromFileButton = new JButton("From File");
		fromFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.load();
				m.update();///////////////////////////////////////////////////////////8/3/2017 11:13AM

			}
		});

		

		
		// JPanel for quit & From File Button
		fileIOPanel.add(fromFileButton);
		fileIOPanel.add(quitButton);
		// JPanel for dayViewButton,weekViewButton,monthViewButton,agendaViewButton
		viewPanel.add(dayViewButton);
		viewPanel.add(weekViewButton);
		viewPanel.add(monthViewButton);
		viewPanel.add(agendaViewButton);
		// Setters for each component \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		days.setLayout(new GridLayout(0, 7));
		leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.Y_AXIS));
		monthPanel.setLayout(new GridLayout(1, 7));
		monthPanel.setPreferredSize(new Dimension (700,350));
		rightSIde.setLayout(new BoxLayout(rightSIde, BoxLayout.Y_AXIS));

		days.setPreferredSize(new Dimension(350, 200));
		labelOnTopOfEventsTextArea.setPreferredSize(new Dimension(150, 25));
		eventsArea.setPreferredSize(new Dimension(600, 350));
		// Best for resolution on Yangpeng
		leftSide.setPreferredSize(new Dimension(700, 350));
		// Best for resolution on Eric's Laptop
		//leftSide.setPreferredSize(new Dimension(350, 350));
		rightSIde.setPreferredSize(new Dimension(700, 350));//////////////////////////////////////8/3/2017

		//rightSIde.setPreferredSize(new Dimension(350, 350));
		eventsArea.setEditable(false);

		labelOnTopOfEventsTextArea.setFont(new Font("Times", Font.PLAIN, 20));
		// Adders for each component \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		buttons.add(prevMonthButton);
		buttons.add(PreviousWeekButton);
		buttons.add(leftButton);
		buttons.add(rightButton);
		buttons.add(rightWeekButton);
		buttons.add(nextMonthButton);
		buttons.add(currentDayButton);
		buttons.add(createButton);
		leftSide.add(buttons);
		leftSide.add(textFieldforMonth);
		leftSide.add(daysOfTheWeek);
		leftSide.add(days);
		rightSIde.add(viewPanel);
		rightSIde.add(labelOnTopOfEventsTextArea);
		rightSIde.add(eventsArea);
		rightSIde.add(fileIOPanel);
		rightSIde.add(monthPanel);
		add(leftSide);
		add(rightSIde);

		dayClicked(m.getCalendar().get(Calendar.DAY_OF_MONTH));
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}


	/**
	 * Displays events for highlighted day on the TextBox on the right 
	 * 
	 * @param currentDay 
	 */
	public void dayClicked(int currentDay) 
	{
		m.getCalendar().set(Calendar.DAY_OF_MONTH, currentDay);
		if (viewIndicator == 0)
		{
			rightSIde.removeAll();////////////////////////////////8/3/2017
			rightSIde.add(viewPanel);/////////////////////////////8/3/2017
			
		
			TreeMap<GregorianCalendar, String> eventTreeMap =  new TreeMap<GregorianCalendar, String>();
			String key1 = m.getYear() + m.getMonth() + m.getDay();
			String allEvents = "";
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
					
					title = title + "-------" + m.getEvents().get(key1).get(j).getEventStartTime() + "-" +m.getEvents().get(key1).get(j).getEventEndTime();
					GregorianCalendar treeKey = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day),theTime,theMinute);
					eventTreeMap.put(treeKey, title);
				}
				
				for(GregorianCalendar i : eventTreeMap.keySet())
				{
					allEvents += eventTreeMap.get(i) + '\n';
				}
			}

			String weekDay = "" + arrayOfDays[m.getCalendar().get((Calendar.DAY_OF_WEEK)) - 1];
			labelOnTopOfEventsTextArea.setText(weekDay + ", " + m.getMonth() + "/" + m.getDay());

			if (allEvents.isEmpty()) {
				eventsArea.setText("No Events Scheduled For Today ");
			} else {
				eventsArea.setText(allEvents);
			}

	
			
			labelOnTopOfEventsTextArea.setText(weekDay + ", " + m.getMonth() + "/" + m.getDay());
			highlight(m.getCalendar().get(Calendar.DAY_OF_MONTH) - 1);
			
			
			rightSIde.add(labelOnTopOfEventsTextArea);/////////////////////////////
			rightSIde.add(eventsArea);////////////////////////////////
			
			rightSIde.add(fileIOPanel);////////////////////////
			rightSIde.revalidate();///////////////////////////
			rightSIde.repaint();/////////////////////////////
		}
		else if(viewIndicator == 1)
		{
			rightSIde.removeAll();
			rightSIde.add(viewPanel);

			Strategy weekView = new WeekView();
			ViewOptions viewOptions = new ViewOptions(weekView);
			rightSIde.add(viewOptions.getResult(m));
			
			rightSIde.add(fileIOPanel);
			rightSIde.revalidate();
			rightSIde.repaint();
		}
		else //viewIndicator == 2
		{
			rightSIde.removeAll();
			rightSIde.add(viewPanel);

			Strategy monthView = new MonthView();
			ViewOptions viewOptions = new ViewOptions(monthView);
			rightSIde.add(viewOptions.getResult(m));

			rightSIde.add(fileIOPanel);
			setVisible(true);

			rightSIde.revalidate();
			rightSIde.repaint();
		}
	}

	/**
	 * StateChanged is notified when the user makes a change and revisualizes the GUI component that 
	 * has recived a change. 
	 * @param e
	 */
	public void stateChanged(ChangeEvent e) {
		if (m.getCalendar().get(Calendar.MONTH) == currentMonth){
			int currentDay = m.getCalendar().get(Calendar.DAY_OF_MONTH);
			dayClicked(currentDay);
			highlight(currentDay - 1);
		} else {
			currentMonth = m.getCalendar().get(Calendar.MONTH);
			textFieldforMonth.setText(arrayOfMonths[m.getCalendar().get(Calendar.MONTH)] + " " + m.getCalendar().get(Calendar.YEAR));
			days.removeAll();
			leftSide.remove(days);
			calendarDays.clear();
			daysOfMonths();
			previousHighlight = -1;

			for (JButton b : calendarDays) {
				days.add(b);
			}

			leftSide.add(days);

			highlight(m.getCalendar().get(Calendar.DAY_OF_MONTH) - 1);
			dayClicked(m.getCalendar().get(Calendar.DAY_OF_MONTH));
			repaint();

		}
	}

	/**
	 * Filler buttons to fill empty spots before the initial start day of the current month. 
	 * This give the Calendar view proper formating so that if a month has a day that starts mid week the 
	 * Calendar will visualize it properly.
	 * 
	 */
	public void fillerDays() {
		GregorianCalendar temp = (GregorianCalendar) m.getCalendar().clone();
		temp.set(Calendar.DAY_OF_MONTH, 1);
		int empty = temp.get(Calendar.DAY_OF_WEEK);
		for (int i = 1; i < empty; i++) {JButton fillerButton = new JButton();
			fillerButton.setEnabled(false);
			fillerButton.setBorder(BorderFactory.createEmptyBorder());days.add(fillerButton);

		}
	}

	/**
	 * Generates each day of the month and puts it into ArrayList of Buttons.
	 * Used for the calendar Month view on the left side of the application. 
	 */
	public void daysOfMonths() {
		final int max = m.getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);


		for (int i = 1; i <= max; i++) {
			final int k = i;
			JButton ithDay = new JButton("" + (i));
			ithDay.setOpaque(true);
			ithDay.setBackground(Color.LIGHT_GRAY);

			ithDay.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dayClicked(k);
					highlight(k - 1);
				}
			});

			calendarDays.add(ithDay);
		}
		fillerDays();

	}

	/**
	 * highlights the current day of the current month used though out the program 
	 * as refrence to the current day.
	 */
	public void highlight(int dayNumber) {
		calendarDays.get(dayNumber).setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
		if (previousHighlight != dayNumber && previousHighlight != -1) {JButton t = new JButton();
			calendarDays.get(previousHighlight).setBorder(t.getBorder());
		}
		previousHighlight = dayNumber;
	}

	/**
	 *   Displays JTextfield with label that contains  month & year
	 */
	public void monthLabel() {
		textFieldforMonth = new JTextField(arrayOfMonths[m.getCalendar().get(Calendar.MONTH)] + " " + m.getCalendar().get(Calendar.YEAR));
		textFieldforMonth.setHorizontalAlignment(JTextField.CENTER);
		textFieldforMonth.setOpaque(false);
		textFieldforMonth.setEditable(false);
		textFieldforMonth.setFont(new Font("Times", Font.PLAIN, 16));
		textFieldforMonth.setBorder(null);
	}

	/**
	 * Displays days of the week  [ S M T W T F S]
	 */
	public void displayDaysOfTheWeek() {
		daysOfTheWeek.setEditable(false);daysOfTheWeek.setBorder(null);
		daysOfTheWeek.setOpaque(false);daysOfTheWeek.setPreferredSize(new Dimension(350, 40));
		daysOfTheWeek.setSize(daysOfTheWeek.getPreferredSize());daysOfTheWeek.setFont(new Font("Times", Font.PLAIN, 20));
	}

	/**
	 * frame produced when client clicks on create Button.
	 * Contains GUI components that allow the user to enter Event info
	 * and save event into the Calenda application.
	 */
	public void createFrame() {

		JFrame createFrame = new JFrame("NEW EVENT");
		JPanel container = new JPanel();
		JPanel eventPanel = new JPanel();
		JPanel startPanel = new JPanel();
		JPanel endTimePanel = new JPanel();
		JLabel dateView = new JLabel( m.getMonth() + "/" + m.getDay() + "/" + m.getYear());
		JLabel eventNameLabel = new JLabel("Title: ");
		JLabel startLabel = new JLabel("Start: ");
		JLabel endTimeLabel = new JLabel("End: ");
		JTextField eventNameText = new JTextField("", 20);
		JTextField startText = new JTextField("", 20);
		JTextField endTimeText = new JTextField("", 20);



		JButton saveButton = new JButton("Schedule it!");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (eventNameText.getText().isEmpty()) {
					emptyTitle();
					createFrame.dispose();
				}
				else if (!eventNameLabel.getText().isEmpty()) {
					String eventName = eventNameText.getText();
					String startTime = startText.getText();
					String endTime = endTimeText.getText();

					if (m.eventTimeConflict(startTime, endTime)) {
						timeConflict();
					} 
					else {
						String newDate = m.getMonth() + "/" + m.getDay() + "/" + m.getYear();
						m.newEvent(eventName, newDate, startTime, endTime);
						dayClicked(m.getCalendar().get(Calendar.DAY_OF_MONTH));
						createFrame.dispose();
					}
				}
			}

		});
		//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		createFrame.setPreferredSize(new Dimension(342, 214));
		endTimePanel.setPreferredSize(new Dimension(10, 20));
		eventPanel.setPreferredSize(new Dimension(10, 20));
		startPanel.setPreferredSize(new Dimension(10, 20));
		dateView.setPreferredSize(new Dimension(150, 60));
		//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		createFrame.setLayout(new BorderLayout());
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		dateView.setHorizontalAlignment(JTextField.CENTER);
		eventPanel.setLayout(new FlowLayout());
		eventNameLabel.setHorizontalAlignment(JTextField.LEFT);
		eventNameLabel.setHorizontalAlignment(JLabel.LEFT);
		saveButton.setOpaque(true);
		saveButton.setForeground(Color.RED);
		saveButton.setFont(new Font("Times", Font.BOLD, 16));
		dateView.setFont(new Font("Times", Font.PLAIN, 20));
		//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		eventPanel.add(eventNameLabel);
		eventPanel.add(eventNameText);
		startPanel.add(startLabel);
		startPanel.add(startText);
		createFrame.add(dateView, BorderLayout.NORTH);
		container.add(eventPanel);
		container.add(startPanel);
		container.add(endTimePanel);
		endTimePanel.add(endTimeLabel);
		endTimePanel.add(endTimeText);
		createFrame.add(container, BorderLayout.CENTER);
		createFrame.add(saveButton, BorderLayout.SOUTH);

		createFrame.pack();
		createFrame.setVisible(true);
	}
	/**
	 * This method will prodeuces a GUI Componet in the form of a window that gives the user the 
	 * ability to search for recurring event and print out all events within specified date.
	 */
     public JScrollPane agendaWindow(){
		TreeMap<GregorianCalendar, String> eventTreeMap =  new TreeMap<GregorianCalendar, String>();
		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		Object[] fields = {
				"Start date", field1, 
				"End date", field2
		};
	
		JTextPane agendaPane = new JTextPane();
		JOptionPane.showConfirmDialog(null,fields,"Agenda Time Period",JOptionPane.DEFAULT_OPTION);
	 try {
		

		String[] startArray = field1.getText().split("/");
		String startMonth = startArray[0];
		String startDay = startArray[1];
		String startYear = startArray[2];
		
		String[] endArray = field2.getText().split("/");
		String endMonth = endArray[0];
		String endDay = endArray[1];
		String endYear = endArray[2];
		
	
		for(String i : m.getEvents().keySet()){//
			for(int j = 0; j <= m.getEvents().get(i).size()-1;j++){
				
				    int eventYear = Integer.parseInt(m.getEvents().get(i).get(j).getEventYear());
				    
				    int eventMonth = Integer.parseInt(m.getEvents().get(i).get(j).getEventMonth());
				   
				    int eventDay = Integer.parseInt(m.getEvents().get(i).get(j).getEventDay());
				  	
				    GregorianCalendar theStartDay = new GregorianCalendar(Integer.parseInt(startYear),Integer.parseInt(startMonth)-1,Integer.parseInt(startDay));
				    GregorianCalendar theEndDay = new GregorianCalendar(Integer.parseInt(endYear),Integer.parseInt(endMonth)-1,Integer.parseInt(endDay));
					GregorianCalendar theEventDay = new GregorianCalendar(eventYear,eventMonth -1,eventDay);
					
					if(theStartDay.compareTo(theEventDay)==0 || theStartDay.compareTo(theEventDay)< 0){
						if(theEndDay.compareTo(theEventDay)==0 || theEndDay.compareTo(theEventDay)>0){
						
							String name = m.getEvents().get(i).get(j).getEventName();
							
							String day = m.getEvents().get(i).get(j).getEventDay();
							
							String month = m.getEvents().get(i).get(j).getEventMonth();
							
							String year = m.getEvents().get(i).get(j).getEventYear();
							
							String[] time = m.getEvents().get(i).get(j).getEventStartTime().split(":");
							
							int theTime = Integer.parseInt(time[0]);
							
							int theMinute = Integer.parseInt(time[1]);
							
							String theEvent = month + "/" + day + "/" + year + ": " + name + "    " + m.getEvents().get(i).get(j).getEventStartTime() + "--->" +m.getEvents().get(i).get(j).getEventEndTime() ;
							
							GregorianCalendar treeKey = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day),theTime,theMinute);
							
							eventTreeMap.put(treeKey, theEvent);
				
							
					}
				}
			}
		}

		
			
		agendaPane.setEditable(false);
		String total = "";
		
		for(GregorianCalendar i : eventTreeMap.keySet()){
			
			total += eventTreeMap.get(i) + '\n';
		}
		agendaPane.setText(total);
		
		JFrame f= new JFrame();
		String [] color = {"RED", "BLUE", "GREEN"};
		JComboBox cb = new JComboBox(color);
		f.add(cb);
		f.pack();
		f.setVisible(true);
		//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cb.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
						JComboBox cb1 = (JComboBox) e.getSource();
						selectedColor = (String) cb1.getSelectedItem();
						
						if(selectedColor.equals("RED"))
				 		{
				 			StrategyTwo colorView = new Red();
				 			ColorContext c1 = new ColorContext(colorView);

				 			c1.getColorResult(agendaPane);
				 			
				 		}
				 		else if(selectedColor.equals("BLUE"))
				 		{
				 			StrategyTwo colorView = new Blue();
				 			ColorContext c2 = new ColorContext(colorView);
				 		
				 			c2.getColorResult(agendaPane);
				 		}
				 		else if(selectedColor.equals("GREEN"))
				 		{
				 			StrategyTwo colorView = new Green();
				 			ColorContext c3 = new ColorContext(colorView);
				 			
				 			c3.getColorResult(agendaPane);
				 		}
					}
				});

    
    } 
	catch ( ArrayIndexOutOfBoundsException error) 
	 {
       
     }
		return new JScrollPane(agendaPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
}

	/**
	 * JFrame displays when user does not enter a title for Event 
	 */
	public void emptyTitle() {
		JFrame emptyTitle = new JFrame();
		JOptionPane.showMessageDialog(emptyTitle,"PLEASE ENTER A TITLE FOR EVENT","WARNING",JOptionPane.WARNING_MESSAGE);
	}

	/**
	 *  JFrame  displays when there is a time conflict 
	 */
	public void timeConflict() {
		JFrame timeConflict = new JFrame();
		JOptionPane.showMessageDialog(timeConflict,"Another event during this time","Event Conflict Warning",JOptionPane.WARNING_MESSAGE);

	}
	/**
	 * 
	 */
	public void emptyAgenda(){
		JFrame emptyAgenda = new JFrame();
		JOptionPane.showMessageDialog(emptyAgenda,"Please ","Event Conflict Warning",JOptionPane.WARNING_MESSAGE);

	}
	

}