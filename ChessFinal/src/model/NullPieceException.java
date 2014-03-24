package model;

@SuppressWarnings("serial")
public class NullPieceException extends RuntimeException{
	public NullPieceException(String message)
    {
        super(message);
    }
}
