package com.atlassian.tutorial.myPlugin5.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class PaddockImpl {
    @XmlElement
    private Integer id;

    @XmlElement
    private String place;

    @XmlElement
    private Date walkingTime;

    @XmlElement
    private Integer duration;

    @XmlElement
    private String status;

    @XmlElement
    private Walker walker;

    @XmlElement
    private String idDogs;

    public PaddockImpl() {
    }

    public PaddockImpl(Integer id, String place, Date walkingTime,
                       Integer duration, String status, Walker walker, String idDogs) {
        this.id = id;
        this.place = place;
        this.walkingTime = walkingTime;
        this.duration = duration;
        this.status = status;
        this.walker = walker;
        this.idDogs = idDogs;
    }

    public Integer getID() {
        return id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getWalkingTime() {
        return walkingTime;
    }

    public void setWalkingTime(Date walkingTime) {
        this.walkingTime = walkingTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Walker getWalker() {
        return walker;
    }

    public void setWalker(Walker walker) {
        this.walker = walker;
    }

    public String getIdDogs() {
        return idDogs;
    }

    public void setIdDogs(String idDogs) {
        this.idDogs = idDogs;
    }
}