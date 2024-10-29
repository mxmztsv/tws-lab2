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
@XmlRootElement(name = "ClientRequest")
@XmlType(name = "ClientRequest", namespace = "http://app.mxmztsv.ru/model")
public class ClientRequest {
    private String firstName;
    private String lastName;
    private Status status;
    private Category category;
    private String createdAt;

    public String toQueryStringUpdate() {
        StringBuilder stringBuilder = new StringBuilder();

        if (setField(firstName)) {
            stringBuilder.append("first_name = '").append(firstName).append("', ");
        }
        if (setField(lastName)) {
            stringBuilder.append("last_name = '").append(lastName).append("', ");
        }
        if (setField(status)) {
            stringBuilder.append("status = '").append(status).append("', ");
        }
        if (setField(category)) {
            stringBuilder.append("category = '").append(category).append("', ");
        }
        if (setField(createdAt)) {
            stringBuilder.append("created_at = '").append(createdAt).append("', ");
        }

        if (!stringBuilder.isEmpty()) {
            stringBuilder.setLength(stringBuilder.length() - 2);  // убираем последние ", "
        }

        return stringBuilder.toString();
    }


    private boolean setField(Object field) {
        if (field != null) {
            if (field instanceof String) {
                return !((String) field).isEmpty();
            } else {
                return true;
            }
        }
        return false;
    }

}
