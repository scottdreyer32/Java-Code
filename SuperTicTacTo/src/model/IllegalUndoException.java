package model;

@SuppressWarnings("serial")
public class IllegalUndoException extends IllegalArgumentException{
    public IllegalUndoException(String message)
    {
        super(message);
    }
}
