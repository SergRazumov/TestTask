package ru.example.testTask.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.example.testTask.model.Service;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

@Component
public class ServiceDAO {

    private final JdbcTemplate jdbcTemplate;

    private ServiceDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Service> showAll() {
        return jdbcTemplate.query("SELECT * FROM Services", new BeanPropertyRowMapper<>(Service.class));
    }

    public String addService(String name, LocalTime time, Integer identifier) {
        try {
            return String.valueOf(jdbcTemplate.update("INSERT INTO services(id, name, durationTime) VALUES(?, ?, ?)", identifier, name, time));
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                return e.getMessage();
            }
        }
        return "error";
    }

    public String removeService(int number) {
        return String.valueOf(jdbcTemplate.update("DELETE FROM Services where id=?", number));
    }

}
