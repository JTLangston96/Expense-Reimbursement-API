package dev.langst.exceptions;

public class ObjectNotFound extends RuntimeException{

    public ObjectNotFound(){
        super("Could not find the specified object from the database.");
    }
}
