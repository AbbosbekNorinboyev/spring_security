package uz.pdp.task1.springframeworkjavaconfig.daos.roles;

import org.springframework.jdbc.core.RowMapper;
import uz.pdp.task1.springframeworkjavaconfig.domains.AuthPermission;
import uz.pdp.task1.springframeworkjavaconfig.domains.AuthRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AuthRoleRowMapper implements RowMapper<AuthRole> {
    @Override
    public AuthRole mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return AuthRole.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .code(resultSet.getString("code"))
                .created_at(resultSet.getTimestamp("created_at").toLocalDateTime())
                .permissions((List<AuthPermission>) resultSet.getArray("permissions"))
                .build();
    }
}
