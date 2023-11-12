package se.ifmo.ru.soa_2023_lab2_service2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import se.ifmo.ru.soa_2023_lab2_service2.external.model.RestClientTicket;
import se.ifmo.ru.soa_2023_lab2_service2.service.model.Color;
import se.ifmo.ru.soa_2023_lab2_service2.service.model.Ticket;
import se.ifmo.ru.soa_2023_lab2_service2.service.model.TicketType;
import se.ifmo.ru.soa_2023_lab2_service2.web.model.TicketGetResponseDto;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "jakarta")
public interface TicketMapper {
    TicketGetResponseDto toDto(Ticket source);
    List<TicketGetResponseDto> toGetResponseDtoList(List<Ticket> source);
    Ticket fromRestClient(RestClientTicket restClientFlat);
    List<Ticket> fromRestClient(List<RestClientTicket> restClientFlat);

    default String fromTicketType(TicketType type) {
        return type.toString();
    }

    default String fromHairColor(Color color) {
        return Objects.requireNonNullElse(color, Color.NONE).toString();
    }

    default TicketType fromStringTicketType(String type){
        return TicketType.fromValue(type);
    }

    default Color fromStringColor(String color){
        return Color.fromValue(color);
    }
}

