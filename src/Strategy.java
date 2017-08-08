import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Class: Strategy
 * Description: Strategy is an interface that defines a common way 
 * to access the Concrete Strategy class for the implementations of the strategy pattern 
 * that allows the user to select diffrent views whithin the application.
 * @author Eric, Yanpeng, Trung
 */
public interface Strategy{
	/**
	 * 
	 * @param m
	 * @return
	 */
    public JScrollPane selectedView (Model m);

}