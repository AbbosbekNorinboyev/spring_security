package uz.pdp.task1.springframeworkjavaconfig.daos.users;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import uz.pdp.task1.springframeworkjavaconfig.domains.AuthUser;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthUserDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void saveAuthUser(@NonNull AuthUser authUser) {
        var sql = "insert into authuser(username, password, role) values(:username, :password, :role)";
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(authUser);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    public Long saveAuthUserReturnId(@NonNull AuthUser authUser) {
        var sql = "insert into authuser(username, password) values(:username, :password)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("username", authUser.getUsername())
                .addValue("password", authUser.getPassword());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder, new String[]{"id"});
        return (Long) keyHolder.getKeys().get("id");
    }

    public AuthUser findUserById(@NonNull Long id) {
        var sql = "select * from authuser where id = :id";
        return namedParameterJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("id", id),
                new AuthUserRowMapper());
    }

    public Optional<AuthUser> findUserByUsername(@NonNull String username) {
        var sql = "select * from authuser where username = :username";
        var rowMapper = BeanPropertyRowMapper.newInstance(AuthUser.class);
        try {
            AuthUser authUser = namedParameterJdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource("username", username),
                    rowMapper);
            return Optional.of(authUser);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<AuthUser> findUserAll() {
        var sql = "select * from authuser";
        return namedParameterJdbcTemplate.query(sql, new AuthUserRowMapper());
    }

    public void updateAuthUser(@NonNull AuthUser authUser) {
        var sql = "update authuser set username = :username, password = :password, role = :role";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("username", authUser.getUsername())
                .addValue("password", authUser.getPassword());
//                .addValue("role", authUser.getRole());
        int update = namedParameterJdbcTemplate.update(sql, sqlParameterSource);
        if (update != 0) {
            System.out.println("AuthUser updated with id: " + authUser.getId());
        } else {
            System.out.println("AuthUser not found with id: " + authUser.getId());
        }
    }

    public void deleteAuthUser(@NonNull Long id) {
        var sql = "delete from authuser where id = :id";
        int delete = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
        if (delete != 0) {
            System.out.println("AuthUser deleted with id: " + id);
        } else {
            System.out.println("AuthUser not found with id: " + id);
        }
    }
}
