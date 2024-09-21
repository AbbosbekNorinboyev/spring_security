package uz.pdp.task1.springframeworkjavaconfig.daos.users;

import org.springframework.jdbc.core.RowMapper;
import uz.pdp.task1.springframeworkjavaconfig.domains.AuthRole;
import uz.pdp.task1.springframeworkjavaconfig.domains.AuthUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AuthUserRowMapper implements RowMapper<AuthUser> {

    @Override
    public AuthUser mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return AuthUser.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .created_at(resultSet.getTimestamp("created_at").toLocalDateTime())
                .roles((List< AuthRole >) resultSet.getArray("roles"))
                .build();
    }
}
