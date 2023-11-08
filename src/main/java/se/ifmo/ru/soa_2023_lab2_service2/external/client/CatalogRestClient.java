package se.ifmo.ru.soa_2023_lab2_service2.external.client;

import jakarta.ejb.Stateless;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.ifmo.ru.soa_2023_lab2_service2.external.model.RestClientTicket;
import se.ifmo.ru.soa_2023_lab2_service2.external.model.TicketListGetResponseDto;

import java.util.List;

@Stateless
public class CatalogRestClient {
    private Client client;
    private final String serviceUrl = "https://localhost:8080/soa_2023_lab2_service1-1.0-SNAPSHOT/service/";


    public RestClientTicket getFlatById(long id){
        String url = serviceUrl + "/tickets/" + id;
        try {
            client = ClientBuilder.newClient();

            Response response = client.target(url).request(MediaType.APPLICATION_XML_TYPE).get();

            RestClientTicket ticket = response.readEntity(RestClientTicket.class);

            client.close();

            return ticket;

        } catch (ProcessingException e) {
            return null;
        }

    }

    public List<RestClientTicket> getAllFlats(){
        String url = serviceUrl + "/tickets";

        try {
            client = ClientBuilder.newClient();

            Response response = client.target(url).request(MediaType.APPLICATION_XML_TYPE).get();

            TicketListGetResponseDto flats = response.readEntity(TicketListGetResponseDto.class);

            client.close();

            return flats.getTicketGetResponseDtos();
        } catch (ProcessingException e){
            return null;
        }
    }
}
