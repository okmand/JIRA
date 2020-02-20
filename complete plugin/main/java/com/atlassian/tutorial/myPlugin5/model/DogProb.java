package com.atlassian.tutorial.myPlugin5.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class DogProb {
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

    public DogProb() {
    }

    public DogProb(String name, String gender, Date birthday,
                   String breed, String color, String character) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.breed = breed;
        this.color = color;
        this.character = character;
    }
}