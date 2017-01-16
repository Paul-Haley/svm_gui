package gui;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

/**
 * This class is used for redirecting an OutputStream to a JTextArea.
 * @author Paul Haley
 *
 */
public class TextAreaOutputStream extends OutputStream {

	//Instance variables
	private JTextArea output;
	
	/**
	 * Constructor for TextArea OutputStream.  The textarea given will have the 
	 * contents of the OutputStream appended to it.
	 * 
	 * @param output The JTextArea where teh OutputStream's data should be 
	 * 		written to.
	 */
	public TextAreaOutputStream(JTextArea output) {
		this.output = output;
	}
	
	@Override
	public void write(int character) throws IOException {
		// Casts the byte holding the character and appends it to the JTextArea.
        output.append(String.valueOf((char)character));
        // Moves the cursor back to the start of the document.
        output.setCaretPosition(output.getDocument().getLength());
	}
}
