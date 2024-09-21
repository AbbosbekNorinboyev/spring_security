package uz.pdp.task1.springframeworkjavaconfig.daos.roles;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import uz.pdp.task1.springframeworkjavaconfig.domains.AuthRole;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthRoleDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<AuthRole> findAllAuthRoleByUserId(@NonNull Long userId) {
        var sql = "select ar.* from authuser_authrole auar inner join authrole ar on ar.id = auar.role_id where auar.user_id = :userId";
        var sqlParamSource = new MapSqlParameterSource("userId", userId);
        try {
            return namedParameterJdbcTemplate.query(sql, sqlParamSource, (resultSet, rowNumber) -> AuthRole.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .code(resultSet.getString("code"))
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void saveAuthRole(@NonNull AuthRole authRole) {
        var sql = "insert into authrole(name, code) values(:name, :code)";
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(authRole);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    public Long saveAuthRoleReturnId(@NonNull AuthRole authRole) {
        var sql = "insert into authrole(name, code) values(:name, :code)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", authRole.getName())
                .addValue("code", authRole.getCode());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder, new String[]{"id"});
        return (Long) keyHolder.getKeys().get("id");
    }

    public AuthRole findAuthRoleById(@NonNull Long id) {
        var sql = "select * from authrole where id = :id";
        return namedParameterJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("id", id),
                new AuthRoleRowMapper());
    }

    public AuthRole findAuthRoleByName(@NonNull String name) {
        var sql = "select * from authrole where name = :name";
        return namedParameterJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("name", name),
                new AuthRoleRowMapper());
    }

    public void updateAuthRole(AuthRole authRole) {
        var sql = "update authrole set name = :name, code = :code where id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", authRole.getName())
                .addValue("code", authRole.getCode())
                .addValue("id", authRole.getId());
        int update = namedParameterJdbcTemplate.update(sql, sqlParameterSource);
        if (update != 0) {
            System.out.println("AuthRole updated with id: " + authRole.getId());
        } else {
            System.out.println("AuthRole not found with id: " + authRole.getId());
        }
    }

    public void deleteAuthRole(Long id) {
        var sql = "delete from authrole where id = :id";
        int delete = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
        if (delete != 0) {
            System.out.println("AuthRole deleted with id: " + id);
        } else {
            System.out.println("AuthRole not found with id: " + id);
        }
    }
}
