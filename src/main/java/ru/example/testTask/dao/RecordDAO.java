package ru.example.testTask.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.example.testTask.model.Record;
import ru.example.testTask.model.Service;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class RecordDAO {


    private final ServiceDAO serviceDAO;
    private final JdbcTemplate jdbcTemplate;

    private RecordDAO(ServiceDAO serviceDAO, JdbcTemplate jdbcTemplate) {
        this.serviceDAO = serviceDAO;
        this.jdbcTemplate = jdbcTemplate;
    }

    private final List<Record> records = new ArrayList<>();

    private int id = 0;

    {
        records.add(new Record(id++, "TestName", LocalDateTime.now().plusMinutes(10), "Люкс", LocalDateTime.now().plusHours(1L)));
    }


    public List<Record> showAll() {
        return jdbcTemplate.query("select records.id, records.name, records.start_time, \n" +
                "services.name as name_service, records.end_time from services, records \n" +
                "where services.id = records.id_services", new RecordMapper());
    }


    public String addRecord(String name, LocalDate dateRecord, LocalTime timeRecord, Integer idServices) {
        List<Service> services = serviceDAO.showAll();
        Service findService = services.stream().filter(service -> service.getId() == idServices).findFirst().orElse(null);
        if (findService == null) return "Not Service with current id";
        LocalDateTime startTime = dateRecord.atTime(timeRecord);

        List<Record> records = showAll();
        Record findRecord = records.stream().filter(record -> (record.getStartDateTime().isBefore(startTime) || record.getStartDateTime().isEqual(startTime)) && record.getEndDateTime().isAfter(startTime)).findAny().orElse(null);
        if (findRecord != null) return "This date and time already busy";

        return String.valueOf(jdbcTemplate.update("INSERT INTO records(name, start_time, id_services, end_time) VALUES(?, ?, ?, ?)",
                name, startTime, idServices, startTime.plusHours(findService.getDurationTime().getHour()).plusMinutes(findService.getDurationTime().getMinute())));
    }

    public String timeLeft(Integer id) {
        for (Record record : showAll()) {
            if (record.getId() == id) {
                return format(Duration.between(LocalDateTime.now(), record.getStartDateTime()));
            }
        }
        return "Not record";
    }

    private String format(Duration d) {
        long days = d.toDays();
        d = d.minusDays(days);
        long hours = d.toHours();
        d = d.minusHours(hours);
        long minutes = d.toMinutes();
        return
                (days == 0 ? "" : days + " days ") +
                        (hours == 0 ? "" : hours + " hours ") +
                        (minutes == 0 ? "" : minutes + " minutes");
    }

}
