package ru.mxmztsv.model.soap;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;
import ru.mxmztsv.model.Category;
import ru.mxmztsv.model.Status;

@Data
@XmlRootElement(name = "searchClientsRequest")
@XmlType(name = "SearchClientsRequest", namespace = "http://app.mxmztsv.ru/")
public class SearchClientsRequest {
    private String firstName;
    private String lastName;
    private Status status;
    private Category category;

    public String toString(){
        return "First Name: " + firstName + " Last Name: " + lastName + " Status: " + status;
    }
}
