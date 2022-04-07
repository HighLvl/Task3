package ru.nsu.cherepanov.task.entity;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MembersType implements UserType {
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.ARRAY};
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<List<Member>> returnedClass() {
        return (Class<List<Member>>) Collections.<Member>emptyList().getClass();
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        var ps = session.connection().prepareStatement("select * from unnest((select members from relation r where r.id = ?))");
        ps.setBigDecimal(1, new BigDecimal(((RelationEntity) owner).getId()));
        var resultSet = ps.executeQuery();
        var members = new ArrayList<Member>();
        while (resultSet.next()) {
            var type = resultSet.getString("type");
            var ref = resultSet.getBigDecimal("ref").toBigInteger();
            var role = resultSet.getString("role");
            members.add(new Member(type, ref, role));
        }
        return members;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        var connection = st.getConnection();
        var members = (List<Member>) value;
        st.setArray(index, connection.createArrayOf("member", members.toArray()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object deepCopy(Object value) throws HibernateException {
        var members = (List<Member>) value;

        return members.stream().map((it) -> new Member(it.getType(), it.getRef(), it.getRole())).collect(Collectors.toList());
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) this.deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return this.deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return this.deepCopy(original);
    }
}
