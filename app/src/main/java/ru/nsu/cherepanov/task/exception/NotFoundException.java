package ru.nsu.cherepanov.task.exception;

import java.math.BigInteger;

public class NotFoundException extends OSMException {
    private static final String MSG = "Entity with id %d not found";

    public NotFoundException(BigInteger id){
        super(String.format(MSG, id));
    }
}
