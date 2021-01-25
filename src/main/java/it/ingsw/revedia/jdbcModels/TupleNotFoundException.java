package it.ingsw.revedia.jdbcModels;

public class TupleNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 12L;

    public TupleNotFoundException(String message)
    {
        super(message);
    }

    public TupleNotFoundException(Throwable cause)
    {
        super(cause);
    }

    public TupleNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
