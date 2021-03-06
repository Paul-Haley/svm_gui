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
		view.svmSelectorListener(new SvmTypeSelector());
		view.kernelSelectorListener(new KernelTypeSelector());	
		
		// CheckBoxes
		view.scaleSaveListener(new ScaleSaveActionListener());
		view.scaleLoadListener(new ScaleLoadActionListener());
		
		// Buttons
		view.helpListener(new HelpButtonActionListener());
		view.resetListener(new ResetButtonActionListener());
		view.resetScaleListener(new ResetScaleButtonActionListener());
		view.scaleListener(new ScaleButtonActionListener());
		view.trainListener(new TrainButtonActionListener());
		view.predictListener(new PredictButtonActionListener());
	}
	
	/**
	 * Class for handling SVM type selections.
	 */
	private class SvmTypeSelector implements ActionListener {
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
	private class KernelTypeSelector implements ActionListener {
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
	 * Class for handling saving checkbox selections.
	 */
	private class ScaleSaveActionListener implements ActionListener {
		/**
		 * When scale save is selected, scale load will be unticked.
		 */
		public void actionPerformed(ActionEvent e) {
			if (view.getScaleSave()) {
				view.setScaleLoad(false);
				view.geryScaleParameterFilepath(false);
			} else if (!view.getScaleLoad()) {
				view.geryScaleParameterFilepath(true);
			}
		}
	}
	
	/**
	 * Class for handling load checkbox selections.
	 */
	private class ScaleLoadActionListener implements ActionListener {
		/**
		 * When scale load is selected, scale save will be unticked.
		 */
		public void actionPerformed(ActionEvent e) {
			if (view.getScaleLoad()) {
				view.setScaleSave(false);
				view.geryScaleParameterFilepath(false);
			} else if (!view.getScaleSave()) {
				view.geryScaleParameterFilepath(true);
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
     * This class listens to the scale tab reset button to reset all training 
     * options to default.
     */
    private class ResetScaleButtonActionListener implements ActionListener {
    	/**
    	 * Resets all training options to their defaults.
    	 */
    	public void actionPerformed(ActionEvent e) {
    		view.resetScale();
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
    		// Calculating the mode value, 0 nothing, 1 save, 2 load.
    		int mode = (view.getScaleSave()?1:0) + (view.getScaleLoad()?2:0);
    		
    		// Call scaling. TODO: error checking
    		model.scale(mode, view.getScaleParameterFilepath(), 
    				view.getDataFilepath(), view.getScaledDataFilepath(), 
    				view.getXLower(), view.getXUpper(), 
    				view.getYLower(), view.getYUpper());
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
    		parameter.shrinking = view.getShrinking()?1:0;
    		parameter.probability = view.getProbability()?1:0;
    		
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
    	 * Run the predict program after reading the user provided settings.
    	 */
    	public void actionPerformed(ActionEvent e) {
    		model.predict(view.getPredictProbability(), view.getDataFilepath(), 
    				view.getModelFilepath(), view.getOutputFilepath());
    	}
    }
}
