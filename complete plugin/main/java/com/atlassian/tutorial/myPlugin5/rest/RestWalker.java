package com.atlassian.tutorial.myPlugin5.rest;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.tutorial.myPlugin5.model.Walker;
import com.atlassian.tutorial.myPlugin5.model.WalkerImpl;
import com.atlassian.tutorial.myPlugin5.model.WalkerManager;
import com.atlassian.tutorial.myPlugin5.model.WalkerProb;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/restWalker")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class RestWalker {
    private final ActiveObjects ao;

    public RestWalker(ActiveObjects ao) {
        this.ao = ao;
    }

    @GET
    @Path("walkers")
    public Response getWalkers(@Context HttpServletRequest req) {
        Walker[] walkers = new WalkerManager(ao).getAllWalkers();
        WalkerProb[] walkerProbs = new WalkerProb[walkers.length];
        for (int i = 0; i < walkerProbs.length; i++) {
            walkerProbs[i] = new WalkerProb(walkers[i].getLastName(), walkers[i].getFirstName(),
                    walkers[i].getMiddleName(), walkers[i].getBirthday(), walkers[i].getPhone(),
                    walkers[i].getEmail());
        }
        return Response.ok(walkerProbs).build();
    }

    @POST
    @Path("/newWalker")
    public Response postWalker(WalkerImpl walkerImpl) {
        String lastName = walkerImpl.getLastName();
        String firstName = walkerImpl.getFirstName();
        String middleName = walkerImpl.getMiddleName();
        Date birthday = walkerImpl.getBirthday();
        String phone = walkerImpl.getPhone();
        String email = walkerImpl.getEmail();
        WalkerProb walkerProb = new WalkerProb(lastName, firstName, middleName, birthday, phone, email);
        new WalkerManager(ao).addWalker(lastName, firstName, middleName, birthday, phone, email);
        return Response.ok(walkerProb).build();
    }

    @PUT
    @Path("/updWalker/{idWalker}")
    public Response putWalker(WalkerImpl walkerImpl,
                              @PathParam(value = "idWalker") Integer idWalker) {
        Walker walker = new WalkerManager(ao).getWalkerById(idWalker);
        WalkerProb walkerProb = null;
        if (walker != null) {
            String lastName = walkerImpl.getLastName();
            String firstName = walkerImpl.getFirstName();
            String middleName = walkerImpl.getMiddleName();
            Date birthday = walkerImpl.getBirthday();
            if (birthday == null)
                birthday = walker.getBirthday();
            String phone = walkerImpl.getPhone();
            String email = walkerImpl.getEmail();
            walkerProb = new WalkerProb(lastName, firstName, middleName, birthday, phone, email);
            new WalkerManager(ao).updateWalker(idWalker, lastName, firstName, middleName, birthday, phone, email);
        }
        return Response.ok(walkerProb).build();
    }

    @DELETE
    @Path("delWalker/{id}")
    public Response deleteWalker(@Context HttpServletRequest req,
                                 @PathParam(value = "id") Integer id) {
        Walker walker = new WalkerManager(ao).getWalkerById(id);
        if (walker != null) {
            new WalkerManager(ao).deleteWalker(id);
        }
        return Response.ok(null).build();
    }
}