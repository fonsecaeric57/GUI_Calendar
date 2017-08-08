import java.awt.Color;

import javax.swing.JTextPane;

/**
 *  Class:	Green
 *  Description: Green is a concrete class that belongs to the strategyTwo pattern implementation and 
 *  provide a specific algorithm to execute.
 * @author Eric, Yanpeng, Trung
 */
public class Green implements StrategyTwo
{
	/**
	 * 
	 */
	public JTextPane selectedColorView(JTextPane tp) {
		tp.setForeground(Color.GREEN);
		return tp;
	}
}
