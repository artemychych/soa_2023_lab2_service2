package se.ifmo.ru.soa_2023_lab2_service2.external.model;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.ext.JodaDeserializers;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlType
@XmlRootElement(name = "Ticket")
public class RestClientTicket {
    private Integer id;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private RestClientTicketCoordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float price; //Поле может быть null, Значение поля должно быть больше 0
    private String type; //Поле не может быть null
    private RestClientTicketPerson person; //Поле может быть null

    @Data
    @XmlType
    @XmlRootElement(name = "Coordinates")
    public static class RestClientTicketCoordinates {
        private Integer x; //Поле не может быть null
        private int y;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlType
    @XmlRootElement(name = "Person")
    public static class RestClientTicketPerson {
        private Long weight; //Поле может быть null, Значение поля должно быть больше 0
        private String hairColor; //Поле не может быть null
        private RestClientTicketLocation location; //Поле не может быть null
    }

    @Data
    @XmlType
    @XmlRootElement(name = "Location")
    public static class RestClientTicketLocation {
        private Float x; //Поле не может быть null
        private long y;
        private double z;
    }

}
