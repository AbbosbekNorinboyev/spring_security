package uz.pdp.task1.namedjdbctemplate;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

public class TodoDAO {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TodoDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(Todo todo) {
        var sql = "insert into todo(title, priority) values(:title, :priority)";
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(todo);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    public Integer saveReturnId(Todo todo) {
        var sql = "insert into todo(title, priority) values(:title, :priority)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("title", todo.getTitle())
                .addValue("priority", todo.getPriority());
        namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder, new String[]{"id"});
        return (Integer) keyHolder.getKeys().get("id");
    }

    public Todo findTodoById(Integer id) {
        var sql = "select * from todo where id = :id";
        return namedParameterJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("id", id),
                new TodoRowMapper());
    }

    public Todo findTodoByTitle(String title) {
        var sql = "select * from todo where title = :title";
        return namedParameterJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("title", title),
                new TodoRowMapper());
    }

    public List<Todo> findTodoAll() {
        var sql = "select * from todo";
        return namedParameterJdbcTemplate.query(sql, new TodoRowMapper());
    }

    public void updateTodo(Todo todo) {
        var sql = "update todo set title = :title, priority = :priority where id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("title", todo.getTitle())
                .addValue("priority", todo.getPriority())
                .addValue("id", todo.getId());
        int update = namedParameterJdbcTemplate.update(sql, sqlParameterSource);
        if (update != 0) {
            System.out.println("Todo updated with id: " + todo.getId());
        } else {
            System.out.println("Todo not found with id: " + todo.getId());
        }
    }

    public void deleteTodo(Integer id) {
        var sql = "delete from todo where id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("id", id);
        int delete = namedParameterJdbcTemplate.update(sql, sqlParameterSource);
        if (delete != 0) {
            System.out.println("Todo deleted with id: " + id);
        } else {
            System.out.println("Todo not found with id: " + id);
        }
    }
}
