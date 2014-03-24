package model;

@SuppressWarnings("serial")
public class AlreadyDoneException extends RuntimeException {
    public AlreadyDoneException(String message)
    {
        super(message);
    }
}

