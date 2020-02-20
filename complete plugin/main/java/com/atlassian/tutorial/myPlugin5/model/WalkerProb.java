package com.atlassian.tutorial.myPlugin5.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class WalkerProb {

    @XmlElement
    private String lastName;

    @XmlElement
    private String firstName;

    @XmlElement
    private String middleName;

    @XmlElement
    private Date birthday;

    @XmlElement
    private String phone;

    @XmlElement
    private String email;

    public WalkerProb() {
    }

    public WalkerProb(String lastName, String firstName,
                      String middleName, Date birthday, String phone,
                      String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
    }
}