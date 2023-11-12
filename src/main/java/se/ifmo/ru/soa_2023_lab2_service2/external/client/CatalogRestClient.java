package se.ifmo.ru.soa_2023_lab2_service2.external.client;

import jakarta.ejb.Stateless;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import se.ifmo.ru.soa_2023_lab2_service2.external.model.RestClientTicket;
import se.ifmo.ru.soa_2023_lab2_service2.external.model.TicketListGetResponseDto;
import se.ifmo.ru.soa_2023_lab2_service2.util.ResponseUtils;
import se.ifmo.ru.soa_2023_lab2_service2.web.model.TicketAddOrUpdateRequestDto;

import java.util.Arrays;
import java.util.List;

@Stateless
@Slf4j
public class CatalogRestClient {
    private Client client;
    private final String serviceUrl = "http://localhost:8080/soa_2023_lab2_service1-1.0-SNAPSHOT/service";


    public RestClientTicket getTicketById(int id){
        String url = serviceUrl + "/tickets/" + id;
        try {
            client = ClientBuilder.newClient();

            Response response = client.target(url).request(MediaType.APPLICATION_XML_TYPE).get();

            RestClientTicket ticket = response.readEntity(RestClientTicket.class);

            client.close();

            return ticket;

        } catch (ProcessingException e) {
            log.error("Aboba: ", e);
            return null;
        }

    }



    public List<RestClientTicket> getAllTickets(){
        String url = serviceUrl + "/tickets";

        try {
            client = ClientBuilder.newClient();

            Response response = client.target(url).request(MediaType.APPLICATION_XML).get();
            List<RestClientTicket> flats = Arrays.asList(response.readEntity(RestClientTicket[].class));

            client.close();

            return flats;
        } catch (ProcessingException e){
            log.error("Aboba: ", e);
            return null;
        }
    }

    public RestClientTicket addTicket(TicketAddOrUpdateRequestDto requestDto) {
        String url = serviceUrl + "/tickets";

        try {
            client = ClientBuilder.newClient();
            System.out.println(Entity.xml(requestDto));
            Response response = client.target(url).request(MediaType.APPLICATION_XML_TYPE).post(Entity.xml(
                    requestDto
            ));
            System.out.println(response.getStatus());
            RestClientTicket ticket = response.readEntity(RestClientTicket.class);

            client.close();
            return ticket;

        } catch (ProcessingException e) {
            log.error("Aboba: ", e);
            return null;
        }
    }

    public boolean deleteTicket(int id) {
        String url = serviceUrl + "/tickets/" + id;
        try {
            client = ClientBuilder.newClient();

            Response response = client.target(url).request(MediaType.APPLICATION_XML_TYPE).delete();

            return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();

        } catch (ProcessingException e) {
            log.error("Aboba: ", e);
            return false;
        }

    }
}
