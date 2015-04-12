package ec.org.alone.analysis;

import java.io.File;

import dk.ange.octave.OctaveEngine;
import dk.ange.octave.OctaveEngineFactory;
import dk.ange.octave.type.OctaveDouble;

public class LeafAnalysis {
	private final String octaveScriptFolderPath = "C:\\jboss-as-7.1.1.Final\\jboss-as-7.1.1.Final\\standalone\\deployments\\WebServAlone.war\\utils";
	private static File octaveExcecutableProgram = new File(
			"C:\\Octave\\Octave-3.8.2\\bin\\octave-3.8.2.exe");
	private int totalLeafDamage;
	private int lowLeafDamage;
	private int highLeafDamage;

	/**
	 * Empty constructor
	 */
	public LeafAnalysis() {

	}

	/**
	 * Constructor that call leafImageAnalysis method.
	 */
	public LeafAnalysis(String imagePath) {
		totalLeafDamage = 0;
		lowLeafDamage = 0;
		highLeafDamage = 0;

		leafImageAnalysis(imagePath);
	}

	/**
	 * Method that calls the script image analysis
	 * 
	 * @param imageToBeAnalyzed
	 *            Path of the image to be analyzed
	 */
	public void leafImageAnalysis(String imageToBeAnalyzed) {
		OctaveEngineFactory engineFactory = new OctaveEngineFactory();
		engineFactory.setOctaveProgram(octaveExcecutableProgram);

		OctaveEngine octaveSession = engineFactory.getScriptEngine();

		// Analysis
		octaveSession.eval("cd " + octaveScriptFolderPath);
		octaveSession.eval("totalLeafDamage = 0;");
		octaveSession.eval("lowLeafDamage = 0;");
		octaveSession.eval("highLeafDamage = 0;");

		String expressionToEvaluate = "[totalLeafDamage lowLeafDamage highLeafDamage] = prosimage("
				+ "'" + imageToBeAnalyzed + "'" + ");";
		octaveSession.eval(expressionToEvaluate);

		OctaveDouble octaveTotalLeafDamage = octaveSession.get(
				OctaveDouble.class, "totalLeafDamage");
		OctaveDouble octaveLowLeafDamage = octaveSession.get(
				OctaveDouble.class, "lowLeafDamage");
		OctaveDouble octaveHighLeafDamage = octaveSession.get(
				OctaveDouble.class, "highLeafDamage");

		totalLeafDamage = (int) octaveTotalLeafDamage.get(1);
		lowLeafDamage = (int) octaveLowLeafDamage.get(1);
		highLeafDamage = (int) octaveHighLeafDamage.get(1);

		try {
			octaveSession.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public int getTotalLeafDamage() {
		return totalLeafDamage;
	}

	public void setTotalLeafDamage(int totalLeafDamage) {
		this.totalLeafDamage = totalLeafDamage;
	}

	public int getLowLeafDamage() {
		return lowLeafDamage;
	}

	public void setLowLeafDamage(int lowLeafDamage) {
		this.lowLeafDamage = lowLeafDamage;
	}

	public int getHighLeafDamage() {
		return highLeafDamage;
	}

	public void setHighLeafDamage(int highLeafDamage) {
		this.highLeafDamage = highLeafDamage;
	}

}
