package com.atlassian.tutorial.myPlugin5.model;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;

import java.util.Date;

public interface Paddock extends Entity {
    String getPlace();

    void setPlace(String place);

    Date getWalkingTime();

    void setWalkingTime(Date walkingTime);

    Integer getDuration();

    void setDuration(Integer duration);

    String getStatus();

    void setStatus(String status);

    Walker getWalker();

    void setWalker(Walker walker);

    Client getClient();

    void setClient(Client client);

    String getIdDogs();

    void setIdDogs(String idDogs);

    Long getPID();

    void setPID(Long PID);

    String getLink();

    void setLink(String link);

    @ManyToMany(value = PaddockToDog.class)
    Dog[] getDogs();
}