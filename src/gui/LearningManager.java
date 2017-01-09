package gui;

/**
 * This class provides the main method that runs SVM_gui
 */
public class LearningManager {

	// Starts the GUI
	public static void main(String[] args) throws Exception {
		LearningModel model = new LearningModel();
		LearningView view = new LearningView(model);
		new LearningController(model, view);
		view.setVisible(true);
	}

}
