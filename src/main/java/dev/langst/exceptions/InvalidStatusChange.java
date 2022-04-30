package dev.langst.exceptions;

public class InvalidStatusChange extends RuntimeException {

    public InvalidStatusChange() {
        super("You may not update or delete the expense unless it is \"PENDING\" and only to \'approve\" or \"deny\"");
    }
}
