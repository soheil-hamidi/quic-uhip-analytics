package main;

@SuppressWarnings("serial")
public class QException extends Exception{

	public QException(String message){
		super(message);
		Output.set(message + "\n");
	}
} // end QException class