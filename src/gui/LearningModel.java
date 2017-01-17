package gui;

import java.io.IOException;
import java.util.*;

import libsvm.svm_parameter;
import svm.svm_predict;
import svm.svm_scale;
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
	 * 
	 */
	
	//public TextAreaOutputStream display, fault;
	
	/**
	 * Constructor for model component of the svm_gui.
	 */
	public LearningModel() {
		return;
	}
	
	/**
	 * Attempts to apply scaling to the given data file and output the scaled 
	 * data to the filepath specified. These parameters can be specified and 
	 * saved or loaded from a user specified file.
	 * 
	 * If yLower == yUpper == 0, then there will be no y scaling.
	 * 
	 * Mode meanings:
	 * mode == 0 => do nothing additional with parameters.
	 * mode == 1 => save parameters to scaleParameterFilepath.
	 * mode == 2 => load parameters from scaleParameterFilepath and apply. This 
	 * 		option will ignore all x and y bounds given.
	 * 
	 * @param mode whether to save, load or do nothing additional with 
	 * 		parameters
	 * @param scaleParameterFilepath file to save or load scaling parameters
	 * @param dataFilepath datafile to read for scaling
	 * @param scaledDataFilepath output file of scaled data
	 * @param xLower lower limit x
	 * @param xUpper upper limit x
	 * @param yLower lower limit y
	 * @param yUpper upper limit y
	 * 
	 * @require 0 <= mode <= 2 &&
	 * (scaleParameterFilepath != null <=>  mode == 0) &&
	 * dataFilepath != null &&
	 * scaledDataFilepath != null &&
	 * ((xLower < xUpper && yLower < yUpper) || yLower == yUpper == 0) 
	 */
	public void scale(int mode, String scaleParameterFilepath, 
			String dataFilepath, String scaledDataFilepath, 
			double xLower, double xUpper, double yLower, double yUpper) {
		// Pushing the arguments to a stack as the number of them is unknown
		Stack<String> commands = new Stack<String>();
		
		// Pushing x lower and upper limits
		commands.push("-l");
		commands.push(String.valueOf(xLower));
		commands.push("-u");
		commands.push(String.valueOf(xUpper));
		
		if (!(yLower == 0.0 && yUpper == 0.0)) {
			commands.push("-y");
			commands.push(String.valueOf(yLower));
			commands.push(String.valueOf(yUpper));
		}
		
		switch (mode) {
			case 1:
				commands.push("-s");
				commands.push(scaleParameterFilepath);
				break;
			case 2:
				commands.push("-r");
				commands.push(scaleParameterFilepath);
				break;
			default:
				break; // Assume mode 0, no saving or loading.
		}
		
		// Pushing data filepath and scaled out filepath
		commands.push(dataFilepath);
		commands.push(scaledDataFilepath);
		
		System.out.println(commands.toString());
		
		// Number of arguments to be passed to training call.
		int argc = commands.size();
		String[] argv = new String[argc];
		for (int i = 0; i < argc; ++i) { // Adding commands to array.
			argv[i] = commands.elementAt(i);
		}
		
		// Attempts to call LIBSVM scale with parameters given.
		try {
			svm_scale.main(argv);
		} catch (IOException e) {
			System.err.println("Error accessing specified file(s) or cannot "
					+ "write output file");
		} catch (Exception e) {
			// Do nothing because this is to replace a system exit.
		}
		return;
	}
	
	/**
	 * Use this method with to do svm training via the information given from 
	 * the svm parameter. This method is assuming that the parameters given are 
	 * valid with the exception of the files being accessed.
	 * 
	 * @param parameters svm pararmeter data for training.
	 * @param crossValidation Number of cross validation partitions to divide 
	 * 		the data into. Specifying -1 will skip cross validation.
	 * @param dataFilepath The filepath (absolute) to the data file.
	 * @param modelFilepath The filepath (absolute) to where to write the model 
	 * 		file. If null, LIBSVM will do its default behaviour and write 
	 * 		(overwrite if file already exists) a file call datafile.model
	 * 
	 * @require svm_parameter != null 
	 * 		&& dataFilepath != null 
	 * 		&& modelFilepath != null
	 */
	public void train(svm_parameter parameters, int crossValidation, 
			String dataFilepath, String modelFilepath) {
		// Pushing the arguments to a stack as the number of them is unknown
		Stack<String> commands = new Stack<String>();
		commands.push("-s");
		commands.push(String.valueOf(parameters.svm_type));
		commands.push("-t");
		commands.push(String.valueOf(parameters.kernel_type));
		commands.push("-d");
		commands.push(String.valueOf(parameters.degree));
		if (parameters.gamma != -1.0) {
			commands.push("-g");
			commands.push(String.valueOf(parameters.gamma));
		}
		commands.push("-r");
		commands.push(String.valueOf(parameters.coef0));
		commands.push("-c");
		commands.push(String.valueOf(parameters.C));
		commands.push("-n");
		commands.push(String.valueOf(parameters.nu));
		commands.push("-p");
		commands.push(String.valueOf(parameters.p));
		commands.push("-m");
		commands.push(String.valueOf(parameters.cache_size));
		commands.push("-e");
		commands.push(String.valueOf(parameters.eps));
		commands.push("-h");
		commands.push(String.valueOf(parameters.shrinking));
		commands.push("-b");
		commands.push(String.valueOf(parameters.probability));
		if (parameters.nr_weight != 0) { 
			commands.push("-wi");//TODO: implement
		}
		if (crossValidation != -1) {
			commands.push("-v");
		commands.push(String.valueOf(crossValidation));
		}
		
		// Pushing filepaths where applicable.
		commands.push(dataFilepath);
		if (!modelFilepath.equals("")) {
			commands.push(modelFilepath); // Pushes the model filepath if given
		}
		
		// Number of arguments to be passed to training call.
		int argc = commands.size();
		String[] argv = new String[argc];
		for (int i = 0; i < argc; ++i) { // Adding commands to array.
			argv[i] = commands.elementAt(i);
		}
		
		/*
		 * Attempting to run LIBSVM train. Beyond exceptions all results will 
		 * appear in the JTextArea (dialog). 
		 */
		System.out.println("\n*** Attempting to run training ***\n");
		try {
			svm_train.main(argv);
		} catch (IOException e) {
			System.err.println("Error accessing specified file(s) or cannot "
					+ "write model file");
		} catch (Exception e) {
			// Do nothing because this is to replace a system exit.
		}
		return;
	}
	
	public void predict(boolean probabilityEstimates, String data, String model, 
			String output) {
		String[] argv = new String[5];
		argv[0] = "-b";
		argv[1] = probabilityEstimates?"1":"0";
		argv[2] = data;
		argv[3] = model;
		argv[4] = output;
		
		// Attempting to run LIBSVM predict.
		System.out.println("\n*** Attempting to run prediction" 
				+ (probabilityEstimates?"(probability estimates)":"") 
				+ "***\n");
		try {
			svm_predict.main(argv);
		} catch (IOException e) {
			System.err.println("Error accessing specified file(s) or cannot "
					+ "write output file");
		} catch (Exception e) {
			// Do nothing because this is to replace a system exit.
		}
		return;
	}	
}
