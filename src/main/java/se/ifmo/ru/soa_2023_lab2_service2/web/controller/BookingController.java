package se.ifmo.ru.soa_2023_lab2_service2.web.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.ifmo.ru.soa_2023_lab2_service2.mapper.TicketMapper;
import se.ifmo.ru.soa_2023_lab2_service2.service.api.BookingService;
import se.ifmo.ru.soa_2023_lab2_service2.util.ResponseUtils;

@Path("/service")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class BookingController {
    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok("PING").build();
    }


    @Inject
    BookingService bookingService;

    @Inject
    ResponseUtils responseUtils;

    @Inject
    TicketMapper ticketMapper;

    @POST
    @Path("/sell/vip/{ticket-id}")
    public Response copyTicketVIPAndDoublePrice(@PathParam("ticket-id") int id) {
        if (id >= 1) {
            return Response.ok()
                    .entity(ticketMapper.toDto(
                            bookingService.copyTicketWithVipAndDoublePrice(id)
                    ))
                    .build();
        }
        throw new IllegalArgumentException("Invalid parameters!");
    }

    @DELETE
    @Path("/event/{ticket-name}/{coorX}/{coorY}/cancel")
    public Response deleteAllTicketsWithNameAndCoordinates
            (@PathParam("ticket-name") String name,
             @PathParam("coorX") int x,
             @PathParam("coorY") int y) {
        boolean deleted = bookingService.deleteAllTicketsWithNameAndCoordinates(
                name, x, y
        );

        if (!deleted) {
            return responseUtils.buildResponseWithMessage(Response.Status.NOT_FOUND, "Flat with name " + name + " not found");
        }

        return Response.noContent().build();
    }
}
