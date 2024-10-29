package ru.mxmztsv.model.soap;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mxmztsv.model.Category;
import ru.mxmztsv.model.Status;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "ClientResponse")
@XmlType(name = "ClientResponse", namespace = "http://app.mxmztsv.ru/model")
public class ClientResponse {
    private int id;
    private String firstName;
    private String lastName;
    private Status status;
    private Category category;
    private String createdAt;
}
