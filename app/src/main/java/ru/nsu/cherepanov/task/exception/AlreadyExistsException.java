package ru.nsu.cherepanov.task.exception;

import java.math.BigInteger;

public class AlreadyExistsException extends OSMException {
    private static final String MSG = "Entity with id %d already exists";

    public AlreadyExistsException(BigInteger id){
        super(String.format(MSG, id));
    }
}