package converterComponent;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.math.*;
import java.text.ParseException;
import java.util.Stack;

/**
 * s
 * 
 * @author Dimzin
 */

// Adding an interface
interface Convertable {

	public boolean isProcessable();

	public void setProcessable(boolean isProcessable);

	public String getError();
        
        public void setError(int errorCase);

	public String getConvertedValue();

	public void setConvertedValue(int convertedToDecimalValue,
			int destinationSystemBase);

	public int getConvertedToDecimalValue();

	public int getDestinationValue();

	public void setDestinationValue(int destinationValue);

	public void setSystemType(int inputSystemType, int destinationSystemType);

	public int getInputValue();

	public void setInputValue(int inputValue);

	public void setIsInputCorrect(boolean isInputCorrect);

	public boolean isInputCorrect();

}

// Implementing the interface and Serializable for the JavaBean component
public class ConverterComponent implements Serializable, Convertable {
        // JavaBean component should have a default public constructor, private variables and Get/Set methods accessing those variables
	
	public ConverterComponent() {
	}

	
	private int inputValue;
	private int inputSystemType;
	private int destinationSystemType;
	private int convertedToDecimalValue;
	private int destinationValue;
	private String convertedValue;
	private String error;
	private boolean processable = true;
	private boolean inputCorrect = true;

	// Getters and Setters for values
	public void setInputSystemType(int inputSystemType) {
		this.inputSystemType = inputSystemType;
	}

	public int getInputSystemType() {
		return inputSystemType;
	}

	public void setDestinationSystemType(int destinationSystemType) {
		this.destinationSystemType = destinationSystemType;
	}

	public int getDestinationSystemType() {
		return destinationSystemType;
	}

	public void setConvertedToDecimalValue(int inputValue, int inputSystemType) {

		this.convertedToDecimalValue = 0;
		this.converToDecimalValue(inputValue, inputSystemType);

	}

	public int getConvertedToDecimalValue() {
		return convertedToDecimalValue;
	}

	public void setConvertedValue(int convertedToDecimalValue,
			int destinationSystemBase) {
		this.convertedValue = "";
		this.convertToDestinationBase(convertedToDecimalValue,
				destinationSystemBase);
	}

	public String getConvertedValue() {
		return convertedValue;
	}

	public void setDestinationValue(int destinationValue) {
		{
			this.destinationValue = destinationValue;
		}

	}

	public int getDestinationValue() {
		return destinationValue;
	}

	// Getters and Setters for true/false
	public void setIsInputCorrect(boolean isInputCorrect) {
		this.inputCorrect = isInputCorrect;

	}

	public boolean isInputCorrect() {
		return inputCorrect;

	}

	public void setProcessable(boolean isProcessable) {
		this.processable = isProcessable;
	}

	public boolean isProcessable() {
		return processable;
	}

	// Adding some error handling here
	public void setError(int errorCase) {

		switch (errorCase) {

		case 1: {
			this.error = "Number should be from 0 to N";
			break;
		}
		case 2: {
			this.error = "Base should be from 2 to 25";
			break;
		}

		default: {
			this.error = null;
			break;
		}

		}
	}

	public String getError() {
		return error;
	}

	// More advanced Getters and Setters with error checking
	public void setInputValue(int inputValue) {
		if (inputValue < 0) {
			this.setError(1);
			this.setProcessable(false);
		} else {
			this.inputValue = inputValue;

		}

	}

	public int getInputValue() {
		return inputValue;
	}

	public void setSystemType(int inputSystemType, int destinationSystemType) {
		if ((inputSystemType > 25 || inputSystemType < 2)
				| (destinationSystemType > 25 || destinationSystemType < 2)) {
			this.setError(2);
			this.setProcessable(false);
			this.setIsInputCorrect(false);
		} else {
			setInputSystemType(inputSystemType);
			setDestinationSystemType(destinationSystemType);

		}
	}

	// Getters and Setters are finished

	// Main logic

	private void converToDecimalValue(int inputValue, int inputSystemType) {
		// Adding stack to support string loop
		Stack<Integer> letters = new Stack<Integer>();
		String inputValueString = Integer.toString(inputValue);
		// Adding the string loop to push the chars to a stack
		for (int index = 0; index < inputValueString.length(); index++) {
			Character aChar = new Character(inputValueString.charAt(index));
			String s = aChar.toString();
			int number = Integer.parseInt(s);
			letters.push(number);
		}
		// Counter is needed to tell the position of the digit and the number
		int counter = 0;
		while (!letters.empty()) {
			Integer value = ((Integer) letters.pop());
			// Checking the result with a println
			// System.out.println(value.intValue() + " " +
			// this.convertedToDecimalValue);
			this.convertedToDecimalValue += (int) (Math.pow(inputSystemType,
					counter++) * value.intValue());

		}
	}

	private void convertToDestinationBase(int convertedToDecimalValue,
			int destinationSystemBase) {
		// Adding the stack to support digit by digit conversion
		Stack<Integer> numbers = new Stack<Integer>();
		while (convertedToDecimalValue > 0) {
			int remain = convertedToDecimalValue % destinationSystemBase;
			numbers.push(remain);
			convertedToDecimalValue /= destinationSystemBase;

		}

		// Adding support for the base 16
		if (destinationSystemBase == 16) {
			String values = "0123456789ABCDEF";
			while (!numbers.empty()) {
				int result = numbers.pop();
				this.convertedValue += values.charAt(result);

			}

		} else {
			while (!numbers.empty()) {
				this.convertedValue += numbers.pop();
			}

		}

	}

}
