package gui;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

//import libsvm.*;
import svm.svm_train;

/**
 * The model for the svm_gui application.
 * 
 * @author Paul Haley
 *
 */
public class LearningModel {
	/*
	 * Class invariant:
	 * 
	 * display != null &&
	 * 
	 * 
	 */
	
	public PrintStream display;
	
	public LearningModel() {
		System.setOut(display); // re-directs stdout for use in the View class.
		return;
	}
	
	public int train() {
		return 1;
	}
	
	public int scale() {
		
		return 1;
	}
	
	public int predict() {
		
		return 1;
	}
	
	
}
