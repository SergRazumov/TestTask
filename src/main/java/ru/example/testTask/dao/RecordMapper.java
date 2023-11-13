package ru.example.testTask.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.example.testTask.model.Record;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecordMapper implements RowMapper<Record> {
    @Override
    public Record mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Record(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getTimestamp("start_time").toLocalDateTime(), resultSet.getString("name_service"),
                resultSet.getTimestamp("end_time").toLocalDateTime());
    }
}
