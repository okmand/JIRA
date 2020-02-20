package com.atlassian.tutorial.myPlugin5.rest;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.tutorial.myPlugin5.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/restPaddock")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class RestPaddock {
    private final ActiveObjects ao;

    public RestPaddock(ActiveObjects ao) {
        this.ao = ao;
    }

    @GET
    @Path("paddocks")
    public Response getPaddocks(@Context HttpServletRequest req) {
        Paddock[] paddocks = new PaddockManager(ao).getAllPaddocks();
        PaddockProb[] paddockProbs = new PaddockProb[paddocks.length];
        for (int i = 0; i < paddockProbs.length; i++) {
            paddockProbs[i] = new PaddockProb(paddocks[i].getPlace(), paddocks[i].getWalkingTime(),
                    paddocks[i].getDuration(), paddocks[i].getStatus(), paddocks[i].getIdDogs());
        }
        return Response.ok(paddockProbs).build();
    }

    @GET
    @Path("busyWalker/{idWalker}/{wTime}/{duration}")
    public Boolean getBusyWalker(@PathParam(value = "idWalker") Integer idWalker,
                                 @PathParam(value = "wTime") Long wTime,
                                 @PathParam(value = "duration") Integer duration) {
        final Long Time = 21600000L + duration * 60 * 1000;
        Walker walker = new WalkerManager(ao).getWalkerById(idWalker);
        boolean check = true;
        Paddock[] paddocks = new PaddockManager(ao).getPaddockByWalker(walker.getID());
        for (Paddock paddock : paddocks) {
            if (Math.abs(paddock.getWalkingTime().getTime() - wTime) < Time) {
                check = false;
            }
            if (!check)
                break;
        }
        return check;
    }

    @POST
    @Path("newPaddock/{idWalker}/{idClient}/{wTime}")
    public Response addPaddock(PaddockImpl paddockImpl,
                                @PathParam(value = "idWalker") Integer idWalker,
                                @PathParam(value = "idClient") Integer idClient,
                                @PathParam(value = "wTime") Long wTime) {
        Integer duration = paddockImpl.getDuration();
        Long time = 21600000L + duration * 60 * 1000;
        Walker walker = new WalkerManager(ao).getWalkerById(idWalker);
        Client client = new ClientManager(ao).getClientById(idClient);
        PaddockProb paddockProb = null;
        boolean check = true;
        Paddock[] paddocks = new PaddockManager(ao).getPaddockByWalker(walker.getID());
        for (Paddock paddock : paddocks) {
            if (Math.abs(wTime - paddock.getWalkingTime().getTime()) < time) {
                check = false;
            }
        }
        if (walker != null && client != null && check) {


            String idDogs = paddockImpl.getIdDogs();
            String place = paddockImpl.getPlace();
            Date walkingTime = new Date(wTime);
            String status = paddockImpl.getStatus();
            paddockProb = new PaddockProb(place, walkingTime, duration, status, idDogs);
            new PaddockManager(ao).addPaddock(place, walkingTime, wTime, duration, status, walker, client, idDogs);
        }
        return Response.ok(paddockProb).build();
    }

    @PUT
    @Path("updPaddock/{idPaddock}/{idWalker}/{idClient}/{wTime}")
    public Response putPaddock(PaddockImpl paddockImpl,
                               @PathParam(value = "idPaddock") Integer idPaddock,
                               @PathParam(value = "idWalker") Integer idWalker,
                               @PathParam(value = "idClient") Integer idClient,
                               @PathParam(value = "wTime") Long wTime) {
        Paddock paddock = new PaddockManager(ao).getPaddockById(idPaddock);
        Client client = new ClientManager(ao).getClientById(idClient);
        Walker walker = new WalkerManager(ao).getWalkerById(idWalker);
        PaddockProb paddockProb = null;
        if (paddock != null && walker != null && client != null) {
            String place = paddockImpl.getPlace();
            Date walkingTime;
            if (wTime == 0) {
                walkingTime = paddock.getWalkingTime();
                wTime = walkingTime.getTime();
            } else
                walkingTime = new Date(wTime);
            Integer duration = paddockImpl.getDuration();
            String status = paddockImpl.getStatus();
            paddockProb = new PaddockProb(place, walkingTime, duration, status, "");
            new PaddockManager(ao).updatePaddock(idPaddock, place, walkingTime, wTime, duration, status, walker, client);
        }
        return Response.ok(paddockProb).build();
    }

    @PUT
    @Path("updateStatusPaddock/{idPaddock}")
    public Response updateStatusPaddock(PaddockImpl paddockImpl,
                                        @PathParam(value = "idPaddock") Integer idPaddock) {
        Paddock paddock = new PaddockManager(ao).getPaddockById(idPaddock);
        PaddockProb paddockProb = null;
        if (paddock != null) {
            String status = paddockImpl.getStatus();
            paddockProb = new PaddockProb("place", new Date(), 40, status, "");
            new PaddockManager(ao).updateStatusPaddock(idPaddock, status);
        }
        return Response.ok(paddockProb).build();
    }


    @DELETE
    @Path("delPaddock/{id}")
    public Response deletePaddock(@Context HttpServletRequest req,
                                  @PathParam(value = "id") Integer id) {
        Paddock paddock = new PaddockManager(ao).getPaddockById(id);
        if (paddock != null) {
            new PaddockManager(ao).deletePaddock(id);
        }
        return Response.ok(null).build();
    }

}