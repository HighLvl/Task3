package ru.nsu.cherepanov.task.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

public class Member implements Serializable {
    private String type;
    private BigInteger ref;
    private String role;

    public Member(String type, BigInteger ref, String role) {
        this.type = type;
        this.ref = ref;
        this.role = role;
    }

    public Member() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return type.equals(member.type) && ref.equals(member.ref) && role.equals(member.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, ref, role);
    }

    @Override
    public String toString() {
        return String.format("(%s,%d,%s)", type, ref, role);
    }
}
