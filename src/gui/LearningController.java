package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JComboBox;

public class LearningController {
	
	private LearningView view;
	private LearningModel model;

	public LearningController(LearningModel model, LearningView view) {
		// Bind instance variables to passed view and model.
		this.view = view;
		this.model = model;
		
		view.helpListener(new HelpButtonActionListener());
		view.resetListener(new ResetButtonActionListener());
		view.predictListener(new PredictButtonActionListener());
	}

	/**
     * This class listens to the button used to bring up help.
     */
    private class HelpButtonActionListener implements ActionListener {
    	/**
    	 * Brings up the help dialog box when the help button is pressed.
    	 */
    	public void actionPerformed(ActionEvent e) {
    		view.helpUsage();
    	}
    }
    
    /**
     * This class listens to the reset button to reset all training options to 
     * default.
     */
    private class ResetButtonActionListener implements ActionListener {
    	/**
    	 * Resets all training options to their defaults.
    	 */
    	public void actionPerformed(ActionEvent e) {
    		view.resetOptions();
    	}
    }
    
    /**
     * This class listens to the predict button.
     */
    private class PredictButtonActionListener implements ActionListener {
    	/**
    	 * Attempts to run the predict program after reading the user provided 
    	 * settings.
    	 */
    	public void actionPerformed(ActionEvent e) {
    		
    	}
    }
}
