package ru.nsu.cherepanov.task.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class RefsConverter implements AttributeConverter<List<BigInteger>, String> {
    @Override
    public String convertToDatabaseColumn(List<BigInteger> attribute) {
        return attribute.stream().map(BigInteger::toString).collect(Collectors.joining(",", "{", "}"));
    }

    @Override
    public List<BigInteger> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.replaceAll("[{}\"]", "").split(",")).map(BigInteger::new).collect(Collectors.toList());
    }
}
