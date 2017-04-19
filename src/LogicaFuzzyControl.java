import net.sourceforge.jFuzzyLogic.FIS;

public class LogicaFuzzyControl {
	public LogicaFuzzyControl()
	{
	    FIS fis = FIS.load("src/robot.fcl", true); // Load from 'FCL' file
	    fis.setVariable("service", 3); // Set inputs
	    fis.setVariable("food", 7);
	    fis.evaluate(); // Evaluate

	    // Show output variable
	    System.out.println("Output value:" + fis.getVariable("tip").getValue()); 
	}

}
