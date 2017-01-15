package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LearningView extends JFrame {
	
	/*
	 * Invariant:
	 * 
	 */
	
	// The model of the Learning Manager.
	private LearningModel model;
	
	/*
	 * Creates selection box used to select options applied.
	 */
	private String[] svmTypes = {"0 -- C-SVC (multi-class classification)", 
			"1 -- nu-SVC (multi-class classification)", "2 -- one-class SVM", 
			"3 -- epsilon-SVR (regression)", "4 -- nu-SVR (regression)"};
	private String[] kernelTypes = {"0 -- linear: u'*v\n", 
			"1 -- polynomial: (gamma*u'*v + coef0)^degree\n",
			"2 -- radial basis function: exp(-gamma*|u-v|^2)\n",
			"3 -- sigmoid: tanh(gamma*u'*v + coef0)\n",
			"4 -- precomputed kernel (kernel values in training_set_file)\n"};
	private String[] yesNo = {"No", "Yes"};
    private JComboBox<String> svmType, kernelType, shrinking, 
    		probabilityEstimates;
    private JTextField degree, gamma, coef0, cost, nu, epsilonLoss, cacheSize, 
    		epsilonTolerance, weight, crossValidation;
    
    /*
     * Inputs and Outputs
     */
    private JTextField dataFilepath, modelFilepath;
    private JTextArea dialog;
    private JButton scale, train, predict, help, reset;
    
    /**
     * Creates a new svm_gui window.
     * @param model Learning Model in the MVC architecture. 
     */
	public LearningView(LearningModel model) {
		//Bind instance variable to passed model.
        this.model = model;
        
        //Creates container for interface setting title, size and close action.
        setTitle("svm_gui");
        setBounds(400,200,700,450);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container container = getContentPane();
        
        //Calls helper method that assembles the GUI.
        addComponents(container);
	}

	/**
     * This helper method assembles the whole GUI using other already made 
     * JPanels. BoxLayout is used.
     * 
     * @param container
     *      Container to place interface components (JPanels).
     */
	private void addComponents(Container container) {
		///Creates a new JPanel with vertical BoxLayout.
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        //Adds all interface components to the JPanel.
		addFileSelector(panel);
		addCombinationSettingHelp(panel);
		addModes(panel);
		
		// Implement and add dialog (output box)
		dialog = new JTextArea(15,50);
		panel.add(dialog);
        
        //Adds the highest level JPanel to the container.
        container.add(panel,"North");
	}
	
	/**
	 * Adds the data filepath selector.
	 * 
	 * @param parent JPanel to contain the JPanel constructed.
	 */
	private void addFileSelector(JPanel parent) {
		//Creates a new JPanel for labels with a 2 by 2 grid.
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,2));
        
        // Implementing data and model TextFields
        dataFilepath = new JTextField(50);
        modelFilepath = new JTextField(50);
        
        panel.add(new JLabel("Data file to use:"));
        panel.add(dataFilepath);
        
        panel.add(new JLabel("Model file to use:"));
        panel.add(modelFilepath);
        
        //TODO: Use an actual file selector.
        
        parent.add(panel);
	}
	
	/**
	 * Adds combination of the SVM setting panel and the panel with the 
	 * restrictions and help button.
	 * 
	 * @param parent JPanel to contain the JPanel constructed.
	 */
	private void addCombinationSettingHelp(JPanel parent) {
		JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
		
        addOptions(panel);
        addRestrictionsHelp(panel);
        
        parent.add(panel);
	}
	
	/**
	 * Adds all inputs for the SVM creation.
	 * 
	 * @param parent JPanel to contain the JPanel constructed.
	 */
	private void addOptions(JPanel parent) {
		/*
		 * Creates a new JPanel for labels with a 4 by 8 grid, effectively only 
		 * 4 by 4 as every option will have a textfield or a dropbox next to it.
		 */
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,8));
        
        // Setting up drop downboxes
        svmType = new JComboBox<String>(svmTypes);
        kernelType = new JComboBox<String>(kernelTypes);
        shrinking = new JComboBox<String>(yesNo);
        probabilityEstimates = new JComboBox<String>(yesNo);
        
        //TODO: Put tooltips for each textfield.
        //TODO: Grey out unrelated fields.
        
        panel.add(new JLabel("SVM type:"));
        panel.add(svmType);
        
        panel.add(new JLabel("Kernel type:"));
        panel.add(kernelType);
        
        panel.add(new JLabel("Enable shrinking?"));
        panel.add(shrinking);
        
        panel.add(new JLabel("Train for probability estimates?"));
        panel.add(probabilityEstimates);
        
        panel.add(new JLabel("Degree in kernel function:"));
        degree = new JTextField(3);
        panel.add(degree);
        
        panel.add(new JLabel("Gamma in kernel function:"));
        gamma = new JTextField(8);
        panel.add(gamma);
        
        panel.add(new JLabel("coef0 in kernel Function"));
        coef0= new JTextField(8);
        panel.add(coef0);
        
        panel.add(new JLabel("Cost (C) [0,3,4]:"));
        cost = new JTextField(3);
        panel.add(cost);
        
        panel.add(new JLabel("nu [1,2,4]:"));
        nu = new JTextField(8);
        panel.add(nu);
        
        panel.add(new JLabel("Epsilon in loss function [3]:"));
        epsilonLoss = new JTextField(8);
        panel.add(epsilonLoss);
        
        panel.add(new JLabel("Epsilon termination tolerance:"));
        epsilonTolerance = new JTextField(8);
        panel.add(epsilonTolerance);
        
        panel.add(new JLabel("Weight (see help) [0]:"));
        weight = new JTextField(8);
        panel.add(weight);
        
        panel.add(new JLabel("n-fold cross validation:"));
        crossValidation = new JTextField(3);
        panel.add(crossValidation);
        
        panel.add(new JLabel("Cache memory in MB:"));
        cacheSize = new JTextField(6);
        panel.add(cacheSize);
		
        // Set all options to their defaults
        resetOptions();
        
        parent.add(panel);
	}
	
	/**
	 * Adds accompanying instructions, along with, help and reset buttons.
	 * 
	 * @param parent JPanel to contain the JPanel constructed.
	 */
	private void addRestrictionsHelp(JPanel parent) {
		JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));
        
        panel.add(new JLabel("[0] -- C-SVC\n"
        		+"[1] -- nu-SVC\n"
        		+"[2] -- one-class SVM\n"
        		+"[3] -- epsilon-SVR\n"
        		+"[4] -- nu-SVR\n"));

        addResetHelp(panel);
        
        parent.add(panel);
	}
	
	/**
	 * Adds help and reset buttons.
	 * 
	 * @param parent JPanel to contain the JPanel constructed.
	 */
	private void addResetHelp(JPanel parent) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		
		// Label and add the help button
		help = new JButton("Help");
        panel.add(help);
        
        // Label and add the reset button
        reset = new JButton("Reset");
        panel.add(reset);
        
		parent.add(panel);
	}
	
	/**
	 * Adds mode buttons for scaling data, training model and predicting for 
	 * given data. 
	 * 
	 * @param parent JPanel to contain the JPanel constructed.
	 */
	private void addModes(JPanel parent) {
		JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,3));
        
        // Implement buttons and set text
        scale = new JButton("Scale");
        train = new JButton("Train");
        predict = new JButton("Predict");
        
        panel.add(scale);
        panel.add(train);
        panel.add(predict);
        
        parent.add(panel);
	}
	
	/**
	 * Resets all the setting fields to their defaults.
	 */
	public void resetOptions() {
		svmType.setSelectedIndex(0);
		kernelType.setSelectedIndex(2);
        shrinking.setSelectedIndex(1);
        probabilityEstimates.setSelectedIndex(0);
        degree.setText("3");
        coef0.setText("0");
        cost.setText("1");
        nu.setText("0.5");
        epsilonLoss.setText("0.1");
        epsilonTolerance.setText("0.001");
        weight.setText("1");
        cacheSize.setText("100");
        return;
	}
	
	/**
     * Allows for the help button to trigger actions. Sets up an ActionListener 
     * with the passed in one.
     * 
     * @param p1 Given ActionListener for monitoring.
     */
    public void helpListener(ActionListener p1) {
        help.addActionListener(p1);
    }
    
    /**
     * Allows for the reset button to trigger actions. Sets up an ActionListener 
     * with the passed in one.
     * 
     * @param p1 Given ActionListener for monitoring.
     */
    public void resetListener(ActionListener p1) {
        reset.addActionListener(p1);
    }
	
	/**
     * Generic error dialogue box to be used for all exceptions, faults or 
     * incorrect user inputs. This box appears with an error icon and displays 
     * the given message passed in a scrollable text area.
     * 
     * @param message
     *      String Message to be displayed to the user in error dialogue box. 
     */
    public void genericErrorBox(String message) {
        /*Creates a textarea and sets its text to the given error message. The 
         * caret is then set to the first character so that the scroll bars (if 
         * needed) when they appear are scroll to the top left. */
        JTextArea errorText = new JTextArea(10,40);
        errorText.setEditable(false);
        errorText.setText(message);
        errorText.setCaretPosition(0);
        //Adds the scrollpane implementing the textarea.
        JScrollPane errorDisplay = new JScrollPane(errorText);
        
        /*Creates dialogue box with given message, title of "error" and an 
         * error icon. */
        JOptionPane.showMessageDialog(this, errorDisplay, "Error", 
                JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Displays a message box with the usage instructions for SVMlib
     *  
     */
    public void helpUsage() {
        //Creates a textarea with the help and usage information.
        JTextArea helpText = new JTextArea(10,40);
        helpText.setEditable(false);
        helpText.setText("Options:\n"
        		+"SVM type: set type of SVM (default 0)\n"
        		+"	0 -- C-SVC		(multi-class classification)\n"
        		+"	1 -- nu-SVC		(multi-class classification)\n"
        		+"	2 -- one-class SVM\n"
        		+"	3 -- epsilon-SVR	(regression)\n"
        		+"	4 -- nu-SVR		(regression)\n"
        		+"kernel_type : set type of kernel function (default 2)\n"
        		+"	0 -- linear: u'*v\n"
        		+"	1 -- polynomial: (gamma*u'*v + coef0)^degree\n"
        		+"	2 -- radial basis function: exp(-gamma*|u-v|^2)\n"
        		+"	3 -- sigmoid: tanh(gamma*u'*v + coef0)\n"
        		+"	4 -- precomputed kernel (kernel values in training_set_file)\n"
        		+"degree : set degree in kernel function (default 3)\n"
        		+"gamma : set gamma in kernel function (default 1/num_features, leave box blank for default)\n"
        		+"coef0 : set coef0 in kernel function (default 0)\n"
        		+"cost : set the parameter C of C-SVC, epsilon-SVR, and nu-SVR (default 1)\n"
        		+"nu : set the parameter nu of nu-SVC, one-class SVM, and nu-SVR (default 0.5)\n"
        		+"epsilon : set the epsilon in loss function of epsilon-SVR (default 0.1)\n"
        		+"cachesize : set cache memory size in MB (default 100)\n"
        		+"epsilon : set tolerance of termination criterion (default 0.001)\n"
        		+"shrinking : whether to use the shrinking heuristics, 0 or 1 (default 1)\n"
        		+"probability_estimates : whether to train a SVC or SVR model for probability estimates, 0 or 1 (default 0)\n"
        		+"weight : set the parameter C of class i to weight*C, for C-SVC (default 1)\n"
        		+": n-fold cross validation mode (leave blank for no cross validation)\n");
        helpText.setCaretPosition(0);
        //Adds the scrollpane implementing the textarea.
        JScrollPane helpDisplay = new JScrollPane(helpText);
        
        /*Creates dialogue box with given message, title of "error" and an 
         * error icon. */
        JOptionPane.showMessageDialog(this, helpDisplay, "Help", 
                JOptionPane.QUESTION_MESSAGE);
    }
}
