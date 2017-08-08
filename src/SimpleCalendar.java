/**
 * Class: SimpleCalendar
 * Description: SimpleCalendar contain the main method that is needed to 
 * initiate the application.
 * @author Eric, Yanpeng, Trung
 *
 */
public class SimpleCalendar 
{
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Model m = new Model();
		ViewController view = new ViewController(m);
		m.attach(view);
	}
}
