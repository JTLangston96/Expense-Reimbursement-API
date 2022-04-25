package dev.langst.exceptions;

public class NegativeExpense extends RuntimeException{

    public NegativeExpense(){
        super("An expense must have a positive amount greater than 0.00.");
    }

}
