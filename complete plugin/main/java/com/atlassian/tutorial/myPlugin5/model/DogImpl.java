package com.atlassian.tutorial.myPlugin5.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class DogImpl {
    @XmlElement
    private Integer id;

    @XmlElement
    private String name;

    @XmlElement
    private String gender;

    @XmlElement
    private Date birthday;

    @XmlElement
    private String breed;

    @XmlElement
    private String color;

    @XmlElement
    private String character;

    @XmlElement
    private Client client;

    public DogImpl() {
    }

    public DogImpl(Integer id, String name, String gender,
                   Date birthday, String breed, String color,
                   String character, Client client) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.breed = breed;
        this.color = color;
        this.character = character;
        this.client = client;
    }

    public Integer getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}