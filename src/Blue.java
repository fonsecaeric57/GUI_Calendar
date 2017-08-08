import java.awt.Color;

import javax.swing.JTextPane;

/**
 *  Class:	Blue
 *  Description: Blue is a concrete class that belongs to the strategyTwo pattern implementation and 
 *  provide a specific algorithm to execute.
 * @author Eric, Yanpeng, Trung
 *
 */
public class Blue implements StrategyTwo
{
	/**
	 * 
	 */
	public JTextPane selectedColorView(JTextPane tp) {
		tp.setForeground(Color.BLUE);
		return tp;
	}
}