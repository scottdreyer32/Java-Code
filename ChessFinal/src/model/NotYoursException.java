package model;

@SuppressWarnings("serial")
public class NotYoursException extends RuntimeException{
	public NotYoursException(String message)
    {
        super(message);
    }
}
