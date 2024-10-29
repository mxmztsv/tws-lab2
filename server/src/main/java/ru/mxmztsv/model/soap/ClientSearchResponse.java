package ru.mxmztsv.model.soap;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "ClientSearchResponse", namespace = "http://app.mxmztsv.ru/")
@XmlType(name = "ClientSearchResponseType", namespace = "http://app.mxmztsv.ru/")
public class ClientSearchResponse {

    private List<SearchClientsResponseModel> responseModelList;

}
