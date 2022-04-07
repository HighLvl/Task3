package ru.nsu.cherepanov.task.dto;

import java.math.BigInteger;

public class MemberDto {
    private String type;
    private BigInteger ref;
    private String role;

    public MemberDto(String type, BigInteger ref, String role) {
        this.type = type;
        this.ref = ref;
        this.role = role;
    }

    public MemberDto() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigInteger getRef() {
        return ref;
    }

    public void setRef(BigInteger ref) {
        this.ref = ref;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
