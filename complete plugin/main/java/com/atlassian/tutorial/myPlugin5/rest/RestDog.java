package com.atlassian.tutorial.myPlugin5.rest;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.tutorial.myPlugin5.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/restDog")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class RestDog {
    private final ActiveObjects ao;

    public RestDog(ActiveObjects ao) {
        this.ao = ao;
    }

    @GET
    @Path("dogs")
    public Response getDogs(@Context HttpServletRequest req) {
        Dog[] dogs = new DogManager(ao).getAllDogs();
        DogProb[] dogProbs = new DogProb[dogs.length];
        for (int i = 0; i < dogProbs.length; i++) {
            dogProbs[i] = new DogProb(dogs[i].getName(), dogs[i].getGender(),
                    dogs[i].getBirthday(), dogs[i].getBreed(), dogs[i].getColor(),
                    dogs[i].getCharacter());
        }
        return Response.ok(dogProbs).build();
    }

    @POST
    @Path("/newDog/{idClient}")
    public Response postDog(DogImpl dogImpl,
                            @PathParam(value = "idClient") Integer idClient) {

        Client client = new ClientManager(ao).getClientById(idClient);
        DogProb dogProb = null;
        if (client != null) {
            String name = dogImpl.getName();
            String gender = dogImpl.getGender();
            Date birthday = dogImpl.getBirthday();
            String breed = dogImpl.getBreed();
            String color = dogImpl.getColor();
            String character = dogImpl.getCharacter();
            dogProb = new DogProb(name, gender, birthday, breed, color, character);
            new DogManager(ao).addDog(name, gender, birthday, breed, color, character, client);
        }
        return Response.ok(dogProb).build();
    }

    @PUT
    @Path("/updDog/{idDog}/{idClient}")
    public Response putDog(DogImpl dogImpl,
                           @PathParam(value = "idDog") Integer idDog,
                           @PathParam(value = "idClient") Integer idClient) {
        Client client = new ClientManager(ao).getClientById(idClient);
        Dog dog = new DogManager(ao).getDogById(idDog);
        DogProb dogProb = null;
        if (dog != null && client != null) {
            String name = dogImpl.getName();
            String gender = dogImpl.getGender();
            Date birthday = dogImpl.getBirthday();
            if (birthday == null)
                birthday = dog.getBirthday();
            String breed = dogImpl.getBreed();
            String color = dogImpl.getColor();
            String character = dogImpl.getCharacter();
            dogProb = new DogProb(name, gender, birthday, breed, color, character);
            new DogManager(ao).updateDog(idDog, name, gender, birthday, breed, color, character, client);
        }
        return Response.ok(dogProb).build();
    }

    @DELETE
    @Path("/delDog/{id}")
    public Response deleteDog(@Context HttpServletRequest req,
                              @PathParam(value = "id") Integer id) {
        Dog dog = new DogManager(ao).getDogById(id);
        if (dog != null) {
            new DogManager(ao).deleteDog(id);
        }
        return Response.ok(null).build();
    }
}