package de.clearit.test.exceptions;

/**
 * Die Ausnahme, die wird geworfen, wenn für GuiElement driver ist nicht
 * gesetzt.
 * 
 * @author Ilja Winokurow
 */
public class ElementIsEnabledException extends RuntimeException {

	/**
	 * Comment for
	 */
	private static final long serialVersionUID = -7988669567573654127L;

	/**
	 * Constructor
	 * 
	 */
	public ElementIsEnabledException() {
		super("Element is still enabled after timeout.");
	}
}
