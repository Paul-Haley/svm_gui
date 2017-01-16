package gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import javafx.scene.layout.Border;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.PrintStream;

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
        setBounds(400, 200, 900, 550);
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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        //Adds all interface components to the JPanel.
		addFileSelector(panel);
		addCombinationSettingHelp(panel);
		addDialog(panel);
		
        //Adds the highest level JPanel to the container.
        container.add(panel,"North");
	}
	
	/**
	 * Adds the data filepath selector.
	 * 
	 * @param parent JPanel to contain the JPanel constructed.
	 */
	private void addFileSelector(JPanel parent) {
		//Creates a new JPanel for labels with a 2 by 1 grid.
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        
        // Two sub panels using FlowLayout will be used for scaling.
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEADING));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        // Implementing data and model TextFields
        dataFilepath = new JTextField(50);
        modelFilepath = new JTextField(50);
        
        //TODO: Use an actual file selector.
        panel1.add(new JLabel("Data file to use:"));
        panel1.add(dataFilepath);
        
        panel2.add(new JLabel("Model file to use:"));
        panel2.add(modelFilepath);
        
        // Combining the two sub panels into the main panel.
        panel.add(panel1);
        panel.add(panel2);
        
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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
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
		 * 
		 * This is done through a BoxLayout with the four rows made in separate 
		 * panels.
		 */
        JPanel panel = new JPanel();
        //panel.setLayout(new GridLayout(4,8));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        //Making the other four need panels using a FlowLayout
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEADING));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEADING));        
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.LEADING));
        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        // Setting up drop downboxes
        svmType = new JComboBox<String>(svmTypes);
        kernelType = new JComboBox<String>(kernelTypes);
        shrinking = new JComboBox<String>(yesNo);
        probabilityEstimates = new JComboBox<String>(yesNo);
        
        //TODO: Put tooltips for each textfield.
        //TODO: Grey out unrelated fields.
        
        // First row
        panel1.add(new JLabel("SVM type:"));
        panel1.add(svmType);
        
        panel1.add(new JLabel("Kernel type:"));
        panel1.add(kernelType);
        
        // Second row
        panel2.add(new JLabel("Degree in kernel function:"));
        degree = new JTextField(3);
        panel2.add(degree);
        
        panel2.add(new JLabel("Gamma in kernel function:"));
        gamma = new JTextField(5);
        panel2.add(gamma);
        
        panel2.add(new JLabel("coef0 in kernel Function"));
        coef0= new JTextField(5);
        panel2.add(coef0);
        
        panel2.add(new JLabel("Cost (C) [0,3,4]:"));
        cost = new JTextField(3);
        panel2.add(cost);
        
        // Third row
        panel3.add(new JLabel("nu [1,2,4]:"));
        nu = new JTextField(5);
        panel3.add(nu);
        
        panel3.add(new JLabel("Epsilon in loss function [3]:"));
        epsilonLoss = new JTextField(5);
        panel3.add(epsilonLoss);
        
        panel3.add(new JLabel("Epsilon termination tolerance:"));
        epsilonTolerance = new JTextField(5);
        panel3.add(epsilonTolerance);
        
        panel3.add(new JLabel("Weight (see help) [0]:"));
        weight = new JTextField(5);
        panel3.add(weight);
        
        //Fourth row
        panel4.add(new JLabel("Enable shrinking?"));
        panel4.add(shrinking);
        
        panel4.add(new JLabel("Train for probability estimates?"));
        panel4.add(probabilityEstimates);
        
        panel4.add(new JLabel("n-fold cross validation:"));
        crossValidation = new JTextField(3);
        panel4.add(crossValidation);
        
        panel4.add(new JLabel("Cache memory in MB:"));
        cacheSize = new JTextField(4);
        panel4.add(cacheSize);
		
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        
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
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        
        JTextArea restrictions = new JTextArea("Restricted to:\n"
        		+"[0] -- C-SVC\n"
        		+"[1] -- nu-SVC\n"
        		+"[2] -- one-class SVM\n"
        		+"[3] -- epsilon-SVR\n"
        		+"[4] -- nu-SVR\n");
        restrictions.setEditable(false);
        restrictions.setOpaque(false);
        
        //Building panel1 with restrictions text and the reset and help buttons.
        panel.add(restrictions);
        addResetHelp(panel1);
        addModes(panel1);
        
        panel.add(panel1);
        
        parent.add(panel);
	}
	
	
	/**
	 * Adds help and reset buttons.
	 * 
	 * @param parent JPanel to contain the JPanel constructed.
	 */
	private void addResetHelp(JPanel parent) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
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
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        
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
	 * Adds the output dialog with scroll bars.
	 * 
	 * @param parent JPanel to contain the JPanel constructed.
	 */
	private void addDialog(JPanel parent) {
		//Creates a new JPanel using default FlowLayout.
		JPanel panel = new JPanel();
		
		// Implement and add dialog (output box) that will print stdout.
		dialog = new JTextArea(10, 70);
		PrintStream output = new PrintStream(new TextAreaOutputStream(dialog));
		System.setOut(output); // Redirecting stdout.
		System.setErr(output); 
		/*
		 * TODO: do something about colour or make a way to make the output more
		 * obviously an error
		 */
		dialog.setEditable(false);
		
				
		/*
		 * Adds the scrollpanes to the textarea making scrollbars on horizontal 
		 * and vertical axis. 
		 * */
		JScrollPane dialogScroll = new JScrollPane(dialog, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
		        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		        
		panel.add(dialogScroll);
		
		parent.add(panel);
	}
	
	/**
	 * Resets all the setting fields to their defaults.
	 */
	public void resetOptions() {
		// Set default values for non-empty fields
		//TODO: Make variables to store these values if needed again.
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
        
        gamma.setText("");
        crossValidation.setText("");
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
     * Allows for the predict button to trigger actions. Sets up an 
     * ActionListener with the passed in one.
     * 
     * @param p1 Given ActionListener for monitoring.
     */
    public void predictListener(ActionListener p1) {
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
        		+"n-fold cross validation mode (leave blank for no cross validation)\n");
        helpText.setCaretPosition(0);
        //Adds the scrollpane implementing the textarea.
        JScrollPane helpDisplay = new JScrollPane(helpText);
        
        /*Creates dialogue box with given message, title of "error" and an 
         * error icon. */
        JOptionPane.showMessageDialog(this, helpDisplay, "Help", 
                JOptionPane.PLAIN_MESSAGE);
    }
}
