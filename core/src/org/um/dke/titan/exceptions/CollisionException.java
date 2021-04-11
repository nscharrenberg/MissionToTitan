package org.um.dke.titan.exceptions;

public class CollisionException extends Exception {
    public CollisionException(String message) {
        super(message);
    }

    public CollisionException() {
        super("The objects have collided with each other.");
    }

    public CollisionException(String name1, String name2) {
        super(String.format("%s collided with %s", name1, name2));
    }
}
