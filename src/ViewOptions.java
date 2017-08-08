import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 
 *  Class:	ViewOptions
 *  Description: ViewOptions facilates our implenetation of Strateggy Pattern.
 *  It is the Context class that owns the (concrete) Strategy objects, and provides 
 * the contextual information that the various Strategy implementations need.
 * @author Eric, Yanpeng, Trung
 */
class ViewOptions {
        Strategy strategy;
        /**
         * 
         * @param strategy
         */
        public ViewOptions(Strategy strategy) 
        {

		this.strategy = strategy;
    }
        /**
         * 
         * @param strategy
         */
    	public void setStrategy(Strategy strategy) 
    	{
		this.strategy = strategy;
        }
    	/**
    	 * 
    	 * @param m
    	 * @return
    	 */
    	public JScrollPane getResult(Model m) {
		 return strategy.selectedView(m);
	}
}
