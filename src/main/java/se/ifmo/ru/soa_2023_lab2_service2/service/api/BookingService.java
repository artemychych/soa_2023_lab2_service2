package se.ifmo.ru.soa_2023_lab2_service2.service.api;

import se.ifmo.ru.soa_2023_lab2_service2.service.model.Ticket;

public interface BookingService {
    Ticket copyTicketWithVipAndDoublePrice(int id);

    boolean deleteAllTicketsWithNameAndCoordinates(String name, int x, int y);
}
