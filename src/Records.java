import java.io.Serializable;

/**
 * Class: Records
 * Description: Records class holds the data to an event (Name, Month, Year, Day, time (Start & End))
 * that is created by the user. 
 * @author Eric, Yanpeng, Trung
 *
 */
public class Records implements Serializable,Comparable<Records>
{
	private String title_event;
	private String event_startTime;
	private String event_endTime;
	private String month_Event;
	private String day_Event;
	private String event_Year;
 
	/**
	 * 
	 * @param title_event
	 * @param date
	 * @param event_startTime
	 * @param event_endTime
	 */
	public Records(String title_event, String date, String event_startTime, String event_endTime) {
		this.title_event = title_event;
		this.event_startTime = event_startTime;
		this.event_endTime = event_endTime;

		String[] event = date.split("/");
		this.month_Event = event[0];
		this.day_Event = event[1];
		this.event_Year = event[2];
	}	

	/**
	 * Month of Event 
	 */
	public String getEventMonth()	{	return month_Event;	}

	/**
	 * Day of Event 
	 */
	public String getEventDay(){	return day_Event;	}

	/**
	 * Year of Event 
	 */
	public String getEventYear()	{	return event_Year;	}

	/**
	 * Name of Event 
	 */
	public String getEventName(){	return title_event;	}

	/**
	 * Start time of Event 
	 */
	public String getEventStartTime(){	return event_startTime;	}

	/**
	 * End time of Event 
	 */
	public String getEventEndTime(){	return event_endTime;	}

	/**
	 * Start time of Event in integer form
	 */
	public int getEventStartTimeInt(){
		String tempStartTime = event_startTime.replace(":", "");
		return Integer.parseInt(tempStartTime);
	}

	/**
	 * End time of Event in integer form
	 */
	public int getEventEndTimeInt()
	{
		String tempEndTime = event_endTime.replace(":", "");
		return Integer.parseInt(tempEndTime);
	}

	/**
	 * 
	 */
	public int compareTo(Records o) 
	{
		return this.event_startTime.compareTo(o.event_startTime);
	}
}