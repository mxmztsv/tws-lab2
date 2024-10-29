package ru.mxmztsv.model.soap;

import jakarta.xml.bind.annotation.XmlType;
import lombok.Builder;
import lombok.Data;
import ru.mxmztsv.model.Category;
import ru.mxmztsv.model.Status;

@Data
@Builder
@XmlType(name = "SearchClientsResponseModelType", namespace = "http://app.mxmztsv.ru/model")
public class SearchClientsResponseModel {
    private int id;
    private String firstName;
    private String lastName;
    private Status status;
    private Category category;
    private String createdAt;
}
