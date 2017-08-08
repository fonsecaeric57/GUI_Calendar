import java.util.GregorianCalendar;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import java.io.ObjectInputStream;
import java.util.Calendar;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class: Model
 * Description: This class is the model portion of the MVC and contains the data models and algorithms
 * It will store a calendar, HashMap of events, and a ArrayList of listeners
 * It is allso responsible for Saving and loading events 
 * @author Eric, Yanpeng, Trung
 *
 */
public class Model 
{
	// Underlyining Data Structures 
	private ArrayList<ChangeListener> listeners = new ArrayList<>();
	private HashMap<String, ArrayList<Records>> events = new HashMap<>();
	private GregorianCalendar calendar = new GregorianCalendar();
	private Calendar gc = GregorianCalendar.getInstance();

	/**
	 * Gets Calendar Model
	 * @return GregorianCalendar  
	 */
	public GregorianCalendar getCalendar()
	{ return calendar; }

	/**
	 * Saves events into eventsRecords.ser
	 */
	public void save()
	{
			try{
				FileOutputStream saveFile = new FileOutputStream("eventsRecords.ser");
				ObjectOutputStream out = new ObjectOutputStream(saveFile);
				out.writeObject(events);
				out.close();
				saveFile.close();
			}
			catch (IOException i) {
				i.printStackTrace();
			}
	}
	/**
	 * loads events from input.txt
	 */
	public void load()
	{
		
		try {
			FileInputStream loadFile = new FileInputStream("eventsRecords.ser");
			ObjectInputStream in = new ObjectInputStream(loadFile);
			events = (HashMap<String, ArrayList<Records>>) in.readObject();
			in.close();
			loadFile.close();
		}
		catch(IOException i) { 
			return;
		}
		catch(ClassNotFoundException c) {
			return;
		}
		
            try
            {
                Scanner in = new Scanner(new File("input.txt"));
                while (in.hasNextLine())
                {
                     setLineIntoEvent(in.nextLine());
                }
            }
            catch (FileNotFoundException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }         
    }

	/**
	 * divides a line of String into elements of a event and calls create an event method
	 * @param str
	 */
	public void setLineIntoEvent(String str) 
	{
		String[] startArray = str.split(";");
		String title = startArray[0];
		int year = Integer.parseInt(startArray[1]);
		int startingMonth = Integer.parseInt(startArray[2]);
		int endingMonth = Integer.parseInt(startArray[3]);
		String dayOfTheWeek = startArray[4];
		String startingTime = startArray[5];
		String endingTime = startArray[6];

		for (int month = startingMonth; month <= endingMonth; month++) 
		{
			gc.set(year, month - 1, 1);
			for (int day = 1; day <= gc.getActualMaximum(Calendar.DAY_OF_MONTH); day++) 
			{
				if ( dayOfTheWeek.contains(getDayOfWeek(year, month, day)) ) 
				{
					String date = Integer.toString(month) + "/" + Integer.toString(day)+ "/"+ Integer.toString(year) ;
					newEvent(title, date, startingTime, endingTime);
				}
			}
		}
	}
	
	/**
	 * gets the day of the week, given a specific date
	 * @param year
	 * @param month
	 * @param day
	 * @return the day of the week
	 * @precondition days will be a sequence of day abbreviations, SMTWHFA, without any space in it. Days can be any order. No space is allowed in days
	 */
	private String getDayOfWeek(int year, int month, int day) 
	{
		gc.set(year, month - 1, day);
        if (gc.get(Calendar.DAY_OF_WEEK) == 1)
        {
        		return "S";
        }
        else if (gc.get(Calendar.DAY_OF_WEEK) == 2)
        {
        		return "M";	
        }
        else if (gc.get(Calendar.DAY_OF_WEEK) == 3)
        {
        		return "T";
        }
        else if (gc.get(Calendar.DAY_OF_WEEK) == 4)
        {
        		return "W";
        }
        else if (gc.get(Calendar.DAY_OF_WEEK) == 5)
        {
        		return "H";
        }
        else if (gc.get(Calendar.DAY_OF_WEEK) == 6)
        {	
        		return "F";
        }
        else if (gc.get(Calendar.DAY_OF_WEEK) == 7)
        {
        		return "A";
        }
        return null;
    }



	/**
	 * Model constructor 
	 * loads events from file 
	 */
	public Model() { }

