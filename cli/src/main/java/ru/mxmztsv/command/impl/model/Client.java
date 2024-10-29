package ru.mxmztsv.command.impl.model;

import lombok.Builder;
import lombok.Data;
import ru.mxmztsv.app.Category;
import ru.mxmztsv.app.Status;

@Data
@Builder
public class Client {
    private Long id;
    private String firstName;
    private String lastName;
    private Status status;
    private Category category;
    private String createdAt;
}
