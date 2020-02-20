package com.atlassian.tutorial.myPlugin5.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class PaddockProb {
    @XmlElement
    private String place;

    @XmlElement
    private Date walkingTime;

    @XmlElement
    private Integer duration;

    @XmlElement
    private String status;

    @XmlElement
    private String idDogs;

    public PaddockProb() {
    }

    public PaddockProb(String place, Date walkingTime, Integer duration,
                       String status, String idDogs) {
        this.place = place;
        this.walkingTime = walkingTime;
        this.duration = duration;
        this.status = status;
        this.idDogs = idDogs;
    }
}