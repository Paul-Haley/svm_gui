package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import libsvm.svm_parameter;

public class LearningController {
	
	// Instance variables
	private LearningView view;
	private LearningModel model;

	public LearningController(LearningModel model, LearningView view) {
		// Bind instance variables to passed view and model.
		this.view = view;
		this.model = model;
		
		// ComboBoxes
		view.svmSelectorListener(new svmTypeSelector());
		view.kernelSelectorListener(new kernelTypeSelector());	
		
		// Buttons
		view.helpListener(new HelpButtonActionListener());
		view.resetListener(new ResetButtonActionListener());
		view.scaleListener(new ScaleButtonActionListener());
		view.trainListener(new TrainButtonActionListener());
		view.predictListener(new PredictButtonActionListener());
	}
	
	/**
	 * Class for handling SVM type selections.
	 */
	private class svmTypeSelector implements ActionListener {
		/**
		 * When an SVM type is selected, unrelated options will be greyed out.
		 * 
		 * Covers all cases, default if more or less if more methods were 
		 * implemented.
		 */
		public void actionPerformed(ActionEvent e) {
			switch (view.getSvmType()) {
				case svm_parameter.C_SVC:
					view.greyCost(false);
					view.greyNrWeight(false);
					view.greyWeightLabel(false);
					view.greyWeight(false);
					view.greyNu(true);
					view.greyEpsilonLoss(true);
					break;
				case svm_parameter.NU_SVC:
				case svm_parameter.ONE_CLASS: // Fall through
					view.greyCost(true);
					view.greyNrWeight(true);
					view.greyWeightLabel(true);
					view.greyWeight(true);
					view.greyNu(false);
					view.greyEpsilonLoss(true);
					break;
				case svm_parameter.EPSILON_SVR:
					view.greyCost(false);
					view.greyNrWeight(true);
					view.greyWeightLabel(true);
					view.greyWeight(true);
					view.greyNu(true);
					view.greyEpsilonLoss(false);
					break;
				case svm_parameter.NU_SVR:
					view.greyCost(false);
					view.greyNrWeight(true);
					view.greyWeightLabel(true);
					view.greyWeight(true);
					view.greyNu(false);
					view.greyEpsilonLoss(true);
					break;
				default: // Failsafe? Close off everything.
					view.greyCost(true);
					view.greyNrWeight(true);
					view.greyWeightLabel(true);
					view.greyWeight(true);
					view.greyNu(true);
					view.greyEpsilonLoss(true);
					break;
			}
		}
	}
	
	/**
	 * Class for handling kernel type selections.
	 */
	private class kernelTypeSelector implements ActionListener {
		/**
		 * When an kernel type is selected, unrelated options will be greyed out.
		 * 
		 * Covers all cases. Default greys all options.
		 */
		public void actionPerformed(ActionEvent e) {
			switch (view.getKernelType()) {
				case svm_parameter.POLY:
					view.greyDegree(false);
					view.greyGamma(false);
					view.greyCoef0(false);
					break;
				case svm_parameter.RBF:
					view.greyDegree(true);
					view.greyGamma(false);
					view.greyCoef0(true);
					break;
				case svm_parameter.SIGMOID:
					view.greyDegree(true);
					view.greyGamma(false);
					view.greyCoef0(false);
					break;
				default:
					view.greyDegree(true);
					view.greyGamma(true);
					view.greyCoef0(true);
					break;
			}
		}
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
     * This class listens to the scale button.
     */
    private class ScaleButtonActionListener implements ActionListener {
    	/**
    	 * Attempts to run the scale program after reading the user provided 
    	 * settings.
    	 */
    	public void actionPerformed(ActionEvent e) {
    		
    	}
    }
    
    /**
     * This class listens to the train button.
     */
    private class TrainButtonActionListener implements ActionListener {
    	/**
    	 * Attempts to run the train program after reading the user provided 
    	 * settings.
    	 */
    	public void actionPerformed(ActionEvent e) {
    		//TODO: Error checking
    		
    		// Making the parameter
    		svm_parameter parameter = new svm_parameter();
    		parameter.svm_type = view.getSvmType();
    		parameter.kernel_type = view.getKernelType();
    		parameter.degree = view.getDegree();
    		parameter.gamma = view.getGamma();
    		parameter.coef0 = view.getCoef0();
    		
    		parameter.cache_size = view.getCacheSize();
    		parameter.eps = view.getEps();
    		parameter.C = view.getCost();
    		parameter.nr_weight = view.getNrWeight();
    		//TODO: implement functionality and uncomment these lines.
    		//parameter.weight_label = view.getWeightLabel();
    		//parameter.weight = view.getWeight();
    		parameter.nu = view.getNu();
    		parameter.p = view.getEpsilonLoss();
    		parameter.shrinking = view.getShrinking();
    		parameter.probability = view.getProbability();
    		
    		// Passing the parameter to the model
    		model.train(parameter, view.getCrossValidation(), 
    				view.getDataFilepath(), view.getModelFilepath());
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
