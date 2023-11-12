package se.ifmo.ru.soa_2023_lab2_service2.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.apache.commons.collections4.CollectionUtils;
import se.ifmo.ru.soa_2023_lab2_service2.external.client.CatalogRestClient;
import se.ifmo.ru.soa_2023_lab2_service2.mapper.TicketMapper;
import se.ifmo.ru.soa_2023_lab2_service2.service.api.BookingService;
import se.ifmo.ru.soa_2023_lab2_service2.service.model.Ticket;
import se.ifmo.ru.soa_2023_lab2_service2.web.model.TicketAddOrUpdateRequestDto;
import se.ifmo.ru.soa_2023_lab2_service2.web.model.TicketGetResponseDto;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BookingServiceImpl implements BookingService {
    @Inject
    private CatalogRestClient catalogRestClient;

    @Inject
    private TicketMapper ticketMapper;

    @Override
    public Ticket copyTicketWithVipAndDoublePrice(int id) {
        Ticket ticket = ticketMapper.fromRestClient(catalogRestClient.getTicketById(id));

        if (ticket == null) {
            throw new NotFoundException("Ticket with id " + id + " not found");
        }

        TicketAddOrUpdateRequestDto requestTicket = TicketAddOrUpdateRequestDto.builder()
                .name(ticket.getName())
                .coordinates(
                        TicketAddOrUpdateRequestDto.TicketCoordinatesAddResponseDto.builder()
                                .x(ticket.getCoordinates().getX())
                                .y(ticket.getCoordinates().getY()).build()
                )
                .price(ticket.getPrice())
                .type(ticket.getType().getValue())
                .person(
                        TicketAddOrUpdateRequestDto.TicketPersonAddResponseDto.builder()
                                .weight(ticket.getPerson().getWeight())
                                .hairColor(ticket.getPerson().getHairColor().getValue())
                                .location(
                                        TicketAddOrUpdateRequestDto.TicketPersonLocationAddResponseDto.builder()
                                                .x(ticket.getPerson().getLocation().getX())
                                                .y(ticket.getPerson().getLocation().getY())
                                                .z(ticket.getPerson().getLocation().getZ()).build()

                                ).build()
                ).build();
        return ticketMapper.fromRestClient(catalogRestClient.addTicket(requestTicket));
    }

    @Override
    public boolean  deleteAllTicketsWithNameAndCoordinates(String name, int x, int y) {

        List<Ticket> tickets = ticketMapper.fromRestClient(catalogRestClient.getAllTickets());

        if (CollectionUtils.isNotEmpty(tickets)){
            for (Ticket i:
                 tickets) {
                if (i.getName().equals(name) && i.getCoordinates().getX() == x && i.getCoordinates().getY() == y) {
                    boolean response = catalogRestClient.deleteTicket(i.getId());
                    if (!response) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            throw new NotFoundException("Not found list of Tickets!");
        }
    }
}
