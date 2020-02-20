package com.atlassian.tutorial.myPlugin5.rest;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.tutorial.myPlugin5.model.Client;
import com.atlassian.tutorial.myPlugin5.model.ClientImpl;
import com.atlassian.tutorial.myPlugin5.model.ClientManager;
import com.atlassian.tutorial.myPlugin5.model.ClientProb;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/restClient")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class RestClient {
    private final ActiveObjects ao;

    public RestClient(ActiveObjects ao) {
        this.ao = ao;
    }

    @GET
    @Path("clients")
    public Response getClients(@Context HttpServletRequest req) {
        Client[] clients = new ClientManager(ao).getAllClients();
        ClientProb[] clientProbs = new ClientProb[clients.length];
        for (int i = 0; i < clientProbs.length; i++) {
            clientProbs[i] = new ClientProb(clients[i].getLastName(), clients[i].getFirstName(),
                    clients[i].getMiddleName(), clients[i].getBirthday(), clients[i].getAddress(),
                    clients[i].getPhone(), clients[i].getEmail());
        }
        return Response.ok(clientProbs).build();
    }

    @POST
    @Path("/newClient")
    public Response postClient(ClientImpl clientImpl) {
        String lastName = clientImpl.getLastName();
        String firstName = clientImpl.getFirstName();
        String middleName = clientImpl.getMiddleName();
        Date birthday = clientImpl.getBirthday();
        String address = clientImpl.getAddress();
        String phone = clientImpl.getPhone();
        String email = clientImpl.getEmail();
        ClientProb clientProb = new ClientProb(lastName, firstName, middleName, birthday, address, phone, email);
        new ClientManager(ao).addClient(lastName, firstName, middleName, birthday, address, phone, email);

        return Response.ok(clientProb).build();
    }

    @PUT
    @Path("/updClient/{idClient}")
    public Response putClient(ClientImpl clientImpl,
                              @PathParam(value = "idClient") Integer idClient) {
        Client client = new ClientManager(ao).getClientById(idClient);
        ClientProb clientProb = null;
        if (client != null) {
            String lastName = clientImpl.getLastName();
            String firstName = clientImpl.getFirstName();
            String middleName = clientImpl.getMiddleName();
            Date birthday = clientImpl.getBirthday();
            if (birthday == null)
                birthday = client.getBirthday();
            String address = clientImpl.getAddress();
            String phone = clientImpl.getPhone();
            String email = clientImpl.getEmail();
            clientProb = new ClientProb(lastName, firstName, middleName, birthday, address, phone, email);
            new ClientManager(ao).updateClient(idClient, lastName, firstName, middleName, birthday, address, phone, email);
        }
        return Response.ok(clientProb).build();
    }

    @DELETE
    @Path("/delClient/{id}")
    public Response deleteClient(@PathParam(value = "id") Integer id) {
        Client client = new ClientManager(ao).getClientById(id);
        if (client != null) {
            new ClientManager(ao).deleteClient(id);
        }
        return Response.ok(null).build();
    }
}