	/**
	 * attaches ChangeListener to model 
	 * @param l ChangeListener
	 */
	public void attach(ChangeListener l){ listeners.add(l); }

	/**
	 * All listeners update 
	 */
	public void update(){
		for (ChangeListener l : listeners){
			l.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * Creates a new event with given information
	 * @param eventName  event name
	 * @param date 		 event date
	 * @param startTime  event start time 
	 * @param endTime    event end time
	 */
	public void newEvent(String eventName, String date, String startTime, String endTime){
		Records event = new Records(eventName, date, startTime, endTime);
		String Key = event.getEventYear() + event.getEventMonth() + event.getEventDay();
		ArrayList<Records> eventArrayList = events.get(Key);
		if (eventArrayList == null){
			eventArrayList = new ArrayList<>();
		}
		eventArrayList.add(event);
		events.put(Key, eventArrayList);
	}

	/**
	 * Retrieves Events that belong to highlighted day 
	 * @param date the date to get all events for
	 * @return bulkEvents (Events of day )
	 */
	public String dayEvents(String date){
		// removes the forward slash from the date 
		String[] split = date.split("/");
		String eventMonth = split[0];
		String eventDay = split[1];
		String eventYear = split[2];
		// key used for date purposes 
		String Key = eventYear + eventMonth + eventDay;
		String bulkEvents = "";
		
		
		ArrayList<Records> dayEvents = events.get(Key);
		Collections.sort(dayEvents);
		for (Records subEvent : dayEvents ){
			bulkEvents = bulkEvents + subEvent.getEventName()+ "------->"  + subEvent.getEventStartTime() + " - " +  subEvent.getEventEndTime() + "\n";
		}
		return bulkEvents;
	}

	/**
	 * Moves the highlighted day on the calendar by one
	 */
	public void nextDay(){	calendar.add(Calendar.DAY_OF_MONTH, 1);	}

	/**
	 * Moves the highlighted day on the calendar by one 
	 */
	public void previousDay(){	calendar.add(Calendar.DAY_OF_MONTH, -1);	}
	
	public void previousWeek()
	{
		calendar.add(Calendar.DAY_OF_MONTH, -7);
	}
	
	public void nextWeek()
	{
		calendar.add(Calendar.DAY_OF_MONTH, 7);
	}
	/**
	 * Moves to the next month of the calendar 
	 */
	public void nextMonth(){ calendar.add(Calendar.MONTH, 1); }

	/**
	 * Moves to the previous month of the calendar
	 */
	public void previousMonth(){ calendar.add(Calendar.MONTH, -1); }
	/**
	 * Gets year in 4 digit String form
	 */
	public String getYear(){
		String yearStringForm = "" + calendar.get(Calendar.YEAR);
			return yearStringForm;	
		}


	/**
	 * Gets month in 2 digit String form 
	 */
	public String getMonth(){
		String monthStringForm = "" + (calendar.get(Calendar.MONTH)+1);
		return monthStringForm;
	}
	
	/**
	 * Gets today
	 */
	public void today(){
		calendar = new GregorianCalendar();
	}
	/**
	 * Gets day in 2 digit String form 
	 */
	public String getDay(){
		String day = "" + calendar.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * Gets events from HashMap
	 */
	public HashMap<String, ArrayList<Records>> getEvents(){	return events;	}

	/**
	 * Checks  time conflict between new events and old events 
	 * @param event_startTime Event start time
	 * @param event_endTime Event end time
	 */
	public boolean eventTimeConflict(String event_startTime, String event_endTime)
	{
		String date = getYear() + getMonth() + getDay();
		ArrayList<Records> eventsForSelectedDay = events.get(date);
		String startTime = event_startTime.replace(":", "");
		int startTime1 = Integer.parseInt(startTime);
		String endTime = event_endTime.replace(":", "");
		int endTime1 = Integer.parseInt(endTime);
		
		if (eventsForSelectedDay == null) {	return false;	}

			for (Records dayEvents : eventsForSelectedDay){
				if(startTime1 >= dayEvents.getEventStartTimeInt() && startTime1 < dayEvents.getEventEndTimeInt()){
					return true;
				}
				if (startTime1 <= dayEvents.getEventStartTimeInt() && endTime1 >= dayEvents.getEventStartTimeInt()){
					return true;
				}
			}
		return false;
	}
}
