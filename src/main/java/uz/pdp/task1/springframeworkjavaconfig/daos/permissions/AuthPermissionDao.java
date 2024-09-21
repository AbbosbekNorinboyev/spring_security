package uz.pdp.task1.springframeworkjavaconfig.daos.permissions;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import uz.pdp.task1.springframeworkjavaconfig.domains.AuthPermission;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthPermissionDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<AuthPermission> findAllAuthPermissionById(@NonNull Long roleId) {
        var sql = "select ap.* from authrole_authpermission arap inner join authpermission ap on ap.id = arap.permission_id where arap.role_id = :roleId;";
        var sqlParamSource = new MapSqlParameterSource("roleId", roleId);
        try {
            return namedParameterJdbcTemplate.query(sql, sqlParamSource, (resultSet, rowNumber) -> AuthPermission.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .code(resultSet.getString("code"))
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void saveAuthPermission(AuthPermission authPermission) {
        var sql = "insert into authpermission(name, code) values(:name, :code)";
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(authPermission);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    public Long saveAuthPermissionReturnId(AuthPermission authPermission) {
        var sql = "insert into authpermission(name, code) values(:code, :name)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", authPermission.getName())
                .addValue("code", authPermission.getCode());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder, new String[]{"id"});
        return (Long) keyHolder.getKeys().get("id");
    }

    public AuthPermission findAuthPermissionById(@NonNull Long id) {
        var sql = "select * from authpermission where id = :id";
        return namedParameterJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("id", id),
                new AuthPermissionRowMapper());
    }

    public AuthPermission findAuthPermissionByName(@NonNull String name) {
        var sql = "select * from authpermission where name = :name";
        return namedParameterJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("name", name),
                new AuthPermissionRowMapper());
    }

    public void updateAuthPermission(AuthPermission authPermission) {
        var sql = "update authpermission where set name = :name, code = :code where id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", authPermission.getName())
                .addValue("code", authPermission.getCode());
        int update = namedParameterJdbcTemplate.update(sql, sqlParameterSource);
        if (update != 0) {
            System.out.println("AuthPermission updated with id: " + authPermission.getId());
        } else {
            System.out.println("AuthPermission not found with id: " + authPermission.getId());
        }
    }

    public void deleteAuthPermission(Long id) {
        var sql = "delete from authpermission where id = :id";
        int delete = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
        if (delete != 0) {
            System.out.println("AuthPermission deleted with id: " + id);
        } else {
            System.out.println("AuthPermission not found id: " + id);
        }
    }
}
