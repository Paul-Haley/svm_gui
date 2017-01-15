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
    private JComboBox<String> svmType, kernelType, shrinking, 
    		probabilityEstimates;
    private JTextField degree, gamma, coef0, cost, nu, epsilonLoss, cacheSize, 
    		epsilonTolerance, weight, crossValidation;
    
    /*
     * Inputs and Outputs
     */
    private JTextField dataFilepath, modelFilepath, dialog; 
    private JButton train, help;
    
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
		
        
        //Adds the highest level JPanel to the container.
        container.add(panel,"North");
	}
	
	/**
	 * Adds 
	 * 
	 * @param parent JPanel to contain the JPanel constructed.
	 */
	private void add(JPanel parent) {
		
		return;
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

}
