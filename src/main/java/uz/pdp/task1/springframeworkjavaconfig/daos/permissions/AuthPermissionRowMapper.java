package uz.pdp.task1.springframeworkjavaconfig.daos.permissions;

import org.springframework.jdbc.core.RowMapper;
import uz.pdp.task1.springframeworkjavaconfig.domains.AuthPermission;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthPermissionRowMapper implements RowMapper<AuthPermission> {

    @Override
    public AuthPermission mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return AuthPermission.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .code(resultSet.getString("code"))
                .created_at(resultSet.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}
