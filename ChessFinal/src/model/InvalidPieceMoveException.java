package model;

@SuppressWarnings("serial")
public class InvalidPieceMoveException extends RuntimeException {
	public InvalidPieceMoveException(String message)
    {
        super(message);
    }
}
