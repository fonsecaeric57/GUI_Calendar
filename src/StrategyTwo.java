import javax.swing.JTextPane;


/**
 * Class: StrategyTwo
 * Description: StrategyTwo is an interface that defines a common way 
 * to access the Concrete Strategy class for the implementations of the strategy pattern 
 * that allows the user to select diffrent colors .
 * @author Eric, Yanpeng, Trung
 *
 */
public interface StrategyTwo 
{
	/**
	 * 
	 * @param tp
	 * @return
	 */
	public JTextPane selectedColorView(JTextPane tp);
}
