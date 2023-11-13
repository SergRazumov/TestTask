package ru.example.testTask.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class Record {

    private int id;

    private String name;

    private LocalDateTime startDateTime;
    private String nameService;

    private LocalDateTime endDateTime;
}
