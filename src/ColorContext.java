import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 *  
 *  Class:	ColorContext
 *  Description: ColorContext facilates our implenetation of StrategyTwo.
 *  It is the Context class that owns the concrete Strategy objects, and provides 
 *  the contextual information that the Strategy implementations need.
 * @author Eric, Yanpeng, Trung
 *
 */
public class ColorContext 
{

     StrategyTwo strategy;
     /**
      * 
      * @param strategy
      */
    public ColorContext(StrategyTwo strategy) 
    {
		this.strategy = strategy;
    }
    /**
     * 
     * @param strategy
     */
    	public void setColorStrategy(StrategyTwo strategy) {
		this.strategy = strategy;
    	}
    	/**
    	 * 
    	 * @param tp
    	 * @return
    	 */
    	public JTextPane getColorResult(JTextPane tp) 
    	{
		 return strategy.selectedColorView(tp);
	}
}
