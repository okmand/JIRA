package com.atlassian.tutorial.myPlugin5.model;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;

import java.util.Date;

public interface Dog extends Entity {
    String getName();

    void setName(String name);

    String getGender();

    void setGender(String gender);

    Date getBirthday();

    void setBirthday(Date birthday);

    String getBreed();

    void setBreed(String breed);

    String getColor();

    void setColor(String color);

    String getCharacter();

    void setCharacter(String character);

    Client getClient();

    void setClient(Client client);

    @ManyToMany(value = PaddockToDog.class)
    Paddock[] getPaddocks();
}