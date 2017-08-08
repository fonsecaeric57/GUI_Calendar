import java.awt.Color;

import javax.swing.JTextPane;

/**
 *  Class:	Red
 *  Description: Red is a concrete class that belongs to the strategyTwo pattern implementation and 
 *  provide a specific algorithm to execute.
 * @author Eric, Yanpeng, Trung
 *
 */
public class Red implements StrategyTwo
{
	/**
	 * 
	 */
	public JTextPane selectedColorView(JTextPane tp) {
		tp.setForeground(Color.RED);
		return tp;
	}
}
