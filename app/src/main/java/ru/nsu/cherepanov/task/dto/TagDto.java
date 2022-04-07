package ru.nsu.cherepanov.task.dto;

public class TagDto {
    private String k;
    private String v;

    public TagDto(String k, String v) {
        this.k = k;
        this.v = v;
    }

    public TagDto() {
    }

    public String getK() {
        return k;
    }

    public String getV() {
        return v;
    }

    public void setK(String k) {
        this.k = k;
    }

    public void setV(String v) {
        this.v = v;
    }
}